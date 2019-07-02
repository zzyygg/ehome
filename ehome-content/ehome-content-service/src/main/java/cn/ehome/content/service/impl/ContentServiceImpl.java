package cn.ehome.content.service.impl;

import cn.ehome.common.jedis.JedisClient;
import cn.ehome.common.pojo.EasyUIDataGridResult;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.common.util.JsonUtil;
import cn.ehome.content.service.ContentService;
import cn.ehome.mapper.TbContentMapper;
import cn.ehome.pojo.TbContent;
import cn.ehome.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Override
    public EasyUIDataGridResult getContentListByContentCategoryId(long contentCategoryId,int page,int rows) {
        if(contentCategoryId==0){
            return getAllContent(page,rows);
        }
        //设置分页信息
        PageHelper.startPage(page,rows);
        //执行查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCategoryId);
        List<TbContent> contentList = contentMapper.selectByExample(example);
        //返回一个对象值
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(contentList);
        //区分有结果
        PageInfo<TbContent> pageInfo = new PageInfo<>(contentList);
        //取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public EhomeResult addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        jedisClient.hdel(CONTENT_LIST,content.getCategoryId()+"");
        return EhomeResult.ok();
    }

    @Override
    public EhomeResult editContent(TbContent content) {
        TbContent oldContent = contentMapper.selectByPrimaryKey(content.getId());
        content.setCreated(oldContent.getCreated());
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKey(content);
        jedisClient.hdel(CONTENT_LIST,content.getCategoryId()+"");
        return EhomeResult.ok();
    }

    @Override
    public EhomeResult deleteContents(String ids){
        if(ids != null && !("".equals(ids))){
            String[] allIds = ids.split(",");
            for(String id : allIds){
                TbContent content = contentMapper.selectByPrimaryKey(Long.valueOf(id));
                contentMapper.deleteByPrimaryKey(Long.valueOf(id));
                jedisClient.hdel(CONTENT_LIST,content.getCategoryId()+"");
            }
            return EhomeResult.ok();
        }
        return null;
    }

    public EasyUIDataGridResult getAllContent(int page,int rows){
        PageHelper.startPage(page,rows);
        //执行查询
        TbContentExample example = new TbContentExample();
        List<TbContent> list = contentMapper.selectByExample(example);
        //创建一个返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        //取分页结果
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        //取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    /**
     * portal-web用的
     * @param cid
     * @return
     */
    @Override
    public List<TbContent> getContentsByContentCategoryId(long cid){
        String json = jedisClient.hget(CONTENT_LIST, cid + "");
        if(StringUtils.isNotBlank(json)){
            List<TbContent> list = JsonUtil.jsonToList(json,TbContent.class);
            return list;
        }
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
        try {
            jedisClient.hset(CONTENT_LIST, cid + "", JsonUtil.objectToJson(list));
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
