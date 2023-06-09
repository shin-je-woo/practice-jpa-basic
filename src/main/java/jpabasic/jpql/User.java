package jpabasic.jpql;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
@NamedQuery(
        name = "User.findByUserName",
        query = "select u from User u where u.username = :username"
)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private int age;

    @Enumerated(value = EnumType.STRING)
    private MyUserType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "belong_id")
    private Belong belong;

    public void changeBelong(Belong belong) {
        this.belong = belong;
        belong.getUserList().add(this);
    }
}
