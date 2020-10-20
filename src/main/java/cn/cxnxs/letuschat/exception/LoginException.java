package cn.cxnxs.letuschat.exception;

/**
 * <p>登陆异常</p>
 *
 * @author mengjinyuan
 * @date 2020-10-14 15:03
 **/
public class LoginException extends RuntimeException{

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
