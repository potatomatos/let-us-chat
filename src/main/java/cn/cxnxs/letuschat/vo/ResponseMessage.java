package cn.cxnxs.letuschat.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;

/**
 * <p>消息类</p>
 *
 * @author mengjinyuan
 * @date 2020-10-14 23:23
 **/
public class ResponseMessage implements Serializable {

    /**
     * 私聊
     */
    public static final Integer PRIVATE = 1;

    /**
     * 群聊
     */
    public static final Integer PUBLIC = 2;

    /**
     * 私聊系统消息
     */
    public static final Integer SYS_PUBLIC = 3;

    /**
     * 群组系统消息
     */
    public static final Integer SYS_PRIVATE = 4;


    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 发送人
     */
    private UserInfoVO fromUser;

    /**
     * 接收人
     */
    private UserInfoVO toUser;

    /**
     * 发送消息
     */
    private String msg;

    /**
     * 在线用户数
     */
    private int onlineCount;

    private List<String> list;

    /**
     * 创建时间
     */
    private Long timestamp=System.currentTimeMillis();

    public ResponseMessage(Integer type, UserInfoVO fromUser, UserInfoVO toUser, String msg, int onlineCount) {
        this.type = type;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.msg = msg;
        this.onlineCount = onlineCount;
    }


    public ResponseMessage(Integer type, UserInfoVO fromUser, UserInfoVO toUser, String msg, int onlineCount, List<String> list) {
        this.type = type;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.msg = msg;
        this.onlineCount = onlineCount;
        this.list = list;
    }

    /**
     * 聊天消息
     * 没有设置接收人 toUser ，视为群聊
     * 设置了接收人 toUser，视为私聊
     */
    public static String jsonStr(Integer type, UserInfoVO fromUser, UserInfoVO toUser, String msg, int onlineCount) {
        return JSON.toJSONString(new ResponseMessage(type, fromUser, toUser, msg, onlineCount));
    }

    public static String jsonStr(Integer type, UserInfoVO fromUser, UserInfoVO toUser, String msg, int onlineCount, List<String> list) {
        return JSON.toJSONString(new ResponseMessage(type, fromUser, toUser, msg, onlineCount, list));
    }

    public Integer getType() {
        return type;
    }

    public ResponseMessage setType(Integer type) {
        this.type = type;
        return this;
    }

    public UserInfoVO getFromUser() {
        return fromUser;
    }

    public ResponseMessage setFromUser(UserInfoVO fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public UserInfoVO getToUser() {
        return toUser;
    }

    public ResponseMessage setToUser(UserInfoVO toUser) {
        this.toUser = toUser;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseMessage setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public ResponseMessage setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
        return this;
    }

    public List<String> getList() {
        return list;
    }

    public ResponseMessage setList(List<String> list) {
        this.list = list;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
