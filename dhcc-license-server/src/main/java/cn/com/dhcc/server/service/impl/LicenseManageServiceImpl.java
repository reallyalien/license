package cn.com.dhcc.server.service.impl;

import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.domain.LicenseManage;
import cn.com.dhcc.server.repository.LicenseManageRepository;
import cn.com.dhcc.server.service.LicenseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LicenseManageServiceImpl implements LicenseManageService {

    @Autowired
    private LicenseManageRepository licenseManageRepository;

    @Override
    public void save(LicenseManage licenseManage) {

    }

    @Override
    public LicenseManage findById(Long id) {
        return null;
    }

    @Override
    public QueryResult<LicenseManage> findAll(LicenseManage licenseManage) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(LicenseManage licenseManage) {
        licenseManageRepository.save(licenseManage);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public LicenseManage findByLicenseApplyId(Long licenseApplyId) {
        return licenseManageRepository.findByLicenseApplyId(licenseApplyId);
    }
}
