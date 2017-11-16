package com.lewis.configcenter.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultMsg {

    /**
     * 请求是否处理成功
     */
    private boolean success;

    /**
     * 请求处理的额外信息
     */
    private String msg;

    /**
     * 状态码
     */
    private int resultCode;

    /**
     * 请求返回的结果
     */
    private Object data;

    public ResultMsg(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
