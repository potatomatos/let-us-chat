package cn.cxnxs.letuschat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Messages extends Model<Messages> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 消息状态 -1失败 1.成功 2撤回
     */
    private Integer status;

    /**
     * 消息类型 1.私聊 2.群聊 3.私聊系统消息 4.群聊系统消息
     */
    private Integer messageType;

    /**
     * 发送者id
     */
    private Integer fromUser;

    /**
     * 接收者id
     */
    private Integer toUser;

    /**
     * 发送时间
     */
    private LocalDateTime time;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
