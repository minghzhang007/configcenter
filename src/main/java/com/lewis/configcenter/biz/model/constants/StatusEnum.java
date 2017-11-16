package com.lewis.configcenter.biz.model.constants;

public enum StatusEnum {
    NORMAL(0, "正常"), DELETE(-1, "删除");

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    StatusEnum(int code, String desc) {

        this.code = code;
        this.desc = desc;
    }
}
