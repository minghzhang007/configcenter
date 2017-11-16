$(document).ready(function () {
    var trigger = $('.hamburger');

    trigger.click(function () {
        var wrapper = $('#wrapper');
        wrapper.toggleClass('toggled');

        if(wrapper.hasClass('toggled')) {
            trigger.addClass('is-open');
            trigger.removeClass('is-closed');
        }
        else {
            trigger.addClass('is-closed');
            trigger.removeClass('is-open');
        }
    });

    //点击下拉菜单默认不收起
    var subNavMenu = $('#sidebar-wrapper .dropdown-menu li');
    subNavMenu.click(function(e) {
        e.stopPropagation();
    });

    //返回顶部
    $("#go_top").hide();
    $(function () {
        //检测屏幕高度
        var height = $(window).height();
        //scroll() 方法为滚动事件
        $(window).scroll(function () {
            if ($(window).scrollTop() > height) {
                $("#go_top").fadeIn(100);
            } else {
                $("#go_top").fadeOut(100);
            }
        });
        $("#go_top").click(function () {
            $('body,html').animate({scrollTop: 0}, 100);
            return false;
        });
    });

});