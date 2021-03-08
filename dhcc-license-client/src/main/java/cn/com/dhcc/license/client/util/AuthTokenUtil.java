package cn.com.dhcc.license.client.util;

import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;

import cn.com.dhcc.license.client.entity.enums.LicenseStatus;
import cn.com.dhcc.license.client.exception.LicenseException;

public class AuthTokenUtil {


	
	public static String getTokenEncoded(String projectId) {
		if(StringUtils.isBlank(projectId)) {
			projectId=MachineUUIDUtil.getCPUID();
		}
		try {
			EncodeUtil encryp = new EncodeUtil();
			JSONObject jSONObject = new JSONObject();
			jSONObject.put("machineId", MachineUUIDUtil.getCPUID());
			jSONObject.put("projectId", projectId);
			return encryp.encrypt(jSONObject.toJSONString());
		} catch (Exception e) {
			throw new LicenseException(LicenseStatus.FAIL.code(),e.getMessage());
		}
	}

	public static boolean compareTokenLicense(String token, String licenseData) {
		String result = "";
		if (StringUtils.isBlank(token)) {
			throw new LicenseException(LicenseStatus.FAIL.code(), "Auth token cannot be empty");
		}
		if (StringUtils.isBlank(licenseData)) {
			throw new LicenseException(LicenseStatus.FAIL.code(), "License data cannot be empty");
		}
		try {
			EncodeUtil encryp = new EncodeUtil();
			result =encryp.decrypt(token);
			if (licenseData.equals(result)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new LicenseException(LicenseStatus.FAIL.code(), e.getMessage());
		}

	}

	public static void main(String[] args) throws Exception {

	}

}
