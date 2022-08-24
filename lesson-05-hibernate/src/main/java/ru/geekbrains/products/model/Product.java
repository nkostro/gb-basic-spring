package ru.geekbrains.products.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "products")
@NamedQueries({
        @NamedQuery(name = "Product.findAll", query = "select p from Product p"),
        @NamedQuery(name = "Product.findById", query = "select p from Product p where p.id = :id"),
        @NamedQuery(name = "Product.deleteById", query = "delete from Product p where p.id = :id"),
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private int price;

    public Product(String title, int price) {
        this.title = title;
        this.price = price;
    }
}
