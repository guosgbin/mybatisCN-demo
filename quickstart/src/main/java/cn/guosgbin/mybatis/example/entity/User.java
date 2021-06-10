package cn.guosgbin.mybatis.example.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private static final long serialVersionUID = 5954602998605246077L;

    private Integer id;
    private String name;
    private Integer age;
    private Integer sex;
    private Integer address;

    private LocalDate birthday;
    private LocalDate createTime;
    private LocalDate updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", address=" + address +
                ", birthday=" + birthday +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
