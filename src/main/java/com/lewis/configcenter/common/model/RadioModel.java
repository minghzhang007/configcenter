package com.lewis.configcenter.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangminghua
 */

@Data
@NoArgsConstructor
public class RadioModel {
    private String key;

    private String value;

    private String selected;
}
