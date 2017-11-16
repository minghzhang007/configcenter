var listVM;
debugger;
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
        },
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
            var url = window.location.protocol + "//" + window.location.host ;

            var formData = {
                "tradeStatus": $("#tradeStatus").val(),
                "startTime": '' != $("#startTime").val() ? moment($("#startTime").val()).format('X') * 1000 : null,
                "endTime": '' != $("#endTime").val() ? moment($("#endTime").val()).format('X') * 1000 : null,
                "userId": $("#userId").val(),
                "userName": $("#userName").val(),
                "tradeNo": $("#tradeNo").val(),
                "payMethod": $("#payMethod").val(),
                "currentPage": _this.result.paginator.currentPage,
                "pageSize": _this.pageSize,
                "totalCount": _this.result.paginator.totalCount
            };

            $.ajax({
                url: url + "/config/query",
                type: "GET",
                dataType: "JSON",
                data: JSON.stringify(formData),
                success: function (data) {
                    _this.result = data;
                }
            });
        },

        update: function (config) {
            var url = window.location.protocol + "//" + window.location.host ;
            $("#configModal").modal({
                show: true,
                remote: url + "/config/edit?" + JSON.stringify(config),
                backdrop: 'static'
            });
        }
    }
});

$("#configModal").on("hidden.bs.modal", function () {
    $(this).removeData("bs.modal");
});
