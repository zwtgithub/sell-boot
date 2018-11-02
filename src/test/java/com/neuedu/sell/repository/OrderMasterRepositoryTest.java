package com.neuedu.sell.repository;

import com.neuedu.sell.entity.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Test
    public void findByBuyerOpenidTest(){
        Pageable pageable=new PageRequest(0,1);
        Page<OrderMaster> page= orderMasterRepository.findByBuyerOpenid("abc123",pageable);
        for (OrderMaster master : page.getContent()) {
            System.out.println(master);
        }
    }

    @Test
    public void saveTest(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("123");
        orderMaster.setBuyerName("萨达姆");
        orderMaster.setBuyerAddress("伊拉克");
        orderMaster.setBuyerPhone("12345874952134");
        orderMaster.setBuyerOpenid("abc123");
        orderMaster.setOrderAmount(new BigDecimal(10.5));
        orderMasterRepository.save(orderMaster);
    }
}