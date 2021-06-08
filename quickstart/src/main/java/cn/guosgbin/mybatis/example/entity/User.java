package cn.guosgbin.mybatis.example.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private static final long serialVersionUID = 5954602998605246077L;

    private int id;
    private String name;
    private int age;
    private int sex;
    private int address;

    private LocalDate birthday;
    private LocalDate createTime;
    private LocalDate updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
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
