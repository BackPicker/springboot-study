package hello.ch05.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.ManyToAny;

@Entity
@Data
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    private String userName;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team team;
    //


    public Member(String userName) {
        this.userName = userName;
    }
}
