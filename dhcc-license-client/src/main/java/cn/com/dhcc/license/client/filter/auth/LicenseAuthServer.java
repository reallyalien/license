package cn.com.dhcc.license.client.filter.auth;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.dhcc.license.client.constant.CommonConstant;
import cn.com.dhcc.license.client.entity.enums.LicenseStatus;
import cn.com.dhcc.license.client.exception.LicenseException;
import cn.com.dhcc.license.client.util.AuthSourcedatUtil;
import cn.com.dhcc.license.client.util.license.License;
import cn.com.dhcc.license.client.util.license.LicenseEntity;


public class LicenseAuthServer {
	
	private static final Logger logger = LoggerFactory.getLogger(LicenseAuthServer.class);

	
    public static void main(String[] args) {
    	//licenseAuth();
    }

	public static void licenseAuth(String projectId) {
		
    	if(!new File(CommonConstant.sourcedat).exists()) {
    		AuthSourcedatUtil.createSourcedat(projectId,CommonConstant.sourcedat);		
    	}
		if(!new File(CommonConstant.licensedat).exists()) {
			logger.info(LicenseStatus.LICENSE_NOT_EXIST.message());
			throw new LicenseException(LicenseStatus.LICENSE_NOT_EXIST);
		}

		License license=new License();
		LicenseEntity e=license.loadLicense(new File(CommonConstant.licensedat));
    	String licensedata = new String(e.getData(),StandardCharsets.UTF_8);
    	if(!AuthSourcedatUtil.verifyAuthSource(licensedata,projectId)) {
    		throw new LicenseException(LicenseStatus.LICENSE_TOKEN_NOT_MATCH);
    	}
	}
	


}
