package cn.com.dhcc.license.client.util;

import java.io.File;


import cn.com.dhcc.license.client.constant.CommonConstant;

public class AuthSourcedatUtil {

	public static boolean verifyAuthSource(String licensedata, String projectId) {
		if (!new File(CommonConstant.sourcedat).exists()) {
			createSourcedat(projectId, CommonConstant.sourcedat);
		}
		return AuthTokenUtil.compareTokenLicense(WriterReadFileUtil.readFileContent(CommonConstant.sourcedat),
				licensedata);
	}

	public static boolean createSourcedat(String projectId, String dir) {
		return WriterReadFileUtil.createSourceFile(AuthTokenUtil.getTokenEncoded(projectId), dir);
	}

}
