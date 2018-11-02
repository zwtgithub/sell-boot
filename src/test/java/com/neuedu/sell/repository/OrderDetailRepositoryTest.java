package com.neuedu.sell.repository;

import com.neuedu.sell.entity.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("a1");
        orderDetail.setOrderId("123");
        orderDetail.setProductId("1");
        orderDetail.setProductName("皮皮虾");
        orderDetail.setProductPrice(new BigDecimal(16));
        orderDetail.setProductQuantity(5);
        orderDetail.setProductIcon("http://fuss10.elemecdn.com/e/df/1868889f1ade9a25c9d816dde6989jpeg.jpeg?imageMogr/format/webp/thumbnail/150x/");
        orderDetailRepository.save(orderDetail);
    }

    @Test
    public void findByOrderIdTest(){
        List<OrderDetail> list=orderDetailRepository.findByOrderId("123");
        for (OrderDetail detail : list) {
            System.out.println(detail);
        }
    }
}