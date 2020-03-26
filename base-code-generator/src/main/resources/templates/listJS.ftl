var ${beanName?cap_first}List = {
    init: function () {
        //初始化表格
        anda.table.init('table_${beanName}', ${beanName?cap_first}List.url.list(), ${beanName?cap_first}List.columns(),null);

        //点击进入详情
        // 加载【查看】按钮事件
        $('#table_${beanName?cap_first} a.view').live('click', function (e) {
            // 取消事件的默认动作
            e.preventDefault();
            // 车辆的UUID
            var ${beanName}Uuid = $(this).attr("data-id");
            $("input[name='${beanName}Uuid']").val(${beanName}Uuid);
            anda.common.submit('${beanName}Form',${beanName?cap_first}List.url.detail());
        });

        //进入添加页面
        $('#btnGroup .add').live('click', function (e) {
            // 取消事件的默认动作
            e.preventDefault();
            anda.common.submit('${beanName}Form',${beanName?cap_first}List.url.add());
        });

    },
    url: {
        list:function () {
            return '/admin/${beanName}/list.ajax';
        },
        detail:function () {
            return '/admin/${beanName}/detail.page';
        },
        add:function () {
            return '/admin/${beanName}/add.page';
        }
    },
    columns: function () {
    var columns = [
        {"data": null, "sTitle": "序号", "defaultContent": ""}, // 第一列序列号
    <#list columns as column>
        <#if column.javaField!="uuid" && column.javaField!="createTime" && column.javaField!="updateTime" && column.javaField!="updater">
        {"data": "${column.javaField}", "sTitle": "${column.comment}"}<#if column_has_next>,</#if>
        </#if>
    </#list>
    ];
        return columns;
    },
    // 设置检索参数
    setQueryParams: function () {
        return "&" + $('#${beanName}Form').serialize();
    }
}