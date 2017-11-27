package com.lewis.configcenter.biz.model.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author:zhangminghua
 * @version:1.0
 * @since:1.0
 * @createTime:2017-11-15 22:19:40
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppItemDO extends BaseEntity implements java.io.Serializable {


    private static final long serialVersionUID = -3692172690353486545L;

    /**
     * 应用名称  db_column: app_name
     */
    private String appName;

    private String appDesc;

    private String envName;

    private String envDesc;

    /**
     * 配置项的key  db_column: key
     */
    private String dictKey;

    /**
     * 配置项的value  db_column: value
     */
    private String value;

    /**
     * 配置项的评论  db_column: comment
     */
    private String comment;

    private Integer publishStatus;


}