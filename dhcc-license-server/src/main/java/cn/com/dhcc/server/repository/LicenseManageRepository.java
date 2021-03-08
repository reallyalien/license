package cn.com.dhcc.server.repository;


import cn.com.dhcc.server.domain.LicenseManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LicenseManageRepository extends JpaRepository<LicenseManage, Long>, JpaSpecificationExecutor<LicenseManage> {

    void deleteByLicenseApplyId(Long id);

    LicenseManage findByLicenseApplyId(Long licenseApplyId);
}
