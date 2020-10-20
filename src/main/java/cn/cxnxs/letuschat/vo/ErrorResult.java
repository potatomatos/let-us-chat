package cn.cxnxs.letuschat.vo;

/**
 * <p></p>
 *
 * @author mengjinyuan
 * @date 2020-10-13 23:57
 **/
public class ErrorResult {
    private Integer code;

    private String message;

    private String errors;

    public ErrorResult(Integer code, String message, String errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ErrorResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", errors='" + errors + '\'' +
                '}';
    }
}
