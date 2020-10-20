package cn.cxnxs.letuschat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 好友表
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Friends extends Model<Friends> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 好友id
     */
    private Integer friendId;

    /**
     * 自己的id
     */
    private Integer userId;

    /**
     * 备注名
     */
    private String name;

    /**
     * 好友类型
     */
    private Integer friendTypeId;

    /**
     * 所属分组id
     */
    private Integer friendGroupId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
