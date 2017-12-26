package com.lewis.configcenter.biz.model.queryobject;

import com.lewis.configcenter.common.component.page.Paginator;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2017/11/15.
 */
@Data
@NoArgsConstructor
public class ItemQO extends Paginator {

    private String appId;

    private String dictKey;

    private Integer publishStatus;
}
