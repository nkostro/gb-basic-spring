package ru.geekbrains.products.persist;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductsRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    public ProductsRepository() {
        addProduct("Apples", BigDecimal.valueOf(100));
        addProduct("Pears", BigDecimal.valueOf(150));
        addProduct("Grape", BigDecimal.valueOf(300));
        addProduct("Bananas", BigDecimal.valueOf(99));
        addProduct("Melon", BigDecimal.valueOf(450));
    }

    public void addProduct(String title, BigDecimal cost) {
        Product product = new Product(identity.incrementAndGet(), title, cost);
        products.put(product.getId(), product);
    }

    public void deleteProduct(Product product) {
        products.remove(product.getId());
    }

    public Product findById(long id) {
        return products.get(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}
