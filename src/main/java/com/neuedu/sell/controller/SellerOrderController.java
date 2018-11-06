package com.neuedu.sell.controller;

import com.neuedu.sell.dto.OrderDTO;
import com.neuedu.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
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
                             @RequestParam(value = "size",defaultValue = "10") Integer size){
        ModelAndView model=new ModelAndView("/order/list");
        Pageable pageable=new PageRequest(page-1,size);
        Page<OrderDTO> orderDTOPage=orderService.findList(pageable);
        model.addObject("orderDTOPage",orderDTOPage);
        return model;
    }

}
