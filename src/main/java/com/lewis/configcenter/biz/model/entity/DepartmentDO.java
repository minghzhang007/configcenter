package com.lewis.configcenter.biz.model.entity;


import com.sun.xml.internal.rngom.parse.host.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author:zhangminghua
 * @version:1.0
 * @since:1.0
 * @createTime:2017-11-15 22:18:36
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DepartmentDO extends BaseEntity implements java.io.Serializable {


    private static final long serialVersionUID = 4416870378612066737L;
    /**
     * 部门名称  db_column: depart_name
     */
    private String departName;

    /**
     * 部门描述  db_column: depart_desc
     */
    private String departDesc;

}