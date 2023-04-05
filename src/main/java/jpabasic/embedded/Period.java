package jpabasic.embedded;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class Period {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
