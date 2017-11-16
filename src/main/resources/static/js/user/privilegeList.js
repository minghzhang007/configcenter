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
                "privilegeName": $("#privilegeName").val(),
                "currentPage": _this.result.paginator.currentPage,
                "pageSize": _this.pageSize,
                "totalCount": _this.result.paginator.totalCount
            };

            $.ajax({
                url: url + "/privilege/query",
                type: "GET",
                dataType: "json",
                data: JSON.stringify(formData),
                success: function (data) {
                    _this.result = data;
                }
            });
        },


        addPrivilege: function () {

            var addForm = {
                "privilegeName": '',
                "privilegeDesc": '',
                "status": -1
            };
            var url = window.location.protocol + "//" + window.location.host ;
            $("#addPrivilegeModal").modal({
                show: true,
                remote: url + "/privilege/edit?" + JSON.stringify(addForm),
                backdrop: 'static'
            });
        },

        update: function (role) {
            var url = window.location.protocol + "//" + window.location.host ;
            $("#addPrivilegeModal").modal({
                show: true,
                remote: url + "/privilege/edit?" + JSON.stringify(role),
                backdrop: 'static'
            });
        }
    }
});

$("#addPrivilegeModal").on("hidden.bs.modal", function () {
    $(this).removeData("bs.modal");
});
