<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        &times;
    </button>
    <h4 class="modal-title" id="myModalLabel" style="color: black">添加新环境</h4>
</div>

<form class="form-horizontal form" role="form" id="modalForm">
    <div class="modal-body">
        <div class="row">
            <div class="form-group">
                <label for="addEnvName" class="col-md-2 control-label">环境名称</label>
                <div class="col-md-4">
                    <input type="text" id="addEnvName" name="addEnvName" v-model="queryForm.envName"
                           placeholder="请输入环境名称"
                           class="form-control">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label for="addEnvDesc" class="col-md-2 control-label">环境描述</label>
                <div class="col-md-4">
                    <input type="text" id="addEnvDesc" name="addEnvDesc" v-model="queryForm.envDesc"
                           placeholder="请输入环境描述"
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
    var formData =${env};
</script>
<script type="text/javascript" src="${request.contextPath}/js/env/envEdit.js"></script>
