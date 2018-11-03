package com.neuedu.sell.serviceImpl;

import com.neuedu.sell.ResultVoUtils.KeyUtils;
import com.neuedu.sell.converter.OrderMaster2OrderDTOConverter;
import com.neuedu.sell.dto.CartDTO;
import com.neuedu.sell.dto.OrderDTO;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.entity.OrderMaster;
import com.neuedu.sell.entity.ProductInfo;
import com.neuedu.sell.enums.OrderStatusEnum;
import com.neuedu.sell.enums.PayStatusEnum;
import com.neuedu.sell.enums.ResultEnum;
import com.neuedu.sell.exception.SellException;
import com.neuedu.sell.repository.OrderDetailRepository;
import com.neuedu.sell.repository.OrderMasterRepository;
import com.neuedu.sell.repository.ProductInfoRepository;
import com.neuedu.sell.service.OrderService;
import com.neuedu.sell.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    ProductInfoService productInfoService;
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAccount=new BigDecimal(BigInteger.ZERO);
        //生成id
        String orderId=KeyUtils.generateUniqueKey();
        //查询商品
        List<OrderDetail> orderDetailList=orderDTO.getOrderDetails();
        for (OrderDetail detail : orderDetailList) {
           ProductInfo productInfo=productInfoService.findOne(detail.getProductId());
           if (productInfo==null){
               throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
           }
            // 计算总价
           orderAccount=orderAccount.add(productInfo.getProductPrice().multiply(new BigDecimal(detail.getProductQuantity())));

           //订单详情入库
           // 设置订单id
            detail.setOrderId(orderId);
           //设置数据id
            detail.setDetailId(KeyUtils.generateUniqueKey());
            //复制商品信息
            BeanUtils.copyProperties(productInfo,detail);
            orderDetailRepository.save(detail);
        }
        // 订单主表入库
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAccount);
        orderMasterRepository.save(orderMaster);
        //扣库存
        List<CartDTO> cartDTOList=new ArrayList<>();
        for (OrderDetail orderDetail : orderDTO.getOrderDetails()) {
            cartDTOList.add(new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity()));
        }
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster=orderMasterRepository.findOne(orderId);
        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.size()==0){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO=OrderMaster2OrderDTOConverter.convert(orderMaster);
        orderDTO.setOrderDetails(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> masterPage=orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
       List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(masterPage.getContent());

        return new PageImpl<>(orderDTOList,pageable,masterPage.getTotalElements());
    }

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster=orderMasterRepository.findOne(orderDTO.getOrderId());
        //1.判断订单状态
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
         //2.修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderMasterRepository.save(orderMaster);
        // 3.返还库存
        List<CartDTO> cartDTOList=new ArrayList<>();

        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        for (OrderDetail orderDetail : orderDetailList) {
            cartDTOList.add(new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity()));
        }
        productInfoService.increaseStock(cartDTOList);
         //4.如果已支付，退款
        orderDTO.setBuyerOpenid(orderMaster.getBuyerOpenid());
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster=orderMasterRepository.findOne(orderDTO.getOrderId());
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster orderMaster=orderMasterRepository.findOne(orderDTO.getOrderId());
        if (orderMaster.getPayStatus().equals(PayStatusEnum.PAID.getCode())){
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        orderMaster.setPayStatus(PayStatusEnum.PAID.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
