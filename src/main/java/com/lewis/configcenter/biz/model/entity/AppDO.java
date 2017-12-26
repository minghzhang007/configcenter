package com.lewis.configcenter.biz.model.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author:zhangminghua
 * @version:1.0
 * @since:1.0
 * @createTime:2017-11-15 22:19:10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppDO extends BaseEntity implements java.io.Serializable {


    private static final long serialVersionUID = -4875482884061816223L;


    private String appId;
    /**
     * 应用名称  db_column: app_name
     */
    private String appName;


    /**
     * 应用负责人  db_column: app_manager
     */
    private String appManager;

    /**
     * 部门名称  db_column: depart_name
     */
    private String departName;

    /**
     * 部门描述  db_column: depart_desc
     */
    private String departDesc;

}