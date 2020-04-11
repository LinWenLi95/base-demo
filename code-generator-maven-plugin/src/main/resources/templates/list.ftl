<%--
Created by IntelliJ IDEA.
author: ${copyRight.author}
since: ${copyRight.since?string("yyyy/MM/dd")}
version: ${copyRight.version}
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/pages/include/header.jsp" %>
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="/media/css/chosen.css" rel="stylesheet" type="text/css"/>
<link href="/media/css/profile.css" rel="stylesheet" type="text/css"/>
<link href="/media/css/select2_metro.css" rel="stylesheet" type="text/css"/>
<link href="/media/css/DT_bootstrap.css" rel="stylesheet" type="text/css"/>
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN PAGE -->
<div class="page-content">
    <!-- BEGIN PAGE CONTAINER-->
    <div class="container-fluid">
        <!-- BEGIN PAGE HEADER-->
        <div class="row-fluid">
            <div class="span12">
                <!-- BEGIN PAGE TITLE & BREADCRUMB-->
                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home"></i>
                        <a href="/admin/index">主页</a>
                        <i class="icon-angle-right"></i>
                    </li>
                    <li>
                        <a href="#">【面包屑导航-请修改】</a>
                    </li>
                </ul>
                <!-- END PAGE TITLE & BREADCRUMB-->
            </div>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <!--页面标题-->
                <h3 class="page-title">
                    【页面标题-请修改】
                    <small>【页面功能信息-请修改】</small>
                    <p id="btnGroup" class="pull-right">
                        <a href="javascript:;" class="btn green add">新增</a>
                    </p>
                </h3>
                <!--页面标题-->
                <form id="${beanName}Form" action="#" method="post">
                    <input type="hidden" name="${beanName}Uuid">
                </form>
                <div class="row-fluid form-horizontal form-line">
                    <table class="table table-striped table-bordered table-hover table-full-width"
                           id="table_${beanName}">
                    </table>
                </div>
            </div>
        </div>
        <!-- END PAGE CONTENT-->
    </div>
    <!-- END PAGE CONTAINER-->
</div>

<%@ include file="/WEB-INF/pages/include/footer.jsp" %>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script type="text/javascript" src="/media/js/select2.min.js"></script>
<script type="text/javascript" src="/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/media/js/DT_bootstrap.js"></script>
<script type="text/javascript" src="/media/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/media/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/media/js/page/${beanName}/list.js"></script>
<script>
    jQuery(document).ready(function () {
        ${beanName?cap_first}List.init();
    });
</script>
<!-- END PAGE LEVEL SCRIPTS -->