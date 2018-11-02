package com.neuedu.sell.converter;

import com.neuedu.sell.dto.OrderDTO;
import com.neuedu.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
          OrderDTO orderDTO=new OrderDTO();
          BeanUtils.copyProperties(orderMaster,orderDTO);
           return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasters){
        List<OrderDTO> orderDTOList=new ArrayList<>();
        for (OrderMaster orderMaster : orderMasters) {
            orderDTOList.add(convert(orderMaster));
        }
        return orderDTOList;
    }

}
