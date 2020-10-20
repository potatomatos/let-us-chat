package cn.cxnxs.letuschat.websocket;

import cn.cxnxs.letuschat.entity.*;
import cn.cxnxs.letuschat.mapper.*;
import cn.cxnxs.letuschat.vo.RequestMessage;
import cn.cxnxs.letuschat.vo.ResponseMessage;
import cn.cxnxs.letuschat.vo.UserInfoVO;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author potatomato
 */
@Slf4j
@Component
@ServerEndpoint("/chat/{id}/{token}")
public class WebSocketChatServer {
    /**
     * 全部在线会话  PS: 基于场景考虑 这里使用线程安全的Map存储会话对象。
     * 以用户姓名为key
     */
    private static final Map<Integer, Session> ONLINE_SESSIONS = new ConcurrentHashMap<>();

    private static final Map<Integer, UserInfoVO> ONLINE_USER_INFO = new ConcurrentHashMap<>();


    private static RedisTemplate<String, User> redisTemplate;

    private static UserMapper userMapper;

    private static MessagesMapper messagesMapper;

    private static PrivateMsgContentMapper privateMsgContentMapper;

    private static PrivateMsgUserRelMapper privateMsgUserRelMapper;

    private static GroupMsgContentMapper groupMsgContentMapper;

    private static GroupMsgUserRelMapper groupMsgUserRelMapper;

    private static GroupUserRelMapper groupUserRelMapper;


    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();


    /**
     * 当通信发生异常：打印错误日志
     */
    @OnError
    public void onError(Session session, Throwable error) {
        currentUser.remove();
        error.printStackTrace();
    }

    /**
     * 当客户端打开连接：1.添加会话对象 2.更新在线人数
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") Integer id, @PathParam("token") String token) throws IOException {
        if (id == null || token == null) {
            log.info("连接参数为空");
            session.close();
            return;
        }
        User userEntity = redisTemplate.opsForValue().get(token);
        if (userEntity == null) {
            log.error("token有误");
            session.close();
            return;
        } else {
            if (!userEntity.getId().equals(id)) {
                log.error("id与token不匹配");
                session.close();
                return;
            }
        }
        log.info("------连接成功,用户信息：{}------", userEntity.toString());
        ONLINE_SESSIONS.put(id, session);
        UserInfoVO userInfoVO = new UserInfoVO();
        User currentUser = userMapper.selectById(id);
        this.currentUser.set(currentUser);
        ONLINE_USER_INFO.put(id, userInfoVO.po2vo(currentUser));
        Messages messagesEntity = new Messages();
        messagesEntity.setMessage("连接成功,用户信息：" + userEntity.toString());
        messagesEntity.setStatus(1);
        messagesEntity.setMessageType(RequestMessage.SYS_PRIVATE);
        messagesEntity.setFromUser(id);
        messagesEntity.setToUser(null);
        messagesEntity.setTime(LocalDateTime.now());
        messagesMapper.insert(messagesEntity);
    }

    /**
     * 当客户端发送消息：1.获取它的用户名和消息 2.发送消息给所有人
     * <p>
     * PS: 这里约定传递的消息为JSON字符串 方便传递更多参数！
     */
    @OnMessage
    public void onMessage(String jsonStr) {
        RequestMessage requestMessage = JSON.parseObject(jsonStr, RequestMessage.class);
        log.info("收到消息：{}", requestMessage);
        if (RequestMessage.PRIVATE.equals(requestMessage.getType())) {
            sendMessageToUser(RequestMessage.jsonStr(RequestMessage.PRIVATE, requestMessage.getFromUser(), requestMessage.getToUser(), requestMessage.getMsg(), ONLINE_SESSIONS.size()));
        } else if (RequestMessage.PUBLIC.equals(requestMessage.getType())) {
            sendMessageToAll(requestMessage);
        }

        Messages messagesEntity = new Messages();

        messagesEntity.setMessage(requestMessage.getMsg());
        messagesEntity.setStatus(1);
        messagesEntity.setMessageType(RequestMessage.SYS_PRIVATE);
        messagesEntity.setFromUser(requestMessage.getFromUser());
        messagesEntity.setToUser(requestMessage.getToUser());
        messagesEntity.setTime(LocalDateTime.now());
        messagesMapper.insert(messagesEntity);
    }

    /**
     * 当关闭连接：1.移除会话对象 2.更新在线人数
     */
    @OnClose
    public void onClose(Session session, @PathParam("id") Integer id) {
        ONLINE_SESSIONS.remove(id);
        ONLINE_USER_INFO.remove(id);
        currentUser.remove();
    }

