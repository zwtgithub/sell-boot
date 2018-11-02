package com.neuedu.sell.service;

import com.neuedu.sell.dto.CartDTO;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    void decreaseStock(List<CartDTO> cartDTOList);
}
