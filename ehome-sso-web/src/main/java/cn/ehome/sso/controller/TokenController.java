package cn.ehome.sso.controller;

import cn.ehome.common.util.EhomeResult;
import cn.ehome.common.util.JsonUtil;
import cn.ehome.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:Jun
 * @time:2019/4/7
 */
@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value="/user/token/{token}",
            produces= MediaType.APPLICATION_JSON_UTF8_VALUE+";application/json;charset=utf-8")
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback) {
        EhomeResult result = tokenService.getUserByToken(token);
        //响应结果之前，判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            //把结果封装成一个js语句响应
            return callback + "(" + JsonUtil.objectToJson(result)  + ");";
        }
        return JsonUtil.objectToJson(result);
    }
//    @RequestMapping(value="/user/token/{token}")
//    @ResponseBody
//    public Object getUserByToken(@PathVariable String token, String callback) {
//        System.out.println("/user/token");
//        EhomeResult result = tokenService.getUserByToken(token);
//        String s = JsonUtil.objectToJson(result.getData());
//        System.out.println(s);
//        //响应结果之前，判断是否为jsonp请求
//        if (StringUtils.isNotBlank(callback)) {
//            //把结果封装成一个js语句响应
//            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
//            mappingJacksonValue.setJsonpFunction(callback);
//            return mappingJacksonValue;
//        }
//        return result;
//    }
}
