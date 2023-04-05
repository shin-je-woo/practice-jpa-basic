package jpabasic.jpql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Seat {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private int stockAmount;
}
