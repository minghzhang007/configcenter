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
public class CommitDO extends BaseEntity {

    /**
     * 修改变更集
     */
    private String changeSets;

    /**
     * appId
     */
    private String appId;

}
