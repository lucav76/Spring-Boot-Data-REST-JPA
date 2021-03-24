package eu.lucaventuri.api.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long productId;

    private String name;
    private String description;
    private String brand;
    private double priceInUsd;
}