    /**
     * 公共方法：发送信息给所有人
     */
    private static void sendMessageToAll(RequestMessage requestMessage) {
        //获取群成员
        List<GroupUserRel> groupUserRels = groupUserRelMapper.selectList(new QueryWrapper<GroupUserRel>().eq("group_id", requestMessage.getToUser()));

        //保存群聊消息
        GroupMsgContent groupMsgContent = new GroupMsgContent();
        groupMsgContent.setGroupId(requestMessage.getToUser());
        groupMsgContent.setContent(requestMessage.getMsg());
        groupMsgContent.setFromUserId(requestMessage.getFromUser());
        groupMsgContent.setCreateTime(LocalDateTime.now());
        groupMsgContentMapper.insert(groupMsgContent);

        groupUserRels.forEach(groupUserRel -> {
            GroupMsgUserRel groupMsgUserRel = new GroupMsgUserRel();
            groupMsgUserRel.setMsgId(groupMsgContent.getId());
            groupMsgUserRel.setUserId(groupUserRel.getUserId());
            groupMsgUserRel.setCreateTime(LocalDateTime.now());
            if (ONLINE_SESSIONS.get(groupUserRel.getUserId()) != null) {
                try {
                    groupMsgUserRel.setState(2);
                    ONLINE_SESSIONS.get(groupUserRel.getUserId()).getBasicRemote().sendText(
                            ResponseMessage.jsonStr(2,
                                    ONLINE_USER_INFO.get(requestMessage.getFromUser()),
                                    ONLINE_USER_INFO.get(groupUserRel.getUserId()),
                                    requestMessage.getMsg(),
                                    ONLINE_SESSIONS.size()));
                } catch (IOException e) {
                    groupMsgUserRel.setState(3);
                }
            }else {
                groupMsgUserRel.setState(1);
            }
            groupMsgUserRelMapper.insert(groupMsgUserRel);
        });
    }

    /**
     * 单独聊天方法：发送信息给指定的人
     */
    private static void sendMessageToUser(String msg) {
        RequestMessage requestMessage = JSON.parseObject(msg, RequestMessage.class);
        PrivateMsgContent privateMsgContent = new PrivateMsgContent();
        privateMsgContent.setContent(requestMessage.getMsg());
        privateMsgContent.setFromUser(requestMessage.getFromUser());
        privateMsgContent.setToUser(requestMessage.getToUser());
        privateMsgContent.setCreateTime(LocalDateTime.now());
        privateMsgContentMapper.insert(privateMsgContent);

        PrivateMsgUserRel fromUserMsgRel = new PrivateMsgUserRel();
        fromUserMsgRel.setUserId(requestMessage.getFromUser());
        fromUserMsgRel.setMsgId(privateMsgContent.getId());
        fromUserMsgRel.setCreateTime(LocalDateTime.now());
        fromUserMsgRel.setState(1);
        privateMsgUserRelMapper.insert(fromUserMsgRel);

        PrivateMsgUserRel toUserMsgRel = new PrivateMsgUserRel();
        toUserMsgRel.setUserId(requestMessage.getToUser());
        toUserMsgRel.setMsgId(privateMsgContent.getId());
        toUserMsgRel.setCreateTime(LocalDateTime.now());

        if (ONLINE_SESSIONS.get(requestMessage.getToUser()) != null) {
            try {
                toUserMsgRel.setState(2);
                ONLINE_SESSIONS.get(requestMessage.getToUser()).getBasicRemote().sendText(msg);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                toUserMsgRel.setState(3);
            } finally {
                privateMsgUserRelMapper.insert(toUserMsgRel);
            }
        } else {
            //存入未读消息表
            toUserMsgRel.setState(1);
            privateMsgUserRelMapper.insert(toUserMsgRel);
        }
    }

    @Resource
    public void setRedisTemplate(RedisTemplate<String, User> redisTemplate) {
        WebSocketChatServer.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketChatServer.userMapper = userMapper;
    }

    @Autowired
    public void setMessagesMapper(MessagesMapper messagesMapper) {
        WebSocketChatServer.messagesMapper = messagesMapper;
    }

    @Autowired
    public void setPrivateMsgContentMapper(PrivateMsgContentMapper privateMsgContentMapper) {
        WebSocketChatServer.privateMsgContentMapper = privateMsgContentMapper;
    }

    @Autowired
    public void setPrivateMsgUserRelMapper(PrivateMsgUserRelMapper privateMsgUserRelMapper) {
        WebSocketChatServer.privateMsgUserRelMapper = privateMsgUserRelMapper;
    }

    @Autowired
    public void setGroupMsgContentMapper(GroupMsgContentMapper groupMsgContentMapper) {
        WebSocketChatServer.groupMsgContentMapper = groupMsgContentMapper;
    }

    @Autowired
    public void setGroupMsgUserRelMapper(GroupMsgUserRelMapper groupMsgUserRelMapper) {
        WebSocketChatServer.groupMsgUserRelMapper = groupMsgUserRelMapper;
    }

    @Autowired
    public void setGroupUserRelMapper(GroupUserRelMapper groupUserRelMapper) {
        WebSocketChatServer.groupUserRelMapper = groupUserRelMapper;
    }
}
