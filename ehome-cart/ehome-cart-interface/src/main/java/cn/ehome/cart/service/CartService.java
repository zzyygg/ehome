package cn.ehome.cart.service;

import cn.ehome.common.util.EhomeResult;
import cn.ehome.pojo.TbItem;

import java.util.List;

/**
 * @author:Jun
 * @time:2019/4/20
 */
public interface CartService {
    EhomeResult addCart(long userId, long itemId, int num);
    EhomeResult mergeCart(long userId, List<TbItem> itemList);
    List<TbItem> getCartList(long userId);
    EhomeResult updateCartNum(long userId, long itemId, int num);
    EhomeResult deleteCartItem(long userId, long itemId);
    EhomeResult clearCartItem(Long userId);
}
