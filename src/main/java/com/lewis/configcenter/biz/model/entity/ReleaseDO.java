package com.lewis.configcenter.biz.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zhangminghua
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReleaseDO extends BaseEntity {

    /**
     * 发布的Key
     */
    private String releaseKey;

    /**
     * 发布的名字
     */
    private String name;

    /**
     * 发布说明
     */
    private String comment;

    /**
     * appId
     */
    private String appId;

    /**
     * 发布的配置
     */
    private String configurations;

}
