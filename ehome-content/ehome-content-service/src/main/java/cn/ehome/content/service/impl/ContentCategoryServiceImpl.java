package cn.ehome.content.service.impl;

import cn.ehome.common.pojo.EasyUITreeNode;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.content.service.ContentCategoryService;
import cn.ehome.mapper.TbContentCategoryMapper;
import cn.ehome.pojo.TbContentCategory;
import cn.ehome.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCateGoryList(long parentId) {
        // 根据parentid查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> catList = contentCategoryMapper.selectByExample(example);
        //转换成EasyUITreeNode的列表
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : catList) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            //添加到列表
            nodeList.add(node);
        }
        return nodeList;
    }

    @Override
    public EhomeResult addContentCategory(long parentId, String name) {
        TbContentCategory category = new TbContentCategory();
        category.setParentId(parentId);
        category.setName(name);
        category.setIsParent(false);
        category.setSortOrder(1);
        category.setStatus(1);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        contentCategoryMapper.insert(category);

        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parent.getIsParent()){
            parent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        return EhomeResult.ok(category);
    }

    @Override
    public EhomeResult updateContentCategory(long id,String name) {
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
        category.setName(name);
        contentCategoryMapper.updateByPrimaryKey(category);
        return EhomeResult.ok();
    }

    @Override
    public EhomeResult deleteContentCategoryById(long id){
        contentCategoryMapper.deleteByPrimaryKey(id);
        return EhomeResult.ok();
    }
}
