package cn.com.dhcc.license.client.filter.annotation;




import java.lang.annotation.*;


/**
 * @author DHCC cz
 * @date 2021-3-5 
 * @description 授权校验拦截注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LicenseAuth {
	 String getProjectId() default ""; //控制并发最大数量
}
