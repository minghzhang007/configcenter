package com.lewis.configcenter.biz.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:zhangminghua
 * @version:1.0
 * @since:1.0
 * @createTime:2017-11-15 22:18:36
 */
@Data
@NoArgsConstructor
public class DepartmentDO implements java.io.Serializable {

    private static final long serialVersionUID = -5758278565167569769L;
    /**
     * 主键ID  db_column: id
     */
    private Integer id;

    /**
     * 部门名称  db_column: depart_name
     */
    private String departName;

    /**
     * 部门描述  db_column: depart_desc
     */
    private String departDesc;

    /**
     * 状态 0：正常 -1删除:  db_column: status
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