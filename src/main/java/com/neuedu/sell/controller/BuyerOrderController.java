package com.neuedu.sell.controller;

import com.neuedu.sell.ResultVoUtils.ResultVoUtils;
import com.neuedu.sell.converter.OrderForm2OrderDTOConverter;
import com.neuedu.sell.dto.OrderDTO;
import com.neuedu.sell.enums.ResultEnum;
import com.neuedu.sell.exception.SellException;
import com.neuedu.sell.form.OrderForm;
import com.neuedu.sell.service.BuyerService;
import com.neuedu.sell.service.OrderService;
import com.neuedu.sell.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    BuyerService buyerService;
    @PostMapping("/create")                 //@Valid注解校验orderForm，把结果包装在bindingResult中
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
         //检验参数的合法性
         if (bindingResult.hasErrors()){
             throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
         }
         //转化为OrderDTO类型
         OrderDTO orderDTO=OrderForm2OrderDTOConverter.convert(orderForm);
         //创建订单
         orderService.create(orderDTO);
         //包装结果
        Map<String,String> map=new HashMap<>();
        map.put("orderId",orderDTO.getOrderId());
        ResultVo resultVo=ResultVoUtils.success(map);
        return resultVo;
    }

    @GetMapping("/list")
    public ResultVo list(@RequestParam("openid") String openid,
                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        System.out.println(77777777);
        Pageable pageable=new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage= orderService.findList(openid,pageable);
        ResultVo resultVo=ResultVoUtils.success(orderDTOPage.getContent());
        return resultVo;
    }

    @GetMapping("/detail")
    public ResultVo detail(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO=buyerService.findOrderOne(openid,orderId);//防止横向越权
        return ResultVoUtils.success(orderDTO);
    }
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){


        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrder(openid,orderId);//防止横向越权
        return ResultVoUtils.success();
    }
}
