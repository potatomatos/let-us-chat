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
 * 群消息内容表
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GroupMsgContent extends Model<GroupMsgContent> {

    private static final long serialVersionUID=1L;

    /**
     * 群消息id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 群id
     */
    private Integer groupId;

    /**
     * 群消息内容
     */
    private String content;

    /**
     * 发送者id
     */
    private Integer fromUserId;

    /**
     * 发送时间
     */
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
