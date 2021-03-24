package eu.lucaventuri.api.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long newsId;

    private String title;
    private LocalDate date = LocalDate.now();
}
