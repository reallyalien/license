package cn.com.dhcc.license.client.util;

import java.io.File;
import java.io.FileReader;


import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import cn.com.dhcc.license.client.entity.enums.LicenseStatus;
import cn.com.dhcc.license.client.exception.LicenseException;


public class ProjectUUIDUtil {


	public static String getPrejectID(){
    	String rootPath = System.getProperty("user.dir");                
    	MavenXpp3Reader reader = new MavenXpp3Reader();
    	String myPom = rootPath + File.separator + "pom.xml";
    	Model model = null;
		try {
			model = reader.read(new FileReader(myPom));
		} catch (Exception e) {
			throw new LicenseException(LicenseStatus.POM_READ_FAIL.code(),LicenseStatus.POM_READ_FAIL.message(),e.getMessage());
		}
    	return model.getId();
	}



}
