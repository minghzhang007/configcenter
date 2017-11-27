package com.lewis.configcenter.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MsgConstant {

    private static final String MSG_ADD_SUCCESS = "新增数据成功";
    private static final String MSG_ADD_FAILURE = "新增数据失败";

    private static final String MSG_UPDATE_SUCCESS = "修改数据成功";
    private static final String MSG_UPDATE_FAILURE = "修改数据失败";

    private static final String MSG_DELETE_SUCCESS = "删除数据成功";
    private static final String MSG_DELETE_FAILURE = "删除数据失败";

    private static final String MSG_PUBLISH_SUCCESS = "发布数据成功";
    private static final String MSG_PUBLISH_FAILURE = "发布数据失败";

    public static String getAddMsg(boolean success) {
        if (success) {
            return MSG_ADD_SUCCESS;
        }
        return MSG_ADD_FAILURE;
    }

    public static String getUpdateMsg(boolean success) {
        if (success) {
            return MSG_UPDATE_SUCCESS;
        }
        return MSG_UPDATE_FAILURE;
    }

    public static String getDeleteMsg(boolean success) {
        if (success) {
            return MSG_DELETE_SUCCESS;
        }
        return MSG_DELETE_FAILURE;
    }

    public static String getPublishMsg(boolean success) {
        if (success) {
            return MSG_PUBLISH_SUCCESS;
        }
        return MSG_PUBLISH_FAILURE;
    }
}
