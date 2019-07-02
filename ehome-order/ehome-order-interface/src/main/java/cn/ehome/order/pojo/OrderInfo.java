package cn.ehome.order.pojo;

import cn.ehome.pojo.TbOrder;
import cn.ehome.pojo.TbOrderItem;
import cn.ehome.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @author:Jun
 * @time:2019/4/23
 */
public class OrderInfo extends TbOrder implements Serializable {

    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
