var VM;

$(document).ready(function () {
    debugger;
    VM = new Vue({

        el: "#modalForm",
        data: {
            //表单数据
            queryForm: formData
        },

        methods: {
            submitData: function () {
                var queryForm2 = this.queryForm;
                var url = window.location.protocol + "//" + window.location.host ;
                var data = JSON.stringify(queryForm2);

                url += '/config/update?' + data;
                $("#modalForm").data('bootstrapValidator').validate();
                if($("#modalForm").data('bootstrapValidator').isValid()){
                    $.ajax({
                        url: url,
                        type: "GET",
                        dataType: "json",
                        data: JSON.stringify(data),
                        success: function (data) {
                            if (data && data.success) {
                                $('#configModal').modal('hide');
                                listVM.query();
                                toastr.success(data.msg, '操作结果：', {timeOut: 2000, positionClass: "toast-top-center"});
                            } else {
                                toastr.error(data.msg, '操作结果：', {timeOut: 2000, positionClass: "toast-top-center"});
                            }
                        }
                    });
                }

            }

        }
    });

    $("#modalForm").bootstrapValidator({
        message: "this value is not valid",
        fields: {
            addRoleName: {
                validators: {
                    notEmpty: {
                        message: '未填写'
                    }
                }
            },
            addRoleDesc: {
                validators: {
                    notEmpty: {
                        message: '未填写'
                    }
                }
            },
            addStatus: {
                validators: {
                    notEmpty: {
                        message: '未选择'
                    }
                }
            }
           /* addPrivilege: {
                validators: {
                    notEmpty: {
                        message: '未选择'
                    }
                }
            }*/
        }
    });
});



