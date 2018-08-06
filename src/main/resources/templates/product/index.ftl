<!DOCTYPE html>
<html lang="en">

<#include "../common/header.ftl">

<body>

<div id="wrapper" class="toggled">

<#--边栏sidebar-->
    <#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">

        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label for="exampleInputEmail1">名称</label>
                            <input type="text" class="form-control" name="productName" value="${(product.productName)!''}" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">价格</label>
                            <input type="text" class="form-control" name="productPrice" value="${(product.productPrice)!''}" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">库存</label>
                            <input type="number" class="form-control" name="productStock" value="${(product.productStock)!''}" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">描述</label>
                            <input type="text" class="form-control" name="productDescription" value="${(product.productDescription)!''}" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">图片</label>
                            <img height="100" width="100" src="${(product.productIcon)!''}" alt="">
                            <input type="text" class="form-control" name="productIcon" value="${(product.productIcon)!''}" />
                        </div>

                        <div class="form-group">
                            <label for="exampleInputEmail1">类目</label>

                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>

                                    <option value="${category.categoryType}"
                                        <#if (product.categoryType)?? && product.categoryType == category.categoryType>
                                            selected
                                        </#if>

                                    >
                                        ${category.categoryName}
                                    </option>
                                </#list>

                            </select>

                        </div>

                        <input hidden type="text" name="productId" value="${(product.productId)!''}">

                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>





</body>
</html>


