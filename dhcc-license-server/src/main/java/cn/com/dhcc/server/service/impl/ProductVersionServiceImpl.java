package cn.com.dhcc.server.service.impl;

import cn.com.dhcc.common.utils.SecurityUtils;
import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.domain.ProductVersion;
import cn.com.dhcc.server.domain.Project;
import cn.com.dhcc.server.repository.ProductVersionRepository;
import cn.com.dhcc.server.service.ProductVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductVersionServiceImpl implements ProductVersionService {

    @Autowired
    private ProductVersionRepository productVersionRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(ProductVersion productVersion) {
        productVersion.setCreateBy(SecurityUtils.getCurrentUsername());
        productVersion.setUpdatedBy(SecurityUtils.getCurrentUsername());
        productVersionRepository.save(productVersion);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(ProductVersion productVersion) {
        productVersion.setUpdatedBy(SecurityUtils.getCurrentUsername());
        productVersionRepository.save(productVersion);
    }

    @Override
    @Transactional(readOnly = true)
    public QueryResult<ProductVersion> findByCondition(int page, int pageSize, ProductVersion productVersion) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductVersion findById(Long id) {
        ProductVersion productVersion = null;
        Optional<ProductVersion> optional = productVersionRepository.findById(id);
        if (optional.isPresent()) {
            productVersion = optional.get();
        }
        return productVersion;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteById(Long id) {
        productVersionRepository.deleteById(id);
    }
}
