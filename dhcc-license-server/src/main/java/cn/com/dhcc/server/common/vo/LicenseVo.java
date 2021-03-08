package cn.com.dhcc.server.common.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.Date;

@Data
public class LicenseVo implements Serializable {

    private Long id;

    //用户名称
    private String userName;

    //项目名称
    private String projectName;

    //申请产品
    private String applyProduct;

    //申请产品版本号
    private String applyProductVersion;

    //责任销售
    private String responsibleSale;

    //授权类型
    private int authorizeType;

    //授权时效
    private int authorizeTime;

    //备注
    private String comment;

    //授权状态
    private int authorizeStatus;

    //授权生效时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date authorizeValidTime;

    //授权失效时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date authorizeInvalidTime;


}
