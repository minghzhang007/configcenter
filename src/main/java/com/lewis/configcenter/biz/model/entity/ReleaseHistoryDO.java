package com.lewis.configcenter.biz.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2018/1/8.
 */
@Data
@NoArgsConstructor
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
     * 发布类型，0: 普通发布，1: 回滚
     */
    private Integer type;

}