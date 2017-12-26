package com.lewis.configcenter.biz.model.dto;

import com.lewis.configcenter.biz.model.vo.ItemDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */
@Data
@NoArgsConstructor
public class ReleaseDTO {

    private List<ItemDTO> itemDtos;

    private String releaseName;

}
