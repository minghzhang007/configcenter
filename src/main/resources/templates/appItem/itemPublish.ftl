<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
        &times;
    </button>
    <h4 class="modal-title" id="myModalLabel" style="color: black">发布(只有发布过的配置才会被客户端获取到)</h4>
</div>

<form class="form-horizontal form" role="form" id="modalForm">
    <div class="modal-body">
        <div>
            <fieldset>
                <legend>变更：</legend>
            </fieldset>
            <table class="table table-bordered table-hover table-striped">
                <thead>
                <tr>
                    <th>Key</th>
                    <th>已发布的值</th>
                    <th>未发布的值</th>
                    <th>修改人</th>
                    <th>修改时间</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="item in result.itemDtos">
                    <td>{{item.item.dictKey}}</td>
                    <td>{{item.oldValue}}</td>
                    <td>{{item.newValue}}</td>
                    <td>{{item.item.modifier}}</td>
                    <td>{{item.item.updateTime | toDateTime}}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="releaseName" class="col-md-2 control-label">Release-Name</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" id="releaseName" name="releaseName" v-model="result.releaseName"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group">
                <label for="addComment" class="col-md-2 control-label">注释</label>
                <div class="col-md-8">
                    <textarea id="addComment" name="addComment" v-model="queryForm.comment"
                              placeholder="请输入该配置项的注释" class="form-control"></textarea>
                </div>
            </div>
        </div>


    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" @click.prevent="submitData">发布</button>
    </div>
</form>
<script type="text/javascript">
</script>
<script type="text/javascript" src="${request.contextPath}/js/appItem/itemPublish.js"></script>
