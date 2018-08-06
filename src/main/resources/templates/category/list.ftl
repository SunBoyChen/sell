<!DOCTYPE html>
<html lang="en">
<#--<head>
    <meta charset="UTF-8">
    <title>卖家商品列表</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>-->

<#include "../common/header.ftl">

<body>

<div id="wrapper" class="toggled">

<#--边栏sidebar-->
    <#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">

        <div class="container-fluids">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>
                                类目id
                            </th>
                            <th>
                                名字
                            </th>
                            <th>
                                type
                            </th>
                            <th>
                                创建时间
                            </th>
                            <th>
                                修改时间
                            </th>
                            <th >
                                操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>

                            <#list categoryList as category>
                            <tr>
                                <td>
                                    ${category.categoryId}
                                </td>
                                <td>
                                    ${category.categoryName}
                                </td>
                                <td>
                                    ${category.categoryType}
                                </td>
                                <td>
                                    ${category.createTime}
                                </td>
                                <td>
                                    ${category.updateTime}
                                </td>

                                <td><a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a></td>

                            </tr>
                            </#list>


                        </tbody>
                    </table>
                </div>


            </div>


        </div>
    </div>

</div>





</body>
</html>




