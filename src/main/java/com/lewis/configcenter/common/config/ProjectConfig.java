package com.lewis.configcenter.common.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "project")
public class ProjectConfig {

    private String name;

    private String code;

    private String url;
}
