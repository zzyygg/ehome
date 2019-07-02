package cn.ehome.service.impl;

import cn.ehome.common.pojo.EasyUITreeNode;
import cn.ehome.mapper.TbItemCatMapper;
import cn.ehome.pojo.TbItemCat;
import cn.ehome.pojo.TbItemCatExample;
import cn.ehome.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ehome.service.ItemService;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guoxiujun
 * @date 2019/2/27
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private ItemService itemservice;

    @Override
    public List<EasyUITreeNode> getItemCatlist(long parentId) {
        //根据parentId查询子节点列表
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //创建返回结果List
        List<EasyUITreeNode> resultList = new ArrayList<>();
        //把列表转换成EasyUITreeNode列表
        for (TbItemCat tbItemCat : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            //设置属性
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            //添加到结果列表
            resultList.add(node);
        }
        //返回结果
        return resultList;
    }
}
