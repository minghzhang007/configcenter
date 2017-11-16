var VM;

VM = new Vue({
    el: "#app",
    data: {
        pageSize: 10,
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
            var url = window.location.protocol + "//" + window.location.host ;

            var formData = {
                "currentPage": _this.result.paginator.currentPage,
                "pageSize": _this.pageSize,
                "totalCount": _this.result.paginator.totalCount
            };

            $.ajax({
                url: url + "/testUser/query",
                type: "GET",
                dataType: "json",
                data: JSON.stringify(formData),
                success: function (data) {
                    _this.result = data;
                }
            });
        },

        exportToExcel: function (book) {
            var url = window.location.protocol + "//" + window.location.host ;

            window.location.href = url + "/book/day/export?" + JSON.stringify(book);
        },

        detail: function (book) {
            var url = window.location.protocol + "//" + window.location.host ;
            url = url + "/book/toDetail?" + JSON.stringify(book);
            window.open(url)
        }
    }
});

