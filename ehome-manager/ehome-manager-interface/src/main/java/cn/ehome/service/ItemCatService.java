package cn.ehome.service;

import cn.ehome.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author Guoxiujun
 * @date 2019/2/27
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatlist(long parentId);
}
