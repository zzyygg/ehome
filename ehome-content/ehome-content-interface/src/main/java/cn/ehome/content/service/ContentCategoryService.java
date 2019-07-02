package cn.ehome.content.service;

import cn.ehome.common.pojo.EasyUITreeNode;
import cn.ehome.common.util.EhomeResult;

import java.util.List;

public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCateGoryList(long parentId);

    EhomeResult addContentCategory(long parentId,String name);

    EhomeResult updateContentCategory(long id,String name);

    EhomeResult deleteContentCategoryById(long id);
}
