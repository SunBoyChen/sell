package com.zhihua.sell.controller;

import com.zhihua.sell.enums.ResultEnum;
import com.zhihua.sell.exception.SellException;
import com.zhihua.sell.from.ProductForm;
import com.zhihua.sell.pojo.ProductCategory;
import com.zhihua.sell.pojo.ProductInfo;
import com.zhihua.sell.service.CategoryService;
import com.zhihua.sell.service.ProductService;
import com.zhihua.sell.utils.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 * Created by 廖师兄
 * 2017-07-23 15:12
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    private static final Logger log = LoggerFactory.getLogger(SellerProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size){

        Pageable pageable = new PageRequest(page - 1,size);
        Map<String,Object> map = new HashMap<>();
        Page<ProductInfo> productInfoPage = productService.findAll(pageable);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);
    }


    //off_sale   on_sale

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam (value = "productId") String productId){
        Map<String, Object> map = new HashMap<>();
        try {
            ProductInfo product = productService.onSale(productId);
        }catch (SellException e) {
            log.error("【卖家端商品上架】发生异常{}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam (value = "productId") String productId){

        Map<String, Object> map = new HashMap<>();
        try {
            ProductInfo product = productService.offSale(productId);
        }catch (SellException e) {
            log.error("【卖家端商品下架】发生异常{}", e.getMessage());
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }


    @GetMapping("/index")
    public ModelAndView index(@RequestParam (value = "productId",required = false) String productId){

        Map<String, Object> map = new HashMap<>();
        if(!StringUtils.isEmpty(productId)){
            ProductInfo product = productService.findOne(productId);
            map.put("product",product);
        }
        //查找类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("product/index", map);
    }


    @PostMapping("/save")
    //@CacheEvict(cacheNames="product",key = "123") //青春緩存，刪除 ，每次访问该方法都进行删除炒作
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult){
        Map<String, Object> map = new HashMap<>();
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确, productForm={}", productForm);
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        ProductInfo product = new ProductInfo();
        try {
            //判断新增还是修改
            if(!StringUtils.isEmpty(productForm.getProductId())){
                //修改
                product = productService.findOne(productForm.getProductId());
            } else {
                productForm.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(productForm,product);
            productService.save(product);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

}
