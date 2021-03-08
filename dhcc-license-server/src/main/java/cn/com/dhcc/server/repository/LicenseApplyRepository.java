package cn.com.dhcc.server.repository;


import cn.com.dhcc.server.domain.LicenseApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface LicenseApplyRepository extends JpaRepository<LicenseApply, Long>, JpaSpecificationExecutor<LicenseApply> {

    @Query(value = "SELECT " +
            "a.id AS id," +
            "a.user_name AS userName," +
            "a.project_name AS projectName," +
            "a.apply_product AS applyProduct," +
            "a.apply_product_version AS applyProductVersion," +
            "a.responsible_sale AS responsibleSale," +
            "a.comment AS `comment`," +
            "a.create_by AS createBy," +
            "a.create_time AS createTime," +
            "a.update_time AS updateTime," +
            "a.update_by AS updateBy," +
            "m.authorize_status AS authorizeStatus," +
            "m.authorize_valid_time AS authorizeValidTime," +
            "m.authorize_invalid_time AS authorizeInvalidTime " +
            "FROM license_apply  a " +
            "LEFT JOIN license_manage m on a.id = m.license_apply_id " +
            "WHERE " +
            "if(?1!= '' && !ISNULL(?1),a.user_name = ?1,1 = 1) AND " +
            "if(?2!= '' && !ISNULL(?2),a.project_name = ?2,1 = 1) AND " +
            "if(?3!= '' && !ISNULL(?3),a.apply_product = ?3,1 = 1) AND " +
            "if(?4!= '' && !ISNULL(?4),a.apply_product_version = ?4,1 = 1) AND " +
            "if(?5!= '' && !ISNULL(?5),a.responsible_sale = ?5,1 = 1) AND " +
            "m.authorize_status = ?6 " +
            "ORDER BY a.create_time DESC " +
            "LIMIT ?7,?8  ", nativeQuery = true)
    List<Map<String, Object>> findByCondition(String userName,
                                              String projectName,
                                              String applyProduct,
                                              String applyProductVersion,
                                              String responsibleSale,
                                              int authorizeStatus,
                                              int limit,
                                              int pageSize);

    @Query(value = "SELECT " +
            "COUNT(a.id) " +
            "FROM license_apply  a " +
            "LEFT JOIN license_manage m on a.id = m.license_apply_id " +
            "WHERE " +
            "if(?1!= '' && !ISNULL(?1),a.user_name = ?1,1 = 1) AND " +
            "if(?2!= '' && !ISNULL(?1),a.project_name = ?2,1 = 1) AND " +
            "if(?3!= '' && !ISNULL(?1),a.apply_product = ?3,1 = 1) AND " +
            "if(?4!= '' && !ISNULL(?1),a.apply_product_version = ?4,1 = 1) AND " +
            "if(?5!= '' && !ISNULL(?1),a.responsible_sale = ?5,1 = 1) AND " +
            "m.authorize_status = ?6 ", nativeQuery = true)
    long count(String userName,
               String projectName,
               String applyProduct,
               String applyProductVersion,
               String responsibleSale,
               int authorizeStatus);

}
