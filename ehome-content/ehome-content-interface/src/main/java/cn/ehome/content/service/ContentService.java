package cn.ehome.content.service;

import cn.ehome.common.pojo.EasyUIDataGridResult;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.pojo.TbContent;
import cn.ehome.pojo.TbContentExample;

import java.util.List;

public interface ContentService {
    EasyUIDataGridResult getContentListByContentCategoryId(long contentCategoryId,int page,int rows);

    EhomeResult addContent(TbContent content);

    EhomeResult editContent(TbContent content);

    EhomeResult deleteContents(String ids);

    List<TbContent> getContentsByContentCategoryId(long cid);
}
