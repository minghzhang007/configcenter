package com.lewis.configcenter.biz.model.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author:zhangminghua
 * @version:1.0
 * @since:1.0
 * @createTime:2017-11-15 22:17:45
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EnvironmentDO extends BaseEntity implements java.io.Serializable {


    private static final long serialVersionUID = 3716678897472682730L;
    /**
     * 环境名称  db_column: env_name
     */
    private String envName;

    /**
     * 环境描述  db_column: env_desc
     */
    private String envDesc;

}