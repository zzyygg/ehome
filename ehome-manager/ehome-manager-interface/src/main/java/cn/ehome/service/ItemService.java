package cn.ehome.service;

import cn.ehome.common.pojo.EasyUIDataGridResult;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.pojo.TbItem;
import cn.ehome.pojo.TbItemDesc;


public interface ItemService {
    TbItem getItemById(long itemId);

    EasyUIDataGridResult getItemList(int page, int rows);

    EhomeResult addItem(TbItem item, String desc);

    TbItemDesc getItemDescById(long itemId);

    EhomeResult editItem(TbItem item,String desc);

    EhomeResult deleteItems(String ids);

    EhomeResult offShelf(String ids);

    EhomeResult onShelf(String ids);
}
