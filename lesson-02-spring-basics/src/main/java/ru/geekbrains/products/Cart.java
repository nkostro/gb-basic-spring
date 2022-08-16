package ru.geekbrains.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.geekbrains.products.persist.Product;
import ru.geekbrains.products.persist.ProductsRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class Cart {
    private final ProductsRepository productsRepository;
    private final List<Product> products;

    @Autowired
    public Cart(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
        this.products = new ArrayList<>();
    }

    public void addProductById(long id) {
        Product product = productsRepository.findById(id);
        if (product == null) {
            throw new IllegalArgumentException("Product with id " + id + " was not found.");
        }
        this.products.add(product);
    }

    public void removeProductById(long id) {
        Iterator<Product> iter = products.iterator();
        while (iter.hasNext()) {
            Product product = iter.next();
            if (product.getId() == id) {
                iter.remove();
                break;
            }
        }
    }

    public List<Product> getProducts() {
        return this.products;
    }
}
