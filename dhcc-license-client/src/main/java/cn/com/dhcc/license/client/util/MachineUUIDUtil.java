package cn.com.dhcc.license.client.util;

import java.util.Scanner;

import cn.com.dhcc.license.client.entity.enums.LicenseStatus;
import cn.com.dhcc.license.client.exception.LicenseException;

public class MachineUUIDUtil {


	public static String getCPUID() {
		Scanner sc = null;
		try {
			Process process = Runtime.getRuntime().exec(new String[] { "wmic", "cpu", "get", "ProcessorId" });
			process.getOutputStream().close();
			sc = new Scanner(process.getInputStream());
			sc.next();
			return sc.next();
		} catch (Exception e) {
			throw new LicenseException(LicenseStatus.MACHINE_CODE_MISSING.code(),LicenseStatus.MACHINE_CODE_MISSING.message(),e.getMessage());
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}


}
