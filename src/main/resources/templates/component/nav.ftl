<#macro nav activeKey="">
<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
    <ul class="nav sidebar-nav">
        <li class="sidebar-brand">
            <a href="#">
                蜗牛读书
            </a>
        </li>
        <li>
            <a href="/index"><i class="fa fa-fw fa-home"></i>首页</a>
        </li>
        <li class="dropdown <#if activeKey == 'trade'>open</#if>">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-fw"></i>环境部门配置
                <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li><a href="/env/toQuery">环境配置</a></li>
                <li><a href="/depart/toQuery">部门配置</a></li>
                <li><a href="/app/toQuery">应用配置</a></li>
            </ul>
        </li>
        <li class="dropdown <#if activeKey == 'settle'>open</#if>">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-fw"></i>结算管理
                <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li><a href="/check-account/toQuery">对账</a></li>
                <li><a href="/content-provider/query/toList">按合同查看</a></li>
                <li><a href="/book/toQuery">按书籍查看</a></li>
                <li><a href="/pay-method/toQuery">按支付渠道查看</a></li>
            </ul>
        </li>
        <li class="dropdown <#if activeKey == 'privilege'>open</#if>">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-fw"></i>权限管理
                <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li><a href="/user/toQuery">用户管理</a></li>
                <li><a href="/role/toQuery">角色管理</a></li>
                <li><a href="/privilege/toQuery">权限管理</a></li>
            </ul>
        </li>
        <li class="dropdown <#if activeKey == 'system'>open</#if>">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-fw"></i>系统运维
                <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li><a href="/trade-flat/toManualFlat">手动订单拆分</a></li>
                <li><a href="/config/toQuery">系统配置</a></li>
            </ul>
        </li>
    </ul>
    <div id="contextPath" style="display: none">${request.contextPath}</div>
</nav>

</#macro>
