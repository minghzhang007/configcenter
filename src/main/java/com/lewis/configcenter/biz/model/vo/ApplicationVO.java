package com.lewis.configcenter.biz.model.vo;

import com.lewis.configcenter.biz.model.entity.ApplicationDO;
import com.lewis.configcenter.common.model.RadioModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangminghua
 */
@Data
@NoArgsConstructor
public class ApplicationVO extends ApplicationDO {

    private List<RadioModel> departs;
}
