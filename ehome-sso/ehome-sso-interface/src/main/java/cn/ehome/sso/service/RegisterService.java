package cn.ehome.sso.service;

import cn.ehome.common.util.EhomeResult;
import cn.ehome.pojo.TbUser;


/**
 * @author:Jun
 * @time:2019/4/7
 */
public interface RegisterService {
    EhomeResult checkData(String param,int type);

    EhomeResult register(TbUser user);


}
