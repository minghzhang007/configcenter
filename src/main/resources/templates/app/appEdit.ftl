<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        &times;
    </button>
    <h4 class="modal-title" id="myModalLabel" style="color: black">添加新应用</h4>
</div>

<form class="form-horizontal form" role="form" id="modalForm">
    <div class="modal-body">
        <div class="row">
            <div class="form-group">
                <label for="addAppId" class="col-md-2 control-label">AppId</label>
                <div class="col-md-4">
                    <input type="text" id="addAppId" name="addAppId" v-model="queryForm.appId"
                           placeholder="请输入应用描述"
                           class="form-control">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label for="addAppName" class="col-md-2 control-label">应用名称</label>
                <div class="col-md-4">
                    <input type="text" id="addAppName" name="addAppName" v-model="queryForm.appName"
                           placeholder="请输入应用名称"
                           class="form-control">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="addStatus" class="col-md-2 control-label">状态</label>
                <div class="col-md-4">
                    <select class="form-control" id="addStatus" name="addStatus" v-model="queryForm.status">
                        <option value="0">有效</option>
                        <option value="-1">删除</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label for="appManager" class="col-md-2 control-label">应用负责人</label>
                <div class="col-md-4">
                    <input type="text" id="appManager" name="appManager" v-model="queryForm.appManager"
                           placeholder="请输入应用负责人"
                           class="form-control">
                </div>
            </div>
        </div>
        <div>
            <fieldset>
                <legend>部门：</legend>
            </fieldset>
            <table class="table table-bordered table-hover table-striped">
                <tbody>
                <tr v-for="item in departs">
                    <td>
                        <input type="radio" v-bind:name="item" :value="item.key" class="col-md-1"
                               v-model="item.selected"/>
                        <label class="col-md-offset-1">{{ item.value }}</label>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" @click.prevent="submitData">提交</button>
    </div>
</form>
<script type="text/javascript">
    var formData =${app};
    var departs =${departs};
</script>
<script type="text/javascript" src="${request.contextPath}/js/app/appEdit.js"></script>
