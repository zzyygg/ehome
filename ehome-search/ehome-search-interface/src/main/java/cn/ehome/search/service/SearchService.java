package cn.ehome.search.service;

import cn.ehome.common.pojo.SearchResult;

/**
 * @author:Jun
 * @time:2019/3/25
 */
public interface SearchService {

    SearchResult search(String keyword, int page, int rows)  throws Exception;

}
