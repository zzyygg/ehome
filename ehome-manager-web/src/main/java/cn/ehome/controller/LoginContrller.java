package cn.ehome.controller;

import cn.ehome.common.util.CookieUtil;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginContrller {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value="/manager/login", method= RequestMethod.POST)
    @ResponseBody
    public EhomeResult login(String username, String password,
                             HttpServletRequest request, HttpServletResponse response) {
        System.out.println(username);
        EhomeResult EhomeResult = loginService.managerLogin(username, password);
        //判断是否登录成功
        if(EhomeResult.getStatus() == 200) {
            String token = EhomeResult.getData().toString();
            System.out.println("登陆成功");
            //如果登录成功需要把token写入cookie
            CookieUtil.setCookie(request, response, "managerToken", token);
        }
        //返回结果
        return EhomeResult;
    }


}
