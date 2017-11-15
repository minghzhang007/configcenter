Vue.filter("toDateTime",
    function (value) {
        if (value) {
            return moment(value).format('YYYY-MM-DD HH:mm:ss');
        } else {
            return value;
        }
    }
);

Vue.filter("toDate",
    function (value) {
        if (value) {
            return moment(value).format('YYYY-MM-DD');
        } else {
            return value;
        }
    }
);

Vue.filter("tradeStatusEnum", function (value) {
    if (value === 0) {
        return '未付款';
    } else if (value === 10) {
        return '已付款';
    } else if (value == 20) {
        return '交易关闭';
    } else {
        return '状态异常';
    }
});

Vue.filter("toPayMethodEnum", function (value) {
        if (value === 'apple.iap') {
            return '苹果IAP';
        } else if (value === 'weixinpay.sdk') {
            return '微信';
        } else if (value === 'weixinpay.jsapi') {
            return '微信服务号';
        } else if (value === 'alipay.sdk') {
            return '支付宝';
        } else if (value === 'huawei.iap') {
            return '华为支付';
        } else if (value === 'apple.iap.sandbox') {
            return '苹果IAP沙盒';
        } else {
            return '';
        }
    }
);