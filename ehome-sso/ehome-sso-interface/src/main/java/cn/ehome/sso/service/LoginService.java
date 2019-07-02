package cn.ehome.sso.service;

import cn.ehome.common.util.EhomeResult;

/**
 * @author:Jun
 * @time:2019/4/7
 */
public interface LoginService {
    EhomeResult userLogin(String username, String password);
}
