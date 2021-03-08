package cn.com.dhcc.server.enums;

/**
 * 授权状态
 */
public enum AuthorizeStatus {
    UN_COMMITTED(0, "授权申请尚未提交"),
    COMMITTED(1, "已提交申请,产品尚未授权"),
    AUTHORIZED(2, "产品已授权"),
    AUTHORIZED_FAILED(3, "授权不通过"),
    AUTHORIZED_INVALID(4, "授权失效");

    private int code;
    private String msg;

    AuthorizeStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
