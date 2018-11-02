package com.neuedu.sell.controller;

import com.neuedu.sell.ResultVoUtils.ResultVoUtils;
import com.neuedu.sell.entity.ProductCategory;
import com.neuedu.sell.entity.ProductInfo;
import com.neuedu.sell.repository.ProductInfoRepository;
import com.neuedu.sell.service.ProductCategoryService;
import com.neuedu.sell.service.ProductInfoService;
import com.neuedu.sell.vo.ProductCategoryVo;
import com.neuedu.sell.vo.ProductInfoVo;
import com.neuedu.sell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class ProductCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    ProductInfoService productInfoService;
    @GetMapping("/list")
    public ResultVo findAll(){
        //查询所有上架商品
        List<ProductInfo> infoList=productInfoService.findUpAll();
        //构建商品类别编号集合
        List<Integer> categoryTypeList=new ArrayList<>();
        for (ProductInfo info : infoList) {
               categoryTypeList.add(info.getCategoryType());
        }
        //对应类目
        List<ProductCategory> categoryList=productCategoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductCategoryVo> categoryVoList=new ArrayList<>();
        for (ProductCategory category : categoryList) {
            ProductCategoryVo productCategoryVo=new ProductCategoryVo();
            BeanUtils.copyProperties(category,productCategoryVo);
            List<ProductInfoVo> infoVoList=new ArrayList<>();
            for (ProductInfo productInfo : infoList) {
                if (productInfo.getCategoryType().equals(category.getCategoryType())){
                    ProductInfoVo productInfoVo=new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    infoVoList.add(productInfoVo);
                }
            }
            productCategoryVo.setFoods(infoVoList);
            categoryVoList.add(productCategoryVo);
        }
        ResultVo resultVo=ResultVoUtils.success(categoryVoList);
        return resultVo;
    }
}
