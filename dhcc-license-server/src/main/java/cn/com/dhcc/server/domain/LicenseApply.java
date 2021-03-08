package cn.com.dhcc.server.domain;

import cn.com.dhcc.common.base.BaseEntity;
import cn.com.dhcc.server.enums.AuthorizeType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 授权申请
 */
@Entity
@Setter
@Getter
@Table(name = "license_apply")
public class LicenseApply extends BaseEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //用户名称
    @Column(name = "user_name")
    private String userName;

    //项目名称
    @Column(name = "project_name")
    private String projectName;

    //申请产品
    @Column(name = "apply_product")
    private String applyProduct;

    //申请产品版本号
    @Column(name = "apply_product_version")
    private String applyProductVersion;

    //责任销售
    @Column(name = "responsible_sale")
    private String responsibleSale;

    /**
     * 授权类型
     * @see AuthorizeType
     */
    @Column(name = "authorize_type")
    private int authorizeType;

    //授权时效
    @Column(name = "authorize_time")
    private int authorizeTime;

    //认证文件
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "authentication_file")
    private byte[] authenticationFile;

    //备注
    @Column(name = "comment")
    private String comment;
}
