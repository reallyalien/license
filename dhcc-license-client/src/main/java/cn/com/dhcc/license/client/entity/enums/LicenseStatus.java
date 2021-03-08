package cn.com.dhcc.license.client.entity.enums;


public enum LicenseStatus {
	OK("200", "success"),
	FAIL("400", "fail"),
	
    LICENSE_NOT_EXIST("404", "License document does not exist, please apply for authorization"),
    LICENSE_INVALID("308", "The license is invalid. Please re authenticate the product authorization"),
    LICENSE_EXPIRED("309","The authorization document has expired. Please apply to the administrator for renewal"),
    LICENSE_TOKEN_NOT_MATCH("310","The product identity information in the authorization file does not match the identity information applying for the authorization"),
    TOKEN_CREATE_FAIL("312","Source file token generation failed"),
    POM_READ_FAIL("313","Project configuration file read failed"),
    MACHINE_CODE_MISSING("311","Program exception, machine code not found")
    

    ;
	


    private final String code;
    private final String message;


    
    LicenseStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String code() {
        return this.code;
    }


    public String message() {
        return this.message;
    }
    

}
