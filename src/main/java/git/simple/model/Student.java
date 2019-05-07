package git.simple.model;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @Description TODO
 * @Author: Q-WHai
 * @Date: Created in 11:10 2019/05/07
 */
public class Student {
    @Field(value = "id")
    private int id;

    @Field(value = "sno")
    private int sno;

    @Field(value = "name")
    private String name;

    @Field(value = "sex")
    private int sex;

    @Field(value = "age")
    private int age;

    @Field(value = "ctime")
    private String ctime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
}
