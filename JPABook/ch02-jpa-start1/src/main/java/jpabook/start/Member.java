package jpabook.start;

import javax.persistence.*;  //**

/**
 * User: HolyEyE Date: 13. 5. 24. Time: 오후 7:43
 */
@Entity
@org.hibernate.annotations.DynamicUpdate
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "ID")
    private String  id;
    @Column(name = "NAME")
    private String  username;
    private Integer age;

    //
    public Member() {
    }

    public Member(String id, String username, Integer age) {
        this.id       = id;
        this.username = username;
        this.age      = age;
    }
    //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    //

    @Override
    public String toString() {
        return "Member{" + "id='" + id + '\'' + ", username='" + username + '\'' + ", age=" + age + '}';
    }
}
