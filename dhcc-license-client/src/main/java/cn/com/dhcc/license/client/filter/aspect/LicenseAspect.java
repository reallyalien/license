
package cn.com.dhcc.license.client.filter.aspect;


import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.com.dhcc.license.client.filter.annotation.LicenseAuth;
import cn.com.dhcc.license.client.filter.auth.LicenseAuthServer;




/**
 * @author DHCC cz
 * @date 2021-3-5 
 * @description 授权校验切面
 */
@Aspect
@Component
public class LicenseAspect {

    private static final Logger log = LoggerFactory.getLogger(LicenseAspect.class);

    @Pointcut("@annotation(cn.com.dhcc.license.client.filter.annotation.LicenseAuth)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void authfilter(JoinPoint joinPoint) {
    	log.info("license  verification");
    	LicenseAuth point=getAnnotationLog(joinPoint);
    	LicenseAuthServer.licenseAuth(point.getProjectId());
    }
    
    /**
     * 是否存在注解
     *
     * @param joinPoint 连接点
     */
    
	private LicenseAuth getAnnotationLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method != null) {
            return method.getAnnotation(LicenseAuth.class);
        }
        return null;
    }


}
