package cn.ehome.search.mapper;

import cn.ehome.common.pojo.SearchItem;

import java.util.List;

/**
 * @author:Jun
 * @time:2019/3/25
 */
public interface ItemMapper {

    List<SearchItem> getItemList();

    SearchItem getItemById(long itemId);
}
