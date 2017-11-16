package com.lewis.configcenter.common.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "cache.nkv")
public class CacheConfig {

    private String master;

    private String slave;

    private String group;

    private short namespace;
}
