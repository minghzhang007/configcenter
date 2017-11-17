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
                <label for="addAppDesc" class="col-md-2 control-label">应用描述</label>
                <div class="col-md-4">
                    <input type="text" id="addAppDesc" name="addAppDesc" v-model="queryForm.appDesc"
                           placeholder="请输入应用描述"
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
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" @click.prevent="submitData">提交</button>
    </div>
</form>
<script type="text/javascript">
    var formData =${app};
</script>
<script type="text/javascript" src="${request.contextPath}/js/app/appEdit.js"></script>
