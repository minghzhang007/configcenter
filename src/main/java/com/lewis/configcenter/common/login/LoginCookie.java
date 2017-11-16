package com.lewis.configcenter.common.login;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;

import java.io.Serializable;

/**
 * 在缓存中保存登录cookie信息
 *
 * @author hzlimaozhi
 */
@Data
@NoArgsConstructor
public class LoginCookie implements Serializable {

    private static final long serialVersionUID = 8450718314806244704L;

    /**
     * 登录cookie内容
     */
    private String base64;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 上次保存到缓存中的时间
     */
    private long lastSaveTime;

    public LoginCookie(String base64) {
        this.base64 = base64;
        this.createTime = System.currentTimeMillis();
        this.lastSaveTime = this.createTime;
    }

    public String getBase64() {
        return base64;
    }

    /**
     * 登录cookie是否有效 当天有效
     */
    public boolean isValid() {
        return new DateTime(this.createTime).getDayOfYear() == new DateTime(System.currentTimeMillis()).getDayOfYear();
    }

    /**
     * 需要延长
     */
    public boolean needSave() {
        if (System.currentTimeMillis() - lastSaveTime >= DateUtils.MILLIS_PER_MINUTE * (LoginConstants.LOGIN_COOKIE_EXPIRE / 3 * 2)) { // 过去2/3就再次设置
            this.lastSaveTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

}
