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
 * 用户
 * </p>
 *
 * @author mengjinyuan
 * @since 2020-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 个性签名
     */
    private String signature;

    private Boolean sex;

    private LocalDateTime birthday;

    /**
     * 电话
     */
    private String phoneCode;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 简介
     */
    private String introl;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生肖
     */
    private String shengxiao;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 星座
     */
    private String constellation;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * 学校
     */
    private String school;

    /**
     * 职业
     */
    private String vocation;

    /**
     * 国家id
     */
    private Integer nationId;

    /**
     * 省id
     */
    private Integer provinceId;

    /**
     * 城市id
     */
    private Integer cityId;

    /**
     * 好友策略id
     */
    private Integer friendshipPolicyId;

    /**
     * 用户状态
     */
    private Integer state;

    /**
     * 好友策略问题
     */
    private String friendPolicyQuestion;

    /**
     * 好友策略答案
     */
    private String friendPolicyAnswer;

    /**
     * 好友策略密码
     */
    private String friendPolicyPassword;

    private String token;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
