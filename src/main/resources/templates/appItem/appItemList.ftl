<#include "../component/Layout.ftl">
<@layout title="用户管理" param={"key": "privilege"}>
<ol class="breadcrumb">
    <li><a href="#" style="color: black;">权限管理</a></li>
    <li class="active">部门配置</li>
</ol>
<div>
    <form class="form-horizontal form" role="form" id="searchForm">
        <div class="row">
            <div class="form-group">
                <label for="dictKey" class="col-md-1 control-label">键</label>
                <div class="col-md-2">
                    <input type="text" id="dictKey" placeholder="请输入键" class="form-control">
                </div>

                <label for="appId" class="col-md-1 control-label">AppId</label>
                <div class="col-md-2">
                    <vue-select :options="appIds" id="appId" clazz="form-control"></vue-select>

                </div>

                <div class="col-md-3">
                    <button type="button" class="btn btn-primary" @click="query">搜索
                    </button>
                    <button type="button" class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>新增KV配置</button>
                    <button type="button" class="btn btn-primary" @click="publish"><i class="fa"></i>发布</button>
                </div>
            </div>
        </div>
    </form>
</div>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">
            应用信息
        </h3>
    </div>
    <div class="panel-body">
        <div class="row">
            <div class="form-group">
                <strong class="col-md-2">应用Id: ${app.appId}</strong>
                <strong class="col-md-2">应用名称: ${app.appName}</strong>
                <strong class="col-md-2">所属部门: ${app.departDesc}</strong>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

        </div>
    </div>
</div>

<div>
    <table class="table table-bordered table-hover table-striped">
        <thead>
        <tr>
            <th>键</th>
            <th>值</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="appItem in result.data" align="center">
            <td>{{appItem.dictKey}}</td>
            <td>{{appItem.value}}</td>
            <td>{{appItem.status | toStatusEnum}}</td>
            <td>
                <button type="button" class="btn btn-default" @click="update(appItem)">修改</button>
            </td>
        </tr>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="100">
                <vue-page :paginator="result.paginator" :size="10" :query="query"></vue-page>
            </td>
        </tr>
        </tfoot>
    </table>
</div>
<div>
    <span id="appString" style="display: none">${appString}</span>
</div>
<!-- /#page-content-wrapper -->
</@layout>
<script type="text/javascript" src="${request.contextPath}/js/appItem/appItemList.js"></script>


