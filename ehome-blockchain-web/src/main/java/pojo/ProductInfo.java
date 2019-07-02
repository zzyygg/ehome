package pojo;

import cn.ehome.pojo.TbItem;

import java.io.Serializable;

public class ProductInfo implements Serializable {

    private TbItem item;
    private String operator;
    private String upDate;

    public ProductInfo(TbItem item, String operator, String upDate) {
        this.item = item;
        this.operator = operator;
        this.upDate = upDate;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "item=" + item +
                ", operator='" + operator + '\'' +
                ", upDate=" + upDate +
                '}';
    }
}
