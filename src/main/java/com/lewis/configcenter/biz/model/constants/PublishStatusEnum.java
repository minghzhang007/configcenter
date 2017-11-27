package com.lewis.configcenter.biz.model.constants;

/**
 * @author zhangminghua
 */
public enum PublishStatusEnum {
    PUBLISH_NO(1, "未发布"), PUBLISH_YES(0, "已发布");

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

    public static PublishStatusEnum of(int code) {
        for (PublishStatusEnum publishStatusEnum : PublishStatusEnum.values()) {
            if (code == publishStatusEnum.getCode()) {
                return publishStatusEnum;
            }
        }
        return null;
    }

    PublishStatusEnum(int code, String desc) {

        this.code = code;
        this.desc = desc;
    }
}
