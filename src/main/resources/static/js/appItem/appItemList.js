var listVM;

listVM = new Vue({
    el: "#app",
    data: {
        pageSize: 100,
        // 搜索表单
        queryForm: {},
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
            this.query();
        })
    }
    ,
    methods: {
        query: function () {
            var _this = this;
            var url = window.location.protocol + "//" + window.location.host;
            var formData = {
                "dictKey": $("#dictKey").val(),
                "envName": $("#envName").val(),
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

        update: function (app) {
            var url = window.location.protocol + "//" + window.location.host;
            var app1 = JSON.parse($("#appString").text());
            console.log(app1);
            $("#addModal").modal({
                show: true,
                remote: url + "/appItem/edit?" + JSON.stringify(app),
                backdrop: 'static'
            });
        },

        publish: function (appName, envName) {
            var app = JSON.parse($("#appString").text());
            var formData = {
                "dictKey": $("#dictKey").val(),
                "envName": $("#envName").val(),
                "appName": app.appName
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
            });
        }
    }
});

$("#addModal").on("hidden.bs.modal", function () {
    $(this).removeData("bs.modal");
});
