package cn.com.dhcc.server.domain;

import cn.com.dhcc.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 授权文件管理类，与授权申请类 1对1关系
 */
@Setter
@Getter
@Entity
@Table(name = "license_manage")
public class LicenseManage extends BaseEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //授权申请id
    @Column(name = "license_apply_id")
    private Long licenseApplyId;

    //授权状态
    @Column(name = "authorize_status")
    private int authorizeStatus;

    //授权生效时间
    @Column(name = "authorize_valid_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date authorizeValidTime;

    //授权失效时间
    @Column(name = "authorize_invalid_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date authorizeInvalidTime;

    //生成的授权文件，提供给用户下载使用
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "license_file")
    private byte[] licenseFile;

    //当用户缺失授权文件时，程序根据sourceFile再次生成license
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "source_file")
    private byte[] sourceFile;
}
