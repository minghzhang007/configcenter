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
public class ItemDTO {

    private ItemDO item;

    /**
     * 是否修改
     */
    private boolean isModified;

    /**
     * 是否是删除的配置项
     */
    private boolean isDeleted;

    /**是否是新增的配置项 */
    private boolean isNew;

    private String oldValue;
    private String newValue;

}
