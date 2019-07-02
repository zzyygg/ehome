package cn.ehome.sso.controller;

import cn.ehome.common.util.EhomeResult;
import cn.ehome.pojo.TbUser;
import cn.ehome.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.spi.RegisterableService;

/**
 * @author:Jun
 * @time:2019/4/6
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    
    @RequestMapping("/page/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public EhomeResult checkData(@PathVariable String param, @PathVariable Integer type) {
        EhomeResult EhomeResult = registerService.checkData(param, type);
        return EhomeResult;
    }

    @RequestMapping(value="/user/register", method= RequestMethod.POST)
    @ResponseBody
    public EhomeResult register(TbUser user) {
        EhomeResult EhomeResult = registerService.register(user);
        return EhomeResult;
    }
}
