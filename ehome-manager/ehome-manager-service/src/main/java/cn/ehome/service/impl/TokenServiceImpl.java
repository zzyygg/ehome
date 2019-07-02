package cn.ehome.service.impl;

import cn.ehome.common.jedis.JedisClient;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.common.util.JsonUtil;
import cn.ehome.pojo.TbManager;
import cn.ehome.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private JedisClient jedisClient;
//    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE = 1800;
    @Override
    public EhomeResult getUserByToken(String token) {
        //根据token到redis中取用户信息
        String json = jedisClient.get("SESSION:" + token);
        //取不到用户信息，登录已经过期，返回登录过期
        if (StringUtils.isBlank(json)) {
            return EhomeResult.build(201, "用户登录已经过期");
        }
        //取到用户信息更新token的过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        //返回结果，E3Result其中包含TbUser对象
        TbManager user = JsonUtil.jsonToPojo(json, TbManager.class);
        System.out.println(user.getManagername());
        return EhomeResult.ok(user);
    }
}
