var VM;

$(document).ready(function () {
    debugger;
    VM = new Vue({

        el: "#modalForm",
        data: {
            //表单数据
            queryForm: formData,
            departs: departs
        },

        methods: {
            submitData: function () {
                this.queryForm.departs = this.departs;
                var id = this.queryForm.id;
                var url = window.location.protocol + "//" + window.location.host;
                var data = JSON.stringify(this.queryForm);
                console.log("departs："+departs);
                if (id == null) {
                    url += '/app/add?' + data;
                } else {
                    url += '/app/update?' + data;
                }
                $("#modalForm").data('bootstrapValidator').validate();
                if ($("#modalForm").data('bootstrapValidator').isValid()) {
                    $.ajax({
                        url: url,
                        type: "GET",
                        dataType: "json",
                        data: JSON.stringify(data),
                        success: function (data) {
                            if (data && data.success) {
                                $('#addModal').modal('hide');
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
            addUserName: {
                validators: {
                    notEmpty: {
                        message: '未填写'
                    }
                }
            },
            addNickName: {
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
        }
    });
});



