var VM;

VM = new Vue({
    el: "#app",
    data: {
        pageSize: 10,
        // 搜索表单
        queryForm: {},
        // 结果对象
        result: {
            data: []
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

            $.ajax({
                url: url + "/testUser/testQuery",
                type: "GET",
                dataType: "json",
                data: {},
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

