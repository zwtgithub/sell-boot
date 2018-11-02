package com.neuedu.sell.serviceImpl;

import com.neuedu.sell.ResultVoUtils.KeyUtils;
import com.neuedu.sell.dto.CartDTO;
import com.neuedu.sell.dto.OrderDTO;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.entity.OrderMaster;
import com.neuedu.sell.entity.ProductInfo;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin.com.Utils;

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
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
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
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
