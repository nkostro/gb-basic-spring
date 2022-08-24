package ru.geekbrains.products;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import ru.geekbrains.products.model.Product;

import java.util.List;

@RequiredArgsConstructor
public class ProductsRepository {
    private final EntityManager entityManager;

    public Product findById(long id) {
        entityManager.getTransaction().begin();
        Product product = entityManager.find(Product.class, id);
        entityManager.getTransaction().commit();
        return product;
    }

    public List<Product> findAll() {
        entityManager.getTransaction().begin();
        List<Product> products = entityManager.createNamedQuery("Product.findAll", Product.class)
                .getResultList();
        entityManager.getTransaction().commit();
        return products;
    }

    public void deleteById(long id) {
        Product product = findById(id);
        if (product != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(product);
            entityManager.getTransaction().commit();
        } else {
            throw new IllegalArgumentException("Product with id " + id + " was not found.");
        }
    }

    public Product saveOrUpdate(Product product) {
        entityManager.getTransaction().begin();
        Product entity = entityManager.merge(product);
        entityManager.getTransaction().commit();
        return entity;
    }

    public void insert(Product product) {
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
    }
}
