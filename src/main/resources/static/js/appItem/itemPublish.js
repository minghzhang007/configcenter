var VM;

$(document).ready(function () {
    debugger;
    VM = new Vue({

        el: "#modalForm",
        data: {
            //表单数据
            queryForm: {},
            result:{
                itemDtos:[],
                releaseName: ""
            },

        },
        mounted: function () {
            this.$nextTick(function () {
                this.query();
                this.createReleaseName();
            })
        },

        methods: {
            query: function () {
                var _this = this;
                var url = window.location.protocol + "//" + window.location.host;
                var app = JSON.parse($("#appString").text());
                var appInfo = {
                    "appId": app.appId,
                    "appName": app.appName
                };
                $.ajax({
                    url: url + "/appItem/changes",
                    type: "GET",
                    dataType: "json",
                    data: JSON.stringify(appInfo),
                    success: function (data) {
                        _this.result = data;
                    }
                });
            },

            createReleaseName: function () {
                var _this = this;
                var url = window.location.protocol + "//" + window.location.host;
                $.ajax({
                    url: url + "/appItem/releaseName",
                    type: "GET",
                    dataType: "text",
                    data: {},
                    success: function (data) {
                        _this.releaseName = data;
                    }
                });
            },

            submitData: function () {
                var app = JSON.parse($("#appString").text());
                var appInfo = {
                    "appId": app.appId,
                    "appName": app.appName
                };
                var id = this.queryForm.id;
                var url = window.location.protocol + "//" + window.location.host;
                var data = JSON.stringify($.extend(this.queryForm, appInfo));
                if (id == null) {
                    url += '/appItem/add?' + data;
                } else {
                    url += '/appItem/update?' + data;
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



