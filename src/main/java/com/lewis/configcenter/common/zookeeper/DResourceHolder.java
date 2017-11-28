package com.lewis.configcenter.common.zookeeper;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhangminghua
 */
@Component
@Data
@NoArgsConstructor
public class DResourceHolder {

    private Map<String, Class> dictKeyClassMapping = Maps.newHashMap();

    private Map<String, Object> dictKeyObjectMapping = Maps.newHashMap();
}
