package cn.com.dhcc.server.enums;

/**
 * 授权类型单位
 */
public enum AuthorizeType {

    H(1, "天"),
    M(2, "月"),
    Y(3, "年");

    private int code;
    private String type;

    AuthorizeType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
