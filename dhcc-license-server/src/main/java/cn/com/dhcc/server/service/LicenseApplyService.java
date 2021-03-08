package cn.com.dhcc.server.service;

import cn.com.dhcc.server.common.dto.LicenseDto;
import cn.com.dhcc.server.common.entity.QueryResult;
import cn.com.dhcc.server.common.vo.LicenseVo;
import cn.com.dhcc.server.domain.LicenseApply;
import cn.com.dhcc.server.domain.LicenseManage;


public interface LicenseApplyService {

    void save(LicenseApply licenseApply);

    void update(LicenseDto licenseDto);

    QueryResult<LicenseVo> findByCondition(int page, int pageSize, LicenseDto licenseDto);

    LicenseApply findById(Long id);

    void deleteById(Long id);

    LicenseManage generateLicense(Long id);

}
