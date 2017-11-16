package com.lewis.configcenter.common.login;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoginUserInfo implements Serializable {

    private static final long serialVersionUID = 1993860563613605741L;

    private String fullName;

    private String nickName;

    private String email;
}
