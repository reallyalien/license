package cn.com.dhcc.server.repository;

import cn.com.dhcc.server.domain.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductVersionRepository extends JpaRepository<ProductVersion, Long>, JpaSpecificationExecutor<ProductVersion> {
}
