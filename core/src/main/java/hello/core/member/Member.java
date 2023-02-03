package hello.core.member;

public class Member {

    //필드
    private Long   id;
    private String name;
    private Grade  grade;

    //생성자
    public Member(Long id, String name, Grade grade) {
        this.id    = id;
        this.name  = name;
        this.grade = grade;
    }

    //메소드


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    //
    @Override
    public String toString() {
        return "Member{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", grade=" + grade +
               '}';
    }
}
