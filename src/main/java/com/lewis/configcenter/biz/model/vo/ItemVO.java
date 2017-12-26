package com.lewis.configcenter.biz.model.vo;

import com.lewis.configcenter.biz.model.entity.ItemDO;
import com.lewis.configcenter.common.model.RadioModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangminghua
 */
@Data
@NoArgsConstructor
public class ItemVO extends ItemDO {

    private List<RadioModel> envs;

    private List<RadioModel> apps;
}
