package com.zhihua.sell.controller;

import com.zhihua.sell.pojo.ProductCategory;
import com.zhihua.sell.pojo.ProductInfo;
import com.zhihua.sell.service.CategoryService;
import com.zhihua.sell.service.ProductService;
import com.zhihua.sell.utils.ResultVOUtil;
import com.zhihua.sell.vo.ProductInfoVO;
import com.zhihua.sell.vo.ProductVO;
import com.zhihua.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @RequestMapping("/list")
    //@Cacheable(cacheNames="product",key = "123")   // 第一次访问緩存起來，以后每次请求访问数据
    public ResultVO<ProductVO> list(){

        //获取所有上架商品
        List<ProductInfo> productList = productService.findUpAll();

        //获取商品分类
        List<Integer> categoryType = productList.stream()
                .map(ProductInfo::getCategoryType)
                .distinct()
                .collect(Collectors.toList());
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryType);

        List<ProductVO> list = new ArrayList<>();
        categoryList.forEach(c -> {
            ProductVO  productVO = new ProductVO();
            productVO.setCategoryName(c.getCategoryName());
            productVO.setCategoryType(c.getCategoryType());
            ArrayList<ProductInfoVO> ProductInfoVOList = new ArrayList<>();
            productList.forEach(p -> {
                if(p.getCategoryType().equals(c.getCategoryType())){
                    ProductInfoVO infoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(p,infoVO);
                    ProductInfoVOList.add(infoVO);
                }
            });
            productVO.setList(ProductInfoVOList);
            list.add(productVO);
        });

        ResultVO success = ResultVOUtil.success(list);
        return success;


    }


}
