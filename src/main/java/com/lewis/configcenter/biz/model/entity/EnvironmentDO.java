package com.lewis.configcenter.biz.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author:zhangminghua
* @version:1.0
* @since:1.0
* @createTime:2017-11-15 22:17:45
*/
@Data
@NoArgsConstructor
public class EnvironmentDO implements java.io.Serializable{


    private static final long serialVersionUID = -8857669951039161227L;
    /**
     * 主键ID  db_column: id
     */
    private Integer id;

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