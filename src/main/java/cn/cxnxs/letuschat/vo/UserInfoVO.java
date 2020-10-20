package cn.cxnxs.letuschat.vo;

import cn.cxnxs.letuschat.entity.User;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * <p></p>
 *
 * @author mengjinyuan
 * @date 2020-10-13 17:25
 **/
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserInfoVO {
    private Integer id;
    private String username;
    private String signature;
    private Short sex;
    private Date birthday;
    private String phoneCode;
    private String name;
    private String email;
    private String introl;
    private String avatar;
    private String shengxiao;
    private Integer age;
    private String constellation;
    private String bloodType;
    private String school;
    private String vocation;
    private Integer nationId;
    private Integer provinceId;
    private Integer cityId;

    public User vo2po(){
        User userEntity=new User();
        BeanUtils.copyProperties(this,userEntity);
        return userEntity;
    }

    public UserInfoVO po2vo(User userEntity){
        if (userEntity!=null){
            BeanUtils.copyProperties(userEntity,this);
        }
        return this;
    }
}
