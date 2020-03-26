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
<link href="/media/css/jquery.fileupload-ui.css" rel="stylesheet"/>
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
                        <a href="/admin/${beanName}/list.page">XX管理</a>
                        <i class="icon-angle-right"></i>
                    </li>
                    <li>
                        <a href="/admin/${beanName}/detail.page?companyUuid=${r'${'}${beanName}.uuid${r'}'}">XX详情</a>
                        <i class="icon-angle-right"></i>
                    </li>
                    <li>
                        <a href="#">编辑XX</a>
                    </li>
                </ul>
                <!-- END PAGE TITLE & BREADCRUMB-->
            </div>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <!--页面标题-->
                <h3 class="page-title">
                    编辑XX信息
                    <p id="btnGroup" class="pull-right">
                        <a href="javascript:;" class="btn green save">保存</a>
                        <a href="javascript:;" class="btn cancle">取消</a>
                    </p>
                </h3>
                <!--页面标题-->
                <form id="companyForm" action="#" method="post" class="form-horizontal">
                    <h4 class="form-section">XXXX</h4>
                    <input type="hidden" name="uuid" value="${r'${'}${beanName}.uuid${r'}'}">
                    <input type="hidden" name="${beanName}Uuid" value="${r'${'}${beanName}.uuid${r'}'}">
                <#list columns as column>
                    <#if column.javaField!="uuid" && column.javaField!="createTime" && column.javaField!="updateTime" && column.javaField!="updater">
                        <div class="control-group row-fluid">
                            <label class="control-label"><span class="text-error">*</span>${column.comment}：</label>
                            <div class="controls">
                                <input type="text" name="${column.javaField}" class="m-wrap"
                                       value="${r'${'}${beanName}.${column.javaField}${r'}'}"
                                       data-rule-required="${column.javaField}">
                            </div>
                        </div>
                    </#if>
                </#list>
                </form>
            </div>
        </div>
        <!-- END PAGE CONTENT-->
        <!-- START 弹窗 -->
        <div id="comfirm" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="headLabel"
             aria-hidden="true">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h3>提示</h3>
            </div>
            <div class="modal-body">
                <div class="row-fluid">
                    <div class="span12">
                        <h4>确认保存？</h4>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn green save">保存信息</button>
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div>
        <!-- END 弹窗 -->
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
<script src="/media/js/page/${beanName}/edit.js"></script>
<script>
    jQuery(document).ready(function () {
        ${beanName?cap_first}Edit.init();
    });
</script>
<!-- END PAGE LEVEL SCRIPTS -->