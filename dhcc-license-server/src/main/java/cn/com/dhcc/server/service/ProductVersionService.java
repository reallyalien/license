package cn.com.dhcc.server.service;

import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.domain.ProductVersion;
import cn.com.dhcc.server.domain.Project;


public interface ProductVersionService {

    void save(ProductVersion ProductVersion);

    void update(ProductVersion productVersion);

    QueryResult<ProductVersion> findByCondition(int page, int pageSize, ProductVersion productVersion);

    ProductVersion findById(Long id);

    void deleteById(Long id);

}
