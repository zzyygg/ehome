package cn.ehome.service.impl;

import cn.ehome.common.pojo.EasyUIDataGridResult;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.common.util.IDUtil;
import cn.ehome.mapper.TbItemDescMapper;
import cn.ehome.mapper.TbItemMapper;
import cn.ehome.pojo.TbItem;
import cn.ehome.pojo.TbItemDesc;
import cn.ehome.pojo.TbItemExample;
import cn.ehome.pojo.TbItemExample.Criteria;
import cn.ehome.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.List;
import java.util.Date;

/**
 * @author Guoxiujun
 * @date 2019/2/27
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Resource
    private Destination topicDestination;

    @Override
    public TbItem getItemById(long itemId) {
        //根据主键查询
        //TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        TbItemExample example = new TbItemExample();
        Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andIdEqualTo(itemId);
        //执行查询
        List<TbItem> list = itemMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        //取分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        //取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public EhomeResult addItem(TbItem item, String desc) {
        //生成商品id
        final long itemId = IDUtil.genItemId();
        //补全item的属性
        item.setId(itemId);
        //1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        itemMapper.insert(item);
        //创建一个商品描述表对应的pojo对象。
        TbItemDesc itemDesc = new TbItemDesc();
        //补全属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId+"");
                System.out.println("发送消息id="+itemId);
                return textMessage;
            }
        });
        //返回成功
        return EhomeResult.ok();
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {
        return itemDescMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EhomeResult editItem(TbItem item, String desc) {
        TbItem oldItem = getItemById(item.getId());
        item.setStatus(oldItem.getStatus());
        item.setCreated(oldItem.getCreated());
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKey(item);
        //创建一个商品描述表对应的pojo对象。
        TbItemDesc itemDesc = new TbItemDesc();
        //补全属性
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(oldItem.getCreated());
        itemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
        final long itemId = item.getId();
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId+"");
                System.out.println("发送消息id="+itemId);
                return textMessage;
            }
        });
        return EhomeResult.ok();
    }

    @Override
    public EhomeResult deleteItems(String ids){
        if(ids != null && !("".equals(ids))){
            String[] allIds = ids.split(",");
            for(String id : allIds){
                itemMapper.deleteByPrimaryKey(Long.valueOf(id));
                itemDescMapper.deleteByPrimaryKey(Long.valueOf(id));
            }
            return EhomeResult.ok();
        }
        return null;
    }

    /**
     * 下架
     * @param ids
     * @return
     */
    @Override
    public EhomeResult offShelf(String ids) {
        String[] allIDs = ids.split(",");
            for(String id : allIDs){
                TbItem item = itemMapper.selectByPrimaryKey(Long.valueOf(id));
                item.setStatus((byte)2);
                itemMapper.updateByPrimaryKey(item);
            }
       return EhomeResult.ok();
    }

    /**
     * 上架
     * @param ids
     * @return
     */
    @Override
    public EhomeResult onShelf(String ids) {
        String[] allIDs = ids.split(",");
        for (String id : allIDs) {
            TbItem item = itemMapper.selectByPrimaryKey(Long.valueOf(id));
            item.setStatus((byte) 1);
            itemMapper.updateByPrimaryKey(item);
        }
        return EhomeResult.ok();
    }

}
