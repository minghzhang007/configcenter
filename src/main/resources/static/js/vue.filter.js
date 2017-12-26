Vue.filter("toDateTime",
    function (value) {
        if (value) {
            if (isString(value)) {
                value = parseInt(value);
            }
            return moment(value).format('YYYY-MM-DD HH:mm:ss');
        } else {
            return value;
        }
    }
);

function isString(value) {
    return (typeof value == 'string') && value.constructor == String;
}


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
        } else if (value === 'apple.iap.deduction') {
            return '苹果IAP分成扣除';
        } else if (value === 'huawei.iap.deduction') {
            return '华为分成扣除';
        } else if (value === 'accumulateIncome') {
            return '单月累计收入';
        } else if (value === 'accumulateSettleIncome') {
            return '单月结算收入';
        } else {
            return '未知选项';
        }
    }
);

Vue.filter('toBookStatusEnum', function (value) {
    if (value === 0) {
        return "有效";
    } else if (value === 1) {
        return "测试";
    } else if (value === 2) {
        return "不可再订";
    } else if (value === -1) {
        return "过期";
    }
});
Vue.filter('toFixed', function (value) {
    if (value === undefined) {
        return value;
    }
    var number = parseFloat(value.toFixed(2));
    return number;
});

//将金额由分转为元，并保留两位小数
Vue.filter('toYuan', function (value) {
    value = value / 100;
    return parseFloat(value.toFixed(2));
});

//将小数转为其对应的百分比
Vue.filter('toPercentage', function (value) {
    value = parseFloat((value * 100).toFixed(2));
    return value + "%";
});

Vue.filter('toStatusEnum', function (value) {
    if (value === 0) {
        return '有效';
    } else {
        return '无效';
    }
});

Vue.filter('toZero', function (value) {
    if (value === null || value === undefined) {
        return 0;
    } else {
        return value;
    }
});

Vue.filter('toDefault', function (value) {
    if (value === null || value === undefined) {
        return '暂无';
    } else {
        return value;
    }
});

Vue.filter('toPublishStatus', function (value) {
    if (value === 0) {
        return '已发布';
    }
    if (value === 1) {
        return '未发布';
    }
    return '发布状态异常';
});
