package ru.geekbrains.products.persist;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductsRepository {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {
        insert("Apples", BigDecimal.valueOf(100));
        insert("Pears", BigDecimal.valueOf(150));
        insert("Grape", BigDecimal.valueOf(300.95));
        insert("Bananas", BigDecimal.valueOf(99.99));
        insert("Melon", BigDecimal.valueOf(450.89));
    }

    public void insert(String title, BigDecimal cost) {
        Product product = new Product(identity.incrementAndGet(), title, cost);
        products.put(product.getId(), product);
    }

    public long getNextAvailableId() {
        return identity.get() + 1;
    }

    public void remove(long id) {
        products.remove(id);
    }

    public Optional<Product> findById(long id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public void update(Product product) {
        products.put(product.getId(), product);
    }
}
