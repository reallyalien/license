package cn.com.dhcc.license.client.entity;

import lombok.Data;


@Data
public class LicenseResponseData {

    private String code;

    private String message;

    private Object data;


    public LicenseResponseData() {
    }

    public LicenseResponseData(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public LicenseResponseData(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public static LicenseResponseData fail(String code, String message, Object data) {
        return responseData(code, message, data);
    }

    private static LicenseResponseData responseData(String code, String message, Object data) {
    	LicenseResponseData responseData = new LicenseResponseData();
        responseData.setCode(code);
        responseData.setMessage(message);
        responseData.setData(data);
        return responseData;
    }

}
