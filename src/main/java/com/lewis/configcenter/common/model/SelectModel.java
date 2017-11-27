package com.lewis.configcenter.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/**
 * @author zhangminghua01
 */
public class SelectModel {

    private String key;

    private String value;

    private String selected;
}
