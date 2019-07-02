package cn.ehome.controller;

import cn.ehome.common.pojo.EasyUIDataGridResult;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.common.util.JsonUtil;
import cn.ehome.pojo.TbItem;
import cn.ehome.pojo.TbItemDesc;
import cn.ehome.service.ItemCatService;
import cn.ehome.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Guoxiujun
 * @date 2019/2/27
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 查询商品
     * @param itemId
     * @return
     */
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    /**
     * 商品列表展示
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    /**
     * 添加商品
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(value="/item/save", method= RequestMethod.POST)
    @ResponseBody
    public EhomeResult addItem(TbItem item, String desc) {
        EhomeResult result = itemService.addItem(item, desc);
        return result;
    }

    /**
     * 根据ID异步加载商品描述信息
     * @param id
     * @return
     */
    @RequestMapping("/rest/item/query/item/desc/{id}")
    @ResponseBody
    public TbItemDesc queryItemDescByID(@PathVariable long id){
        TbItemDesc itemDesc = itemService.getItemDescById(id);
        return itemDesc;
    }

    /**
     * 异步加载商品规格
     * @param id
     * @return
     */
    @RequestMapping("/rest/item/param/item/query/{id}")
    @ResponseBody
    public TbItem queryItemByID(@PathVariable long id){
        return itemService.getItemById(id);
    }

    /**
     * 修改商品信息
     * @param item
     * @param desc
     */
    @RequestMapping(value = "/rest/item/update", method= RequestMethod.POST)
    @ResponseBody
    public EhomeResult updateItem(TbItem item, String desc){
        EhomeResult result = itemService.editItem(item,desc);
        return result;
    }

    /**
     * 批量删除商品
     * @param ids
     * @return
     */
    @RequestMapping(value = "/rest/item/delete", method = RequestMethod.POST)
    @ResponseBody
    public EhomeResult deleteItems(String ids){
        EhomeResult result = itemService.deleteItems(ids);
        return result;
    }

    /**s
     * 下架
     * @param ids
     * @return
     */
    @RequestMapping(value = "/rest/item/instock",method = RequestMethod.POST)
    @ResponseBody
    public EhomeResult offShelfs(String ids){
        EhomeResult result = itemService.offShelf(ids);
        return result;
    }

    /**
     * 上架
     * @param ids
     * @return
     */
    @RequestMapping(value = "/rest/item/reshelf",method = RequestMethod.POST)
    @ResponseBody
    public EhomeResult onShelfs(String ids){
        EhomeResult result = itemService.onShelf(ids);
        return result;
    }
}
