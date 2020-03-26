var ${beanName?cap_first}Edit = {
    init: function () {

        //点击返回
        $('#btnGroup .cancle').on('click',function () {
            anda.common.submit('${beanName}Form',${beanName?cap_first}Edit.url.list());
        });

        //点击保存触发弹窗
        $('#btnGroup .save').on('click',function(){
        $('#comfirm').modal('show');
        });

        //点击弹窗
        $('#comfirm .save').on('click',function(){

        var form = new FormData(${r'$'}('#${beanName}Form')[0]);
        $('#comfirm').modal('hide');
            if(${r'$'}('#${beanName}Form').valid()){
                anda.ajax.request(form,${beanName?cap_first}Edit.url.edit(),${beanName?cap_first}Edit.successFunction,null,false,true);
            }
        });
    },
    url: {
        list:function () {
            return '/admin/${beanName}/list.page';
        },
        detail:function () {
            return '/admin/${beanName}/detail.page';
        },
        edit:function () {
            return '/admin/${beanName}/edit.ajax';
        }
    },
    successFunction:function (result) {
        if(result.success){
            toastr.success(result.msg);
            setTimeout('anda.common.submit("${beanName}Form",${beanName?cap_first}Edit.url.detail())',2000);
        }else{
            toastr.error(result.msg);
        }
    }
}