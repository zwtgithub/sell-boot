package com.neuedu.sell.repository;

import com.neuedu.sell.entity.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Test
    public void findAllTest(){

        List<ProductCategory> list=productCategoryRepository.findAll();
        for (ProductCategory category : list) {
            System.out.println(category);
        }
    }
    @Test
    public void findByCategoryTypeTest(){
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        List<ProductCategory> list1=productCategoryRepository.findByCategoryTypeIn(list);
        for (ProductCategory category : list1) {
            System.out.println(category);
        }
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryType(2);
        productCategory.setCategoryName("女生最爱");
        productCategoryRepository.save(productCategory);
    }
}