package com.lewis.configcenter.biz.model.vo;

import com.lewis.configcenter.biz.model.entity.AppItemDO;
import com.lewis.configcenter.common.model.RadioModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangminghua
 */
@Data
@NoArgsConstructor
public class AppItemVO extends AppItemDO {

    private List<RadioModel> envs;

    private List<RadioModel> apps;
}
