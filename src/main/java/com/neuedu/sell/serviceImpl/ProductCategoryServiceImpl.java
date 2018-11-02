package com.neuedu.sell.serviceImpl;

import com.neuedu.sell.entity.ProductCategory;
import com.neuedu.sell.repository.ProductCategoryRepository;
import com.neuedu.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService
{
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Override
    public List<ProductCategory> findAll() {
        List<ProductCategory> productCategories=productCategoryRepository.findAll();
        return productCategories;
    }

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return productCategoryRepository.findOne(categoryId);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> list) {
        return productCategoryRepository.findByCategoryTypeIn(list);
    }
}
