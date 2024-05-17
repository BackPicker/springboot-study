package hello.datajpa.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.temporal.TemporalAccessor;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;
    private String  userName;
    private Integer age;

    //
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_id")
    private Team team;

    //
    public Member(String userName) {
        this.userName = userName;
    }

    public Member(String userName, Integer age) {
        this.userName = userName;
        this.age      = age;
    }

    public Member(String userName, Integer age, Team team) {
        this.userName = userName;
        this.age      = age;
        if (team != null) {
            teamCheck(team);
        }
    }

    private void teamCheck(Team team) {
        this.team = team;
        team.getMembers()
                .add(this);
    }
}
