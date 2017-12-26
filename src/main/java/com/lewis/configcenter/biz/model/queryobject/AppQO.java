package com.lewis.configcenter.biz.model.queryobject;

import com.lewis.configcenter.common.component.page.Paginator;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2017/11/15.
 */
@Data
@NoArgsConstructor
public class AppQO extends Paginator{

    private String appName;

    private String appDesc;
}
