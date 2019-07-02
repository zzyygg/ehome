package cn.ehome.sso.controller;

import cn.ehome.common.util.CookieUtil;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:Jun
 * @time:2019/4/7
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping("/page/login")
    public String showLogin(String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }

    @RequestMapping(value="/user/login", method= RequestMethod.POST)
    @ResponseBody
    public EhomeResult userLogin(String username, String password,
                             HttpServletRequest request, HttpServletResponse response) {
        System.out.println(username);
        EhomeResult EhomeResult = loginService.userLogin(username, password);
        //判断是否登录成功
        if(EhomeResult.getStatus() == 200) {
            String token = EhomeResult.getData().toString();
            System.out.println("登陆成功");
            //如果登录成功需要把token写入cookie
            CookieUtil.setCookie(request, response, TOKEN_KEY, token);
        }
        //返回结果
        return EhomeResult;
    }
}
