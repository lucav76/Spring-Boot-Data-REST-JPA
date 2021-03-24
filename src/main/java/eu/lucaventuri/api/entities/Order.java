package eu.lucaventuri.api.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders", indexes = @Index(columnList = "user"))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long orderId;

    private String user;
    private Date creationDate = new Date();
    private String state = "OPEN";

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();
}
