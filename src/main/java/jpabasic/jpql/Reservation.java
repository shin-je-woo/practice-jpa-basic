package jpabasic.jpql;

import javax.persistence.*;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private int reserveAmount;

    @Embedded
    private Residence residence;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
}
