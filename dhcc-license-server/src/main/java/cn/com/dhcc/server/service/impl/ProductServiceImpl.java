package cn.com.dhcc.server.service.impl;

import cn.com.dhcc.common.utils.SecurityUtils;
import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.domain.Product;
import cn.com.dhcc.server.repository.ProductRepository;
import cn.com.dhcc.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Product product) {
        product.setCreateBy(SecurityUtils.getCurrentUsername());
        product.setUpdatedBy(SecurityUtils.getCurrentUsername());
        productRepository.save(product);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Product product) {
        product.setUpdatedBy(SecurityUtils.getCurrentUsername());
        productRepository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public QueryResult<Product> findByCondition(int page, int pageSize, Product product) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        Product product = null;
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            product = optional.get();
        }
        return product;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
