package com.lewis.configcenter.biz.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by lewis0077 on 2018/1/17.
 */
@Data
@NoArgsConstructor
public class PublishVO {

    public String  appId;

    private String releaseName;
    private String comment;
}
