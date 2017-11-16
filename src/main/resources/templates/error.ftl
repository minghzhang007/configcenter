<#include "./component/Layout.ftl">
<@layout title="蜗牛读书收费后台首页">
<div style="text-align: center">
    <h1 style="color: red">不好意思，服务器跑神了！</h1>

    <button type="button" class="btn btn-default showError">点击查看具体原因</button>
    <div id="error" style="display: none">
    ${detail!''}
    </div>

</div>
</@layout>
<script type="text/javascript">
    $(".showError").click(function () {
        console.log("show that");
        $("#error").show();
    });
</script>
