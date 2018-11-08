package com.neuedu.sell.controller;

import com.neuedu.sell.Utils.KeyUtils;
import com.neuedu.sell.entity.ProductCategory;
import com.neuedu.sell.entity.ProductInfo;
import com.neuedu.sell.form.ProductForm;
import com.neuedu.sell.service.ProductCategoryService;
import com.neuedu.sell.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    ProductInfoService productInfoService;
    @Autowired
    ProductCategoryService productCategoryService;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "1") Integer size){
        Pageable pageable=new PageRequest(page-1,size);
        Page<ProductInfo> productInfoPage= productInfoService.findAll(pageable);
        ModelAndView model=new ModelAndView("/product/list");
        model.addObject("productInfoPage",productInfoPage);
        model.addObject("currentPage",page);
        model.addObject("size",size);
        return model;
    }

    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId){
       ModelAndView model=new ModelAndView("/product/index");
       if (!StringUtils.isEmpty(productId)){
           ProductInfo productInfo=productInfoService.findOne(productId);
           model.addObject("productInfo",productInfo);
       }
        List<ProductCategory> categoryList= productCategoryService.findAll();
       model.addObject("categoryList",categoryList);
       return model;
    }
    @PostMapping("/save")
    public ModelAndView save(ProductForm productForm){
        if (StringUtils.isEmpty(productForm.getProductId())){
            //新增
            ProductInfo productInfo=new ProductInfo();
            BeanUtils.copyProperties(productForm,productInfo);
            productInfo.setProductId(KeyUtils.generateUniqueKey());
            productInfoService.save(productInfo);
        }else {
            //修改
            ProductInfo productInfo=productInfoService.findOne(productForm.getProductId());
            BeanUtils.copyProperties(productForm,productInfo);
            productInfoService.save(productInfo);
        }
        ModelAndView model=new ModelAndView("redirect:list");
        return model;
    }
}
