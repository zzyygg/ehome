package cn.ehome.service;

import cn.ehome.common.util.EhomeResult;

public interface LoginService {
    public EhomeResult managerLogin(String username, String password);
}
