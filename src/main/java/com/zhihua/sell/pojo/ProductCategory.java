package com.zhihua.sell.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;


/**
 *  @Data   ：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 @Setter：注解在属性上；为属性提供 setting 方法
 @Getter：注解在属性上；为属性提供 getting 方法
 @Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
 @NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
 @AllArgsConstructor：注解在类上；为类提供一个全参的构造方法
 */
//@Table(name = "product_category")   当类名与表明存在映射关系是可以不用
@Entity
@Data                     //次注解默认帮我们写了get set toString等方法
@DynamicUpdate            //动态更新
public class ProductCategory {
    @Id
    @GeneratedValue
    private Integer categoryId;

    //@Column(name = "category_name")   //当字段与列名存在映射关系是可以不用
    private String categoryName;

    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
