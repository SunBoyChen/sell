package com.zhihua.sell.service;

import com.zhihua.sell.dto.CartDTO;
import com.zhihua.sell.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);


    //上架on_sale

    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);


}
