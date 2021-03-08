package cn.com.dhcc.license.client.exception;



import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.dhcc.license.client.entity.LicenseResponseData;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@ControllerAdvice
public class LicenseExceptionHandler extends RuntimeException {

    private static final long serialVersionUID = -4089984233438343983L;
    


    @ExceptionHandler(value = LicenseException.class)
    @ResponseBody
    public LicenseResponseData exceptionHandler(HttpServletRequest request, LicenseException e) {
        log.error("发生业务异常！ 原因是：{}", e.getMessage(),e);
        return LicenseResponseData.fail(e.getCode(), e.getMessage(), e.getData());
    }


}
