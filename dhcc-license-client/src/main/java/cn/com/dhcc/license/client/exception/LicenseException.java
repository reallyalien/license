package cn.com.dhcc.license.client.exception;

import cn.com.dhcc.license.client.entity.enums.LicenseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class LicenseException extends RuntimeException {

	private static final long serialVersionUID = -8325610971314223848L;

	private String code;

    private String message;

    private Object data;

    public LicenseException() {
        super();
    }
    
    public LicenseException(String message) {
        super(message);
    }
    
    public LicenseException(LicenseStatus data) {
    	 super(data.message());
    	 this.code = data.code();
         this.message = data.message();
    }
    
    public LicenseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public LicenseException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    public LicenseException(String message, Throwable cause) {
        super(message, cause);
    }


    
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
