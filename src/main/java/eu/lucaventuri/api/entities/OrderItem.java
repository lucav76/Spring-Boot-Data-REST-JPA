package eu.lucaventuri.api.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @EmbeddedId
    private OrderItemId id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @MapsId("productId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("orderId")
    private Order order;

    @Min(1)
    private int quantity = 1;
}
