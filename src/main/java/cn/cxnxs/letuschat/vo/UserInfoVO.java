package cn.cxnxs.letuschat.vo;

import cn.cxnxs.letuschat.entity.User;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * <p></p>
 *
 * @author mengjinyuan
 * @date 2020-10-13 17:25
 **/
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntrol() {
        return introl;
    }

    public void setIntrol(String introl) {
        this.introl = introl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getShengxiao() {
        return shengxiao;
    }

    public void setShengxiao(String shengxiao) {
        this.shengxiao = shengxiao;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getVocation() {
        return vocation;
    }

    public void setVocation(String vocation) {
        this.vocation = vocation;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
