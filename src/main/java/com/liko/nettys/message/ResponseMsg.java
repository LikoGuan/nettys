package com.liko.nettys.message;

/**
 * Created by likoguan on 7/12/18.
 */
public class ResponseMsg {
    private Long no;

    private String name;

    private String gender;

    private int age;

    private boolean married;

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    @Override
    public String toString() {
        return "ResponseMsg[no=" + this.no
                + ", name=" + this.name
                + ", gender=" + this.gender
                + ", age=" + this.age
                + ", married=" + this.married
                + "]";
    }
}
