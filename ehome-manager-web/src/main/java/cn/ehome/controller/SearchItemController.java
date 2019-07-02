package cn.ehome.controller;

import cn.ehome.common.util.EhomeResult;
import cn.ehome.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:Jun
 * @time:2019/3/25
 */
@Controller
public class SearchItemController {
    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public EhomeResult importItemList(){
        EhomeResult result = searchItemService.importAllItems();
        return result;
    }
}
