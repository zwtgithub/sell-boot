package com.neuedu.sell.serviceImpl;

import com.neuedu.sell.dto.CartDTO;
import com.neuedu.sell.dto.OrderDTO;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
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
        orderDTO.setBuyerName("马六");
        orderDTO.setBuyerPhone("18415236549");
        orderDTO.setBuyerAddress("西八道");
        orderDTO.setBuyerOpenid("abc456");
        List<OrderDetail> orderDetailList=new ArrayList<>();
        orderDetailList.add(new OrderDetail("1",5));
        orderDetailList.add(new OrderDetail("123456",4));
        orderDTO.setOrderDetails(orderDetailList);
        orderService.create(orderDTO);
    }

    @Test
    public void findOne(){
        OrderDTO orderDTO=orderService.findOne("1541225661802152455");
        System.out.println(orderDTO);
    }
    @Test
    public void findListTest(){
        Page<OrderDTO> page= orderService.findList("abc123",new PageRequest(0,2));
        for (OrderDTO orderDTO : page.getContent()) {
            System.out.println(orderDTO);
        }
    }
    @Test
    public void cancel(){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setOrderId("1541074805983883854");
        orderService.cancel(orderDTO);
    }
    @Test
    public void finish(){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setOrderId("1541074805983883854");
        orderService.finish(orderDTO);
    }
    @Test
    public void paidTest(){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setOrderId("1541074805983883854");
        orderService.paid(orderDTO);
    }
}