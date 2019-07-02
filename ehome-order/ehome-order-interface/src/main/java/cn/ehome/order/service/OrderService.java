package cn.ehome.order.service;

import cn.ehome.common.util.EhomeResult;
import cn.ehome.order.pojo.OrderInfo;
import cn.ehome.pojo.TbOrderItem;

/**
 * @author:Jun
 * @time:2019/4/23
 */
public interface OrderService {
    EhomeResult createOrder(OrderInfo orderInfo);
    TbOrderItem getOrderItemByOrderId(String orderId);
}
