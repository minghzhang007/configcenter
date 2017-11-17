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
                <label for="userName" class="col-md-1 control-label">用户名</label>
                <div class="col-md-2">
                    <input type="text" id="userName" placeholder="请输入用户名 邮箱前缀" class="form-control">
                </div>

                <label for="nickName" class="col-md-1 control-label">用户昵称</label>
                <div class="col-md-2">
                    <input type="text" id="nickName" placeholder="请输入用户昵称" class="form-control">
                </div>

                <div class="col-md-3">
                    <button type="button" class="btn btn-primary" @click="query">搜索
                    </button>
                    <button type="button" class="btn btn-primary" @click="add"> <i class="fa fa-plus"></i>新增应用
                    </button>
                </div>
            </div>
        </div>
    </form>
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
            <th>部门ID</th>
            <th>部门名称</th>
            <th>部门描述</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="depart in result.data" align="center">
            <td>{{depart.id}}</td>
            <td>{{depart.departName}}</td>
            <td>{{depart.departDesc}}</td>
            <td>{{depart.status | toStatusEnum}}</td>
            <td>
                <button type="button" class="btn btn-default" @click="update(depart)">修改</button>
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
<!-- /#page-content-wrapper -->
</@layout>
<script type="text/javascript" src="${request.contextPath}/js/app/appList.js"></script>

