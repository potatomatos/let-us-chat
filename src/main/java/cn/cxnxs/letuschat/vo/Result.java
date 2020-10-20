package cn.cxnxs.letuschat.vo;



import org.omg.CORBA.NO_PERMISSION;

import java.io.Serializable;

/**
 * Description:接口通用规范
 * @author mengjinyuan
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer code = ResultEnum.SUCCESS.getCode();
    private String result = ResultEnum.SUCCESS.getResult();
    private String info;
    private T data;
    private String stackInfo;

    public Result() {
    }

    private Result(Integer code, String result, String info, T data, String stackInfo) {
        super();
        this.code = code;
        this.result = result;
        this.info = info;
        this.data = data;
        this.stackInfo = stackInfo;
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getResult(),
                ResultEnum.SUCCESS.getInfo(), null, null);
    }

    public static <T> Result<T> success(String info) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getResult(), info, null, null);
    }

    public static <T> Result<T> success(String info, T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getResult(), info, data,
                null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getResult(), ResultEnum.SUCCESS.getInfo(), data,
                null);
    }

    public static <T> Result<T> failure() {
        return new Result<>(ResultEnum.SYSTEM_EXCEPTION.getCode(), ResultEnum.SYSTEM_EXCEPTION.getResult(),
                ResultEnum.SYSTEM_EXCEPTION.getInfo(), null, null);
    }

    public static <T> Result<T> failure(String info) {
        return new Result<>(ResultEnum.SYSTEM_EXCEPTION.getCode(), ResultEnum.SYSTEM_EXCEPTION.getResult(), info, null,
                null);
    }

    public static <T> Result<T> failure(String info, String stackInfo) {
        return new Result<>(ResultEnum.SYSTEM_EXCEPTION.getCode(), ResultEnum.SYSTEM_EXCEPTION.getResult(), info, null,
                stackInfo);
    }

    public static <T> Result<T> failure(Integer code, String result, String info, String stackInfo) {
        return new Result<>(code, result, info, null, stackInfo);
    }


    public static <T> Result<T> failure(ResultEnum resultEnum, String info, String stackInfo) {
        if (resultEnum.equals(ResultEnum.SUCCESS)) {
            return new Result<>(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getResult(), info,
                    null, stackInfo);
        } else {
            return new Result<>(resultEnum.getCode(), resultEnum.getResult(), info, null,
                    stackInfo);
        }
    }

    public static <T> Result<T> failure(ResultEnum resultEnum, String stackInfo) {
        if (resultEnum.equals(ResultEnum.SUCCESS)) {
            return new Result<>(ResultEnum.UNKNOWN_ERROR.getCode(), ResultEnum.UNKNOWN_ERROR.getResult(), ResultEnum.UNKNOWN_ERROR.getInfo(),
                    null, stackInfo);
        } else {
            return new Result<>(resultEnum.getCode(), resultEnum.getResult(), resultEnum.getInfo(), null,
                    stackInfo);
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStackInfo() {
        return stackInfo;
    }

    public void setStackInfo(String stackInfo) {
        this.stackInfo = stackInfo;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", result='" + result + '\'' +
                ", info='" + info + '\'' +
                ", data=" + data +
                ", stackInfo='" + stackInfo + '\'' +
                '}';
    }

    public enum ResultEnum {
        SUCCESS(20000, "成功", "success"),
        SYSTEM_EXCEPTION(50000, "系统内部异常", "failure"),
        UNKNOWN_ERROR(-1, "未知错误", "failure"),
        ILLEGAL_TOKEN(50008, "token错误", "failure"),
        LOGIN_ERROR(50010, "登陆异常", "failure"),
        DUPLICATION_LOGIN(50012, "重复登录", "failure"),
        TOKEN_EXPIRED(50014, "token过期", "failure"),
        ERROR_404(40000, "404 Not Found", "failure"),
        NO_PERMISSION(43000,"请求无权限","no permission");

        /**
         * 状态码
         */
        private Integer code;
        /**
         * 错误信息
         */
        private String info;
        /**
         * 显示结果提示
         */
        private String result;

        ResultEnum(Integer code, String info, String result) {
            this.code = code;
            this.info = info;
            this.result = result;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public String getResult() {
            return result;
        }
    }
}
