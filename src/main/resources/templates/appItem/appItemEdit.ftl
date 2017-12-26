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
                <label for="addKey" class="col-md-2 control-label">键</label>
                <div class="col-md-8">
                    <input type="text" id="addKey" name="addKey" v-model="queryForm.dictKey"
                           placeholder="请输入键"
                           class="form-control">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label for="addValue" class="col-md-2 control-label">值</label>
                <div class="col-md-8">
                    <input type="text" id="addValue" name="addValue" v-model="queryForm.value"
                           placeholder="请输入值"
                           class="form-control">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label for="addStatus" class="col-md-2 control-label">状态</label>
                <div class="col-md-8">
                    <select class="form-control" id="addStatus" name="addStatus" v-model="queryForm.status">
                        <option value="0">有效</option>
                        <option value="-1">删除</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label for="addComment" class="col-md-2 control-label">注释</label>
                <div class="col-md-8">
                    <textarea id="addComment" name="addComment" v-model="queryForm.comment"
                              placeholder="请输入该配置项的注释" class="form-control"></textarea>
     <#--               <input type="text" id="addComment" name="addComment" v-model="queryForm.comment"
                           placeholder="请输入该配置项的注释" class="form-control">-->
                </div>
            </div>
        </div>

        <#--<div>
            <fieldset>
                <legend>环境：</legend>
            </fieldset>
            <table class="table table-bordered table-hover table-striped">
                <tbody>
                <tr v-for="item in envs">
                    <td>
                        <input type="radio" name="env" :value="item.key" class="col-md-1"
                               v-model="item.selected"/>
                        <label class="col-md-offset-1">{{ item.value }}</label>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>-->
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" @click.prevent="submitData">提交</button>
    </div>
</form>
<script type="text/javascript">
    var formData =${appItem};
    var apps =${apps};
    var envs =${envs};
</script>
<script type="text/javascript" src="${request.contextPath}/js/appItem/appItemEdit.js"></script>
