package com.lewis.configcenter.biz.dao.local;

import com.lewis.configcenter.biz.model.entity.CommitDO;
import com.lewis.configcenter.biz.model.queryobject.CommitQO;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */
public interface CommitMapper {
    List<CommitDO> list(CommitQO qo);

    int insert(CommitDO commitDO);

    int delete(Long id);
}
