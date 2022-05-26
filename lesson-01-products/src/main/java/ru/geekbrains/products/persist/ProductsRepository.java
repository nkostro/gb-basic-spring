package ru.geekbrains.products.persist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductsRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    public void addProduct(String title, BigDecimal cost) {
        Product product = new Product(identity.incrementAndGet(), title, cost);
        products.put(product.getId(), product);
    }

    public void deleteProduct(Product product) {
        products.remove(product.getId());
    }

    public Product getProductById(long id) {
        return products.get(id);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
