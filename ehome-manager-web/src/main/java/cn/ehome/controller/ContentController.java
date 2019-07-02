package cn.ehome.controller;

import cn.ehome.common.pojo.EasyUIDataGridResult;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.content.service.ContentService;
import cn.ehome.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentListByContentCategoryId(long categoryId, Integer page, Integer rows){
        EasyUIDataGridResult result = contentService.getContentListByContentCategoryId(categoryId,page,rows);
        return result;
    }

    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    @ResponseBody
    public EhomeResult addContent(TbContent content){
        EhomeResult result = contentService.addContent(content);
        return result;
    }

    @RequestMapping(value = "/rest/content/edit",method = RequestMethod.POST)
    @ResponseBody
    public EhomeResult editContent(TbContent content){
        EhomeResult result = contentService.editContent(content);
        return result;
    }

    @RequestMapping(value = "/content/delete",method = RequestMethod.POST)
    @ResponseBody
    public EhomeResult deleteContents(String ids){
        EhomeResult result = contentService.deleteContents(ids);
        return result;
    }


}
