package com.zhihua.sell.dto;

import lombok.Data;

@Data
public class CartDTO {
    /** 商品Id. */
    private String productId;

    /** 数量. */
    private Integer productQuantity;


    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public CartDTO() {
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductId() {

        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
