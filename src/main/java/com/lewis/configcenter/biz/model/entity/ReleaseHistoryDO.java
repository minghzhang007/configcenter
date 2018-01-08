package com.lewis.configcenter.biz.model.entity;

/**
 * Created by Administrator on 2018/1/8.
 */
public class ReleaseHistoryDO extends BaseEntity {

    private String appId;

    /**
     * 关联的ReleaseId
     */
    private Long releaseId;

    /**
     * 前一次发布的ReleaseId
     */
    private Long previousReleaseId;

    /**
     * 发布类型，0: 普通发布，1: 回滚，2: 灰度发布，3: 灰度规则更新，4: 灰度合并回主分支发布，5: 主分支发布灰度自动发布，6: 主分支回滚灰度自动发布，7: 放弃灰度
     */
    private Integer type;

}