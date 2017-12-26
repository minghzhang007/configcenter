package com.lewis.configcenter.biz.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseEntity {

    /**
     * 主键  db_column: id
     */
    private Long id;

    /**
     * 状态 0：正常 -1：删除
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

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;
}
