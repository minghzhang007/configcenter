var VM;

VM = new Vue({
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
        monthResult: {
            data: []
        }
    },
    mounted: function () {
        this.$nextTick(function () {
            this.queryMonth();
        })
    }
    ,
    methods: {
        queryMonth: function () {
            var _this = this;
            var url = window.location.protocol + "//" + window.location.host ;
            var formData = {
                "year": $("#year").val()
            };

            $.ajax({
                url: url + "/pay-method/query/month",
                type: "GET",
                dataType: "JSON",
                data: JSON.stringify(formData),
                success: function (data) {
                    _this.monthResult = data;
                }
            });
        },

        exportToExcel: function () {
            var url = window.location.protocol + "//" + window.location.host ;
            var formData = {
                "year": $("#year").val()
            };
            window.location.href = url + "/pay-method/export?" + JSON.stringify(formData);
        },

        queryDay: function () {
            var _this = this;
            var url = window.location.protocol + "//" + window.location.host ;
            var formData = {
                "startDay": $("#startTime").val(),
                "endDay": $("#endTime").val(),
                "currentPage": _this.result.paginator.currentPage,
                "pageSize": _this.pageSize,
                "totalCount": _this.result.paginator.totalCount
            };

            $.ajax({
                url: url + "/pay-method/query/day",
                type: "GET",
                dataType: "JSON",
                data: JSON.stringify(formData),
                success: function (data) {
                    _this.result = data;
                }
            });
        }

    }
});
$('#startTime').datepicker({
    changeMonth: true,
    changeYear: true,
    dateFormat: 'yymmdd'
});
$('#endTime').datepicker({
    changeMonth: true,
    changeYear: true,
    dateFormat: 'yymmdd'
});



