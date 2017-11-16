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
            var type = $("#type").val();
            var content = $("#content").val();
            var trafficPermit;
            var providerName;
            var providerAlias;
            var contractId;
            if (type === 'trafficPermit') {
                trafficPermit = content;
            } else if (type === 'providerName') {
                providerName = content;
            } else if (type === 'providerAlias') {
                providerAlias = content;
            } else if (type === 'contractId') {
                contractId = content;
            }

            var formData = {
                "trafficPermit": trafficPermit,
                "contractId": contractId,
                "providerName": providerName,
                "providerAlias": providerAlias,
                "currentPage": _this.result.paginator.currentPage,
                "pageSize": _this.pageSize,
                "totalCount": _this.result.paginator.totalCount
            };

            $.ajax({
                url: url + "/content-provider/query/list",
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

        detail: function (cp) {
            var url = window.location.protocol + "//" + window.location.host ;
            url = url + "/content-provider/toDetail?" + JSON.stringify(cp);
            window.open(url)
        }
    }
});

