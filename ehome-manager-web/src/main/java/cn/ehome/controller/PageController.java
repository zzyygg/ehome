package cn.ehome.controller;

import cn.ehome.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Guoxiujun
 * @date 2019/2/28
 */
@Controller
public class PageController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page) {
        System.out.println(page);
        return page;
    }
}
