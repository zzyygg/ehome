package cn.ehome.service;

import cn.ehome.common.util.EhomeResult;

public interface TokenService {
    public EhomeResult getUserByToken(String token);
}
