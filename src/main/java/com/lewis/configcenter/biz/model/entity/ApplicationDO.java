package com.lewis.configcenter.biz.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author:zhangminghua
* @version:1.0
* @since:1.0
* @createTime:2017-11-15 22:19:10
*/
@Data
@NoArgsConstructor
public class ApplicationDO implements java.io.Serializable{


    private static final long serialVersionUID = -4633044426378610008L;
    /**
     * 主键  db_column: id
     */
    private Integer id;

    /**
     * 应用名称  db_column: app_name
     */
    private String appName;

    /**
     * 应用描述  db_column: app_desc
     */
    private String appDesc;

    /**
     * 应用负责人  db_column: app_manager
     */
    private String appManager;

    /**
     * 部门ID  db_column: depart_id
     */
    private Integer departId;

    /**
     * 部门名称  db_column: depart_name
     */
    private String departName;

    /**
     * 部门描述  db_column: depart_desc
     */
    private String departDesc;

    /**
     * 环境ID  db_column: env_id
     */
    private Integer envId;

    /**
     * 环境名称  db_column: env_name
     */
    private String envName;

    /**
     * 环境描述  db_column: env_desc
     */
    private String envDesc;

    /**
     * 状态 0：正常 -1：删除  db_column: status
     */
    private Integer status;

    /**
     * 创建时间  db_column: create_time
     */
    private Long createTime;

    /**
     * 修改时间  db_column: update_time
     */
    private Long updateTime;



}