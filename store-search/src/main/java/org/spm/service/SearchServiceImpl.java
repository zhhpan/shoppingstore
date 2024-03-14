package org.spm.service;

import org.spm.mapper.SearchMapper;
import org.spm.param.SearchParam;
import org.spm.pojo.Product;
import org.spm.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private SearchMapper searchMapper;
    /**
     * @param searchParam
     * @return
     */
    @Override
    public R search(SearchParam searchParam) {
        List<Product> ids = searchMapper.searchByName(searchParam.getSearchName());
        return R.ok("Search Successfully",ids);
    }
}
