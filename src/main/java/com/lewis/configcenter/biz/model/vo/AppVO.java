package com.lewis.configcenter.biz.model.vo;

import com.lewis.configcenter.biz.model.entity.AppDO;
import com.lewis.configcenter.common.model.RadioModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangminghua
 */
@Data
@NoArgsConstructor
public class AppVO extends AppDO {

    private List<RadioModel> departs;
}
