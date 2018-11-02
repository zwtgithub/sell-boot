package com.neuedu.sell.service;


import com.neuedu.sell.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> findAll();

    ProductCategory findOne(Integer categoryId);

    ProductCategory save(ProductCategory productCategory);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);
}
