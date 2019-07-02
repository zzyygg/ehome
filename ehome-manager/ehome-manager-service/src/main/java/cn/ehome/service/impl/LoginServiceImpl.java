package cn.ehome.service.impl;

import cn.ehome.common.jedis.JedisClient;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.common.util.JsonUtil;
import cn.ehome.mapper.TbManagerMapper;
import cn.ehome.pojo.TbManager;
import cn.ehome.pojo.TbManagerExample;
import cn.ehome.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TbManagerMapper managerMapper;
    @Autowired
    private JedisClient jedisClient;
    private Integer SESSION_EXPIRE = 1800;

    @Override
    public EhomeResult managerLogin(String managername, String password) {
        // 1、判断用户和密码是否正确
        //根据用户名查询用户信息
        TbManagerExample example = new TbManagerExample();
        TbManagerExample.Criteria criteria = example.createCriteria();
        criteria.andManagernameEqualTo(managername);
        //执行查询
        List<TbManager> list = managerMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            //返回登录失败
            return EhomeResult.build(400, "用户名或密码错误");
        }
        //取用户信息
        TbManager manager = list.get(0);
        //判断密码是否正确
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(manager.getPassword())) {
            // 2、如果不正确，返回登录失败
            return EhomeResult.build(400, "用户名或密码错误");
        }
        // 3、如果正确生成token。
        String token = UUID.randomUUID().toString();
        // 4、把用户信息写入redis，key：token value：用户信息
        manager.setPassword(null);
        jedisClient.set("SESSION:" + token, JsonUtil.objectToJson(manager));
        // 5、设置Session的过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        // 6、把token返回
        return EhomeResult.ok(token);
    }
}
