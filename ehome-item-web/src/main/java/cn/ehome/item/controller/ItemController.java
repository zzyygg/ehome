package cn.ehome.item.controller;

import cn.ehome.item.pojo.Item;
import cn.ehome.pojo.TbItem;
import cn.ehome.pojo.TbItemDesc;
import cn.ehome.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:Jun
 * @time:2019/4/5
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model) {
        //跟据商品id查询商品信息
        TbItem tbItem = itemService.getItemById(itemId);
        //把TbItem转换成Item对象
        Item item = new Item(tbItem);
        //根据商品id查询商品描述
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
        //把数据传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        return "item";
    }
}