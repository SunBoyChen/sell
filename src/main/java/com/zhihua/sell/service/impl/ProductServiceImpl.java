package com.zhihua.sell.service.impl;

import com.zhihua.sell.dao.ProductInfoDao;
import com.zhihua.sell.dto.CartDTO;
import com.zhihua.sell.enums.ProductStatusEnums;
import com.zhihua.sell.enums.ResultEnum;
import com.zhihua.sell.exception.SellException;
import com.zhihua.sell.pojo.ProductInfo;
import com.zhihua.sell.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@CacheConfig(cacheNames="product")
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductInfoDao productDao;

    @Override
    //@Cacheable(key = "#productId",condition = "#productId.length()<1",unless = "#result.getCategoryType() != 1 ")
    // 1。动态获取key值，condition结果成立对key缓存,unless对结果进行判断（根据返回值来判断是否缓存）
    public ProductInfo findOne(String productId) {
        return productDao.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productDao.findByProductStatus(ProductStatusEnums.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productDao.findAll(pageable);
    }

    @Override
    //@CachePut(key = "123")   //跟新操作 要保持返回值一致
    public ProductInfo save(ProductInfo productInfo) {
        return productDao.save(productInfo);
    }


    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {

        cartDTOList.forEach(cart -> {
            ProductInfo product = productDao.findOne(cart.getProductId());
            if (product == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            product.setProductStock(product.getProductStock()+cart.getProductQuantity());
            productDao.save(product);
        });
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        cartDTOList.forEach(cart -> {
            ProductInfo product = productDao.findOne(cart.getProductId());
            if (product == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //判断库存是否大于购买数量
            int stock = product.getProductStock() - cart.getProductQuantity();
            if(stock < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            //减少库存
            product.setProductStock(stock);
            productDao.save(product);
        });


    }

    @Override
    public ProductInfo onSale(String productId) {

        ProductInfo product = productDao.findOne(productId);
        if(null == product){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (product.getProductStatus().equals(ProductStatusEnums.UP.getCode())) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        product.setProductStatus(ProductStatusEnums.UP.getCode());
        return productDao.save(product);
    }


    @Override
    public ProductInfo offSale(String productId) {

        ProductInfo product = productDao.findOne(productId);
        if(null == product){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (product.getProductStatus().equals(ProductStatusEnums.DOWN.getCode())) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        product.setProductStatus(ProductStatusEnums.DOWN.getCode());
        return productDao.save(product);
    }
}
