package ru.geekbrains.products.persist;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Product {
    private long id;
    private String title;
    private BigDecimal cost;

    public Product() {

    }

    public Product(long id, String title, BigDecimal cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product: id = " +id + ", title = " + title + ", cost = " + cost;
    }
}
