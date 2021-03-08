package cn.com.dhcc.server.service;

import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.domain.LicenseManage;

public interface LicenseManageService {

    void save(LicenseManage licenseManage);

    LicenseManage findById(Long id);

    QueryResult<LicenseManage> findAll(LicenseManage licenseManage);

    void update(LicenseManage licenseManage);

    void deleteById(Long id);

    LicenseManage findByLicenseApplyId(Long licenseApplyId);
}
