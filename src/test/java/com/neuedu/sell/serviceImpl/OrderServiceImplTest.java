package com.neuedu.sell.serviceImpl;

import com.neuedu.sell.dto.CartDTO;
import com.neuedu.sell.dto.OrderDTO;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    OrderService orderService;
    @Test
    public void createTest(){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("张三");
        orderDTO.setBuyerPhone("17415236548");
        orderDTO.setBuyerAddress("西七道");
        orderDTO.setBuyerOpenid("abc123");
        List<OrderDetail> orderDetailList=new ArrayList<>();
        orderDetailList.add(new OrderDetail("1",10));
        orderDetailList.add(new OrderDetail("123456",2));
        orderDTO.setOrderDetails(orderDetailList);
        orderService.create(orderDTO);
    }
}