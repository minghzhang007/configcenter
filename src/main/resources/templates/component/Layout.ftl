<#include "../component/nav.ftl">
<#macro layout title="" param={}>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0"/>
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>${title}</title>
    <style type="text/css">
        .table > thead > tr > th {
            text-align: center;
        }
        #go_top{position:fixed; LEFT: 85%;bottom:50px;}
    </style>
    <!-- Bootstrap -->
    <#include "../component/common.ftl">
    <@css></@css>
</head>
<body>
<div>
    <div id="wrapper" <#if !(param.hideNavbar?exists && param.hideNavbar == 'true')>class="toggled"</#if>>
        <!-- Sidebar -->
        <@nav activeKey=param.key!''/>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div class="m-content" id="page-content-wrapper">
            <button type="button" class="hamburger animated fadeInLeft <#if param.hideNavbar?exists && param.hideNavbar == 'true'>is-closed<#else>is-open</#if>" data-toggle="offcanvas">
                <span class="hamb-top"></span>
                <span class="hamb-middle"></span>
                <span class="hamb-bottom"></span>
            </button>
            <div class="container">
                <div id="app">
                <#--#c8e5bc-->
                    <div class="row" <#--style="background-color: #9acfea"-->>
                        <div class="col-md-1 col-md-offset-10">
                            <#if Session.currentUser?exists>
                                <span style="color:black">${(Session.currentUser.fullName)!"先生"}</span>
                            </#if>

                        </div>
                        <div class="col-md-1">
                            <#if Session.currentUser?exists>
                                <a href="/login/out" style="color:black"><strong>退出登录</strong></a>
                            </#if>
                        </div>
                    </div>
                    <div id="go_top">
                        <img src="${request.contextPath}/images/toTop.png">
                    </div>
                    <#nested />

                </div>
            </div>
        </div>
        <!-- /#page-content-wrapper -->
    </div>
</div>
    <@js></@js>
</body>
</html>
</#macro>