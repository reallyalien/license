package cn.com.dhcc.license.client.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class WriterReadFileUtil {

	private static final Logger logger = LoggerFactory.getLogger(WriterReadFileUtil.class);

	
	public static Boolean createSourceFile(String data, String filePath) {
		Writer write = null;
		// 标记文件生成是否成功
		Boolean flag = false;
		// 生成json格式文件
		try {
			// 保证创建一个新文件
			File file = new File(filePath);
			if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
				file.getParentFile().mkdirs();
			}
			if (file.exists()) { // 如果已存在,删除旧文件
				file.delete();
			}
			file.createNewFile();
			// 将格式化后的字符串写入文件
			write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			write.write(data);
			write.flush();
			write.close();
			if (file.exists()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("source.dat create successful,file url 【{}】",filePath);
		return flag;
	}
	
	
	public static String readFileContent(String filePath) {
	    File file = new File(filePath);
	    BufferedReader reader = null;
	    StringBuffer sbf = new StringBuffer();
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempStr;
	        while ((tempStr = reader.readLine()) != null) {
	            sbf.append(tempStr);
	        }
	        reader.close();
	        return sbf.toString();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    return sbf.toString();
	}


}
