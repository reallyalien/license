package cn.com.dhcc.server.service;

import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.domain.Product;
import cn.com.dhcc.server.domain.Project;


public interface ProductService {

    void save(Product product);

    void update(Product product);

    QueryResult<Product> findByCondition(int page, int pageSize, Product product);

    Product findById(Long id);

    void deleteById(Long id);
}
