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
 * 群消息-用户关联表
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GroupMsgUserRel extends Model<GroupMsgUserRel> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 群消息id
     */
    private Integer msgId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 接收状态：1已读，2未读，3撤回
     */
    private Integer state;

    /**
     * 发送时间
     */
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
