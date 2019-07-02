package cn.ehome.controller;

import cn.ehome.common.pojo.EasyUITreeNode;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.content.service.ContentCategoryService;
import cn.ehome.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCatgoryController {
    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatgoryList(@RequestParam(name="id",defaultValue = "0")long parnetId){
        List<EasyUITreeNode> list = contentCategoryService.getContentCateGoryList(parnetId);
        return list;
    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    public EhomeResult createContentCategory(long parentId,String name){
        EhomeResult result = contentCategoryService.addContentCategory(parentId,name);
        return result;
    }

    @RequestMapping("/content/category/update")
    @ResponseBody
    public EhomeResult updateContentCategory(long id,String name){
        EhomeResult result = contentCategoryService.updateContentCategory(id,name);
        return result;
    }

    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public EhomeResult deleteContentCategory(long id){
        EhomeResult result = contentCategoryService.deleteContentCategoryById(id);
        return result;
    }
}
