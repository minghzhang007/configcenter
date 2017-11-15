var VM;

VM = new Vue({
    el: "#app",
    data: {
        pageSize: 10,
        // 搜索表单
        queryForm: {},
        // 结果对象
        result: {
            data: {
                "monthTradeItems": [],
                "monthConsumeItems":[],
                "monthTradeConsumeItems":[],
                "monthOtherItems": []
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
            var url = window.location.protocol + "//" + window.location.host + $("#contextPath").text();

            var formData = {
                "year": $("#year").val()
            };

            $.ajax({
                url: url + "/check-account/query",
                type: "GET",
                dataType: "JSON",
                data: JSON.stringify(formData),
                success: function (data) {
                    _this.result = data;
                }
            });
        },

        exportToExcel: function () {
            var url = window.location.protocol + "//" + window.location.host + $("#contextPath").text();
            var formData = {
                "year": $("#year").val()
            };
            window.location.href = url + "/check-account/export?" + JSON.stringify(formData);
        }
    }
});

