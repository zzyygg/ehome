package cn.ehome.controller;

import cn.ehome.common.pojo.EasyUITreeNode;
import cn.ehome.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Guoxiujun
 * @date 2019/3/10
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value = "id",defaultValue = "0") long parentId){
        List<EasyUITreeNode> list = itemCatService.getItemCatlist(parentId);
        return list;
    }
}
