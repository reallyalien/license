package cn.com.dhcc.license.client.util.server.controller;



import java.io.IOException;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.dhcc.license.client.filter.annotation.LicenseAuth;
import cn.com.dhcc.license.client.util.server.FileUtil;




/**
 * @Author dhcc cz
 * @Description:
 * @Date 2021/3/5 14:52
 **/
@RestController
@RequestMapping("/file")
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
   
	@LicenseAuth(getProjectId = "test")
    @RequestMapping("/say")
    public String sayHello() {
    	return "hello";
    }
    

    
    
   
    @GetMapping("/license/download/{id}")
    public void downloadLicense(@PathVariable("id") String id, HttpServletResponse response, HttpServletRequest request) {
        ServletOutputStream outputStream = null;
        String fileName="license.dat";
        String license="test";
        try {
            byte[] bytes = license.getBytes();
            response.setCharacterEncoding("utf-8");
            // 下载使用"application/octet-stream"更标准
            response.setContentType("application/octet-stream");        
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + FileUtil.setFileDownloadHeader(request, fileName));
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            
        } catch (Exception e) {
        	logger.error("下载失败", e.getMessage());
        }finally {
        	try {
        		if(outputStream!=null) {
        			outputStream.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    
    
}
