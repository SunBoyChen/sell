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
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label for="exampleInputEmail1">名字</label>
                            <input type="text" class="form-control" name="categoryName" value="${(category.categoryName)!''}" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">type</label>
                            <input type="text" class="form-control" name="categoryType" value="${(category.categoryType)!''}" />
                        </div>


                        <input hidden type="text" name="categoryId" value="${(category.categoryId)!''}">

                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>





</body>
</html>


