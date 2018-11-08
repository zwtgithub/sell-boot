package com.neuedu.sell.controller;

import com.neuedu.sell.dto.OrderDTO;
import com.neuedu.sell.enums.ResultEnum;
import com.neuedu.sell.exception.SellException;
import com.neuedu.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/seller")
public class SellerOrderController {

    @Autowired
    OrderService orderService;
    @RequestMapping("/order/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "1") Integer size){
        ModelAndView model=new ModelAndView("/order/list");
        Pageable pageable=new PageRequest(page-1,size);
        Page<OrderDTO> orderDTOPage=orderService.findList(pageable);
        model.addObject("orderDTOPage",orderDTOPage);
        model.addObject("currentPage",page);
        model.addObject("size",size);
        return model;
    }
    @GetMapping("/order/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId){
        try {
            OrderDTO orderDTO=orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch (SellException e){
            ModelAndView errorModel=new ModelAndView();
            errorModel.setViewName("common/error");
            errorModel.addObject("msg",e.getMessage());
            errorModel.addObject("url","/sell/seller/order/list");
             return errorModel;
        }
        ModelAndView model=new ModelAndView("common/success");
        model.addObject("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        model.addObject("url","/sell/seller/order/list");
        return model;
    }
    @GetMapping("/order/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId){
        OrderDTO orderDTO=new OrderDTO();
        try {
            orderDTO=orderService.findOne(orderId);
        }catch (SellException e){
            ModelAndView errorModel=new ModelAndView();
            errorModel.setViewName("common/error");
            errorModel.addObject("msg",e.getMessage());
            errorModel.addObject("url","/sell/seller/order/list");
            return errorModel;
        }
        ModelAndView model=new ModelAndView("order/detail");
        model.addObject("orderDTO",orderDTO);
        return model;
    }

    @GetMapping("/order/finish")
    public ModelAndView finished(@RequestParam("orderId") String orderId){
        try {
            OrderDTO orderDTO=orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e){
            ModelAndView errorModel=new ModelAndView();
            errorModel.setViewName("common/error");
            errorModel.addObject("msg",e.getMessage());
            errorModel.addObject("url","/sell/seller/order/list");
            return errorModel;
        }
        ModelAndView model=new ModelAndView("common/success");
        model.addObject("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        model.addObject("url","/sell/seller/order/list");
        return model;
    }
}
