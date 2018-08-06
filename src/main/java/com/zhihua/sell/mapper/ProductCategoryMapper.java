package com.zhihua.sell.mapper;

import com.zhihua.sell.pojo.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProductCategoryMapper {

    @Insert("INSERT INTO product_category (category_name,category_type) VALUES(#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    Integer insertByMap(Map<String,Object> map);


    @Insert("INSERT INTO product_category (category_name,category_type) VALUES(#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    Integer insertByObject(ProductCategory category);

    @Select("SELECT * from product_category where category_type = #{categoryType,jdbcType=INTEGER}")
    @Results({
           @Result(column = "category_type",property = "categoryType"),
           @Result(column = "category_name",property = "categoryName"),
    })
    ProductCategory findByCategoryType(Integer categoryType);


    @Select("SELECT * from product_category where category_name = #{categoryName,jdbcType=VARCHAR}")
    @Results({
            @Result(column = "category_type",property = "categoryType"),
            @Result(column = "category_name",property = "categoryName"),
    })
    List<ProductCategory>findByCategoryName(String categoryName);

    @Update("UPDATE product_category SET category_name = #{categoryName,jdbcType=VARCHAR} WHERE category_type = #{categoryType,jdbcType=INTEGER}")
    Integer updateByCategoryType(@Param("categoryName") String categoryName,@Param("categoryType") Integer categoryType);

    @Delete("DELETE FROM product_category WHERE category_type = #{categoryType,jdbcType=INTEGER}")
    Integer deleteByCategoryType(Integer categoryType);


    ProductCategory selectByCategoryType(Integer categoryType);
}
