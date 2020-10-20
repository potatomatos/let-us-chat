package cn.cxnxs.letuschat.vo;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>消息类</p>
 *
 * @author mengjinyuan
 * @date 2020-10-14 23:23
 **/
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RequestMessage implements Serializable {

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
     * 好友列表更新
     */
    public static final Integer SYS_FRIEND_UPDATE = 5;

    /**
     * 群用户列表更新
     */
    public static final Integer SYS_GROUP_MEMBER_UPDATE = 6;


    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 发送人
     */
    private Integer fromUser;

    /**
     * 接收人
     */
    private Integer toUser;

    /**
     * 发送消息
     */
    private String msg;

    /**
     * 在线用户数
     */
    private int onlineCount;

    private List<String> list;

    public RequestMessage(Integer type, Integer fromUser, Integer toUser, String msg, int onlineCount) {
        this.type = type;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.msg = msg;
        this.onlineCount = onlineCount;
    }


    public RequestMessage(Integer type, Integer fromUser, Integer toUser, String msg, int onlineCount, List<String> list) {
        this.type = type;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.msg = msg;
        this.onlineCount = onlineCount;
        this.list = list;
    }


    public static String jsonStr(Integer type, Integer fromUser, Integer toUser, String msg, int onlineCount) {
        return JSON.toJSONString(new RequestMessage(type, fromUser, toUser, msg, onlineCount));
    }

    public static String jsonStr(Integer type, Integer fromUser, Integer toUser, String msg, int onlineCount, List<String> list) {
        return JSON.toJSONString(new RequestMessage(type, fromUser, toUser, msg, onlineCount, list));
    }
}
