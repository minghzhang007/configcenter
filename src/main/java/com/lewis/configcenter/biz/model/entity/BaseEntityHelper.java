package com.lewis.configcenter.biz.model.entity;

import com.lewis.configcenter.biz.model.constants.StatusEnum;

public class BaseEntityHelper {

    public static void setDefaultValue(BaseEntity baseEntity) {
        if(baseEntity != null){
            //todo 设置真是登录用户信息
            baseEntity.setCreator("zhangminghua01");
            baseEntity.setModifier("zhangminghua01");
            baseEntity.setStatus(StatusEnum.NORMAL.getCode());
            baseEntity.setCreateTime(System.currentTimeMillis());
            baseEntity.setUpdateTime(System.currentTimeMillis());
        }
    }
}
