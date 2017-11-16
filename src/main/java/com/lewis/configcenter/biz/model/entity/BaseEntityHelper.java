package com.lewis.configcenter.biz.model.entity;

import com.lewis.configcenter.biz.model.constants.StatusEnum;

public class BaseEntityHelper {

    public static void setDefaultValue(BaseEntity baseEntity) {
        if(baseEntity != null){
            baseEntity.setStatus(StatusEnum.NORMAL.getCode());
            baseEntity.setCreateTime(System.currentTimeMillis());
            baseEntity.setUpdateTime(System.currentTimeMillis());
        }
    }
}
