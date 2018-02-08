package com.lewis.configcenter.biz.model.constants;

public enum ReleaseTypeEnum {

    NORMAL(0, "普通发布"), ROLLBACK(1, "回滚");

    private int code;

    private String desc;

    ReleaseTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ReleaseTypeEnum of(int code) {
        for (ReleaseTypeEnum releaseTypeEnum : ReleaseTypeEnum.values()) {
            if (releaseTypeEnum.getCode() == code) {
                return releaseTypeEnum;
            }
        }
        return null;
    }
}
