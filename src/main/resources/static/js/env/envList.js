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
            var url = window.location.protocol + "//" + window.location.host ;

            var formData = {
                "currentPage": _this.result.paginator.currentPage,
                "pageSize": _this.pageSize,
                "totalCount": _this.result.paginator.totalCount
            };

            $.ajax({
                url: url + "/env/query",
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
                "envName": '',
                "envDesc": '',
                "status": -1
            };
            var url = window.location.protocol + "//" + window.location.host ;
            $("#addModal").modal({
                show: true,
                remote: url + "/env/edit?" + JSON.stringify(addForm),
                backdrop: 'static'
            });
        },

        update: function (user) {
            var url = window.location.protocol + "//" + window.location.host ;
            $("#addModal").modal({
                show: true,
                remote: url + "/env/edit?" + JSON.stringify(user),
                backdrop: 'static'
            });
        }
    }
});

$("#addModal").on("hidden.bs.modal", function () {
    $(this).removeData("bs.modal");
});
