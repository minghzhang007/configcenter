package com.lewis.configcenter.common.login;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * openId关联
 *
 * @author hzlimaozhi
 */
@Data
@NoArgsConstructor
public class OpenIdAssociate implements Serializable {

    private static final long serialVersionUID = -4213264702453710344L;

    private String assoc_handle;

    private String assoc_type;

    private int expires_in;

    private String mac_key;
}
