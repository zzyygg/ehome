package cn.ehome.portal.controller;

import cn.ehome.content.service.ContentService;
import cn.ehome.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Value("${CONTENT_LUNBO_ID}")
    private Long CONTENT_LUNBO_ID;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model){
        List<TbContent> list = contentService.getContentsByContentCategoryId(CONTENT_LUNBO_ID);
        model.addAttribute("ad1List",list);
        return "index";
    }
}
