var listVM;

listVM = new Vue({
    el: "#app",
    data: {
        pageSize: 100,
        // 搜索表单
        queryForm: {},
        appIds: [],
        // 结果对象
        result: {
            data: [],
            paginator: {
                currentPage: 0,
                totalCount: 0,
                totalPage: 0
            }
        }
    },
    mounted: function () {
        this.$nextTick(function () {
            this.queryAppIds();
            this.query();
        })
    }
    ,
    methods: {
        query: function () {
            var _this = this;
            var url = window.location.protocol + "//" + window.location.host;
            var appId = JSON.parse($("#appString").text()).appId;
            if(appId === undefined || appId === ''){
                appId = $("#appId").val();
            }
            console.log("appId:"+appId);
            var formData = {
                "dictKey": $("#dictKey").val(),
                "appId": appId,
                "currentPage": _this.result.paginator.currentPage,
                "pageSize": _this.pageSize,
                "totalCount": _this.result.paginator.totalCount
            };

            $.ajax({
                url: url + "/appItem/query",
                type: "GET",
                dataType: "json",
                data: JSON.stringify(formData),
                success: function (data) {
                    _this.result = data;
                }
            });
        },
        queryAppIds: function () {
            var _this = this;
            var url = window.location.protocol + "//" + window.location.host;
            $.ajax({
                url: url + "/app/appIds",
                type: "GET",
                dataType: "json",
                data: {},
                success: function (data) {
                    _this.appIds = data;
                }
            });
        },


        add: function () {
            var addForm = {
                "key": '',
                "value": '',
                "status": -1
            };
            var url = window.location.protocol + "//" + window.location.host;
            $("#addModal").modal({
                show: true,
                remote: url + "/appItem/edit?" + JSON.stringify(addForm),
                backdrop: 'static'
            });
        },

        update: function (item) {
            var url = window.location.protocol + "//" + window.location.host;
            $("#addModal").modal({
                show: true,
                remote: url + "/appItem/edit?" + JSON.stringify(item),
                backdrop: 'static'
            });
        },

        del:function (item) {
            var _this = this;
            var url = window.location.protocol + "//" + window.location.host;
            $.ajax({
                url: url + "/appItem/delete",
                type: "GET",
                dataType: "json",
                data: JSON.stringify(item),
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
        },

        publish: function () {
            var url = window.location.protocol + "//" + window.location.host;
            var app = JSON.parse($("#appString").text());
            $("#publishModal").modal({
                show: true,
                remote: url + "/appItem/toPublish?" + JSON.stringify(app),
                backdrop: 'static'
            });

            /*var app = JSON.parse($("#appString").text());
            var formData = {
                "appId": app.appName
            };
            var url = window.location.protocol + "//" + window.location.host;
            $.ajax({
                url: url + "/appItem/publish",
                type: "GET",
                dataType: "json",
                data: JSON.stringify(formData),
                success: function (data) {
                    if (data && data.success) {
                        $('#addModal').modal('hide');
                        listVM.query();
                        toastr.success(data.msg, '操作结果：', {timeOut: 2000, positionClass: "toast-top-center"});
                    } else {
                        toastr.error(data.msg, '操作结果：', {timeOut: 2000, positionClass: "toast-top-center"});
                    }
                }
            });*/
        }
    }
});

$("#addModal").on("hidden.bs.modal", function () {
    $(this).removeData("bs.modal");
});

$("#publishModal").on("hidden.bs.modal", function () {
    $(this).removeData("bs.modal");
});
