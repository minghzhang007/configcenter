package com.lewis.configcenter.common.login;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class LoginUserInfoHolder {

    private static Set<LoginUserInfo> users = new CopyOnWriteArraySet<>();

    public static boolean addUser(LoginUserInfo user) {
        return users.add(user);
    }

    public static boolean removeUser(LoginUserInfo user) {
        return users.remove(user);
    }
}
