package com.lewis.configcenter.biz.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author:zhangminghua
* @version:1.0
* @since:1.0
* @createTime:2017-11-15 22:19:40
*/
@Data
@NoArgsConstructor
public class AppItemDO implements java.io.Serializable{


    private static final long serialVersionUID = 6621557440374847281L;
    /**
     * 主键  db_column: id
     */
    private Long id;

    /**
     * 应用ID  db_column: app_id
     */
    private Integer appId;

    /**
     * 应用名称  db_column: app_name
     */
    private String appName;

    /**
     * 配置项的key  db_column: key
     */
    private String key;

    /**
     * 配置项的value  db_column: value
     */
    private String value;

    /**
     * 配置项的评论  db_column: comment
     */
    private String comment;

    /**
     * 创建时间  db_column: create_time
     */
    private Long createTime;

    /**
     * 修改时间  db_column: update_time
     */
    private Long updateTime;



}