package com.neuedu.sell.repository;

import com.neuedu.sell.entity.ProductInfo;
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
public class ProductInfoRepositoryTest {
     @Autowired
    ProductInfoRepository productInfoRepository;
    @Test
    public void saveTest(){
        ProductInfo productInfo =new ProductInfo();
        productInfo.setProductName("冰粥");
        productInfo.setProductId("123456");
        productInfo.setProductPrice(new BigDecimal(3.5));
        productInfo.setCategoryType(1);
        productInfo.setProductDescription("很好吃");
        productInfo.setProductIcon("www.xxxx.xxxx");
        productInfo.setProductStatus(0);
        productInfo.setProductStock(30);
        productInfoRepository.save(productInfo);
    }

    @Test
    public void findAllTest(){
        Pageable pageable=new PageRequest(0,2);
        Page<ProductInfo> productInfos=productInfoRepository.findAll(pageable);
        for (ProductInfo info : productInfos) {
            System.out.println(info);
        }
    }
}