package cn.ehome.controller;

import cn.ehome.common.util.EhomeResult;
import cn.ehome.common.util.JsonUtil;
import cn.ehome.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value="/manager/token/{token}",
            produces= MediaType.APPLICATION_JSON_UTF8_VALUE+";application/json;charset=utf-8")
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback) {
        System.out.println("getToken");
        System.out.println(token);
        EhomeResult result = tokenService.getUserByToken(token);
        //响应结果之前，判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            //把结果封装成一个js语句响应
            return callback + "(" + JsonUtil.objectToJson(result)  + ");";
        }
        return JsonUtil.objectToJson(result);
    }
}
