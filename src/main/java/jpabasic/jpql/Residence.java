package jpabasic.jpql;

import javax.persistence.Embeddable;

@Embeddable
public class Residence {

    private String city;
    private String street;
    private String zipcode;
}
