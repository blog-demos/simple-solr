package git.simple.model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * @Description TODO
 * @Author Q-WHai
 * @Date 2019/5/7 21:32
 */
public class StudentSolrResult {
    @Field(value = "id")
    private String id;

    @Field(value = "sno")
    private int sno;

    @Field(value = "name")
    private String name;

    @Field(value = "sex")
    private int sex;

    @Field(value = "age")
    private int age;

    @Field(value = "ctime")
    private Date ctime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
