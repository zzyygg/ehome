package cn.ehome.cart.service.impl;

import cn.ehome.cart.service.CartService;
import cn.ehome.common.jedis.JedisClient;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.common.util.JsonUtil;
import cn.ehome.mapper.TbItemMapper;
import cn.ehome.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:Jun
 * @time:2019/4/20
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;
    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public EhomeResult addCart(long userId, long itemId, int num) {
        //向redis中添加购物车。
        //数据类型是hash key：用户id field：商品id value：商品信息
        //判断商品是否存在
        Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
        //如果存在数量相加
        if (hexists) {
            String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
            //把json转换成TbItem
            TbItem item = JsonUtil.jsonToPojo(json, TbItem.class);
            item.setNum(item.getNum() + num);
            //写回redis
            jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtil.objectToJson(item));
            return EhomeResult.ok();
        }
        //如果不存在，根据商品id取商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        //设置购物车数据量
        item.setNum(num);
        //取一张图片
        String image = item.getImage();
        if (StringUtils.isNotBlank(image)) {
            item.setImage(image.split(",")[0]);
        }
        //添加到购物车列表
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtil.objectToJson(item));
        return EhomeResult.ok();
    }

    @Override
    public EhomeResult mergeCart(long userId, List<TbItem> itemList) {
        //遍历商品列表
        //把列表添加到购物车。
        //判断购物车中是否有此商品
        //如果有，数量相加
        //如果没有添加新的商品
        for (TbItem tbItem : itemList) {
            addCart(userId, tbItem.getId(), tbItem.getNum());
        }
        //返回成功
        return EhomeResult.ok();
    }

    @Override
    public List<TbItem> getCartList(long userId) {
        //根据用户id查询购车列表
        List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for (String string : jsonList) {
            //创建一个TbItem对象
            TbItem item = JsonUtil.jsonToPojo(string, TbItem.class);
            //添加到列表
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public EhomeResult updateCartNum(long userId, long itemId, int num) {
        //从redis中取商品信息
        String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        //更新商品数量
        TbItem tbItem = JsonUtil.jsonToPojo(json, TbItem.class);
        tbItem.setNum(num);
        //写入redis
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtil.objectToJson(tbItem));
        return EhomeResult.ok();
    }

    @Override
    public EhomeResult deleteCartItem(long userId, long itemId) {
        // 删除购物车商品
        jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
        return EhomeResult.ok();
    }

    @Override
    public EhomeResult clearCartItem(Long userId) {
        //删除购物车信息
        jedisClient.del(REDIS_CART_PRE + ":" + userId);
        return EhomeResult.ok();
    }
}
