package ru.geekbrains.products;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import ru.geekbrains.products.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class ProductsRepository {
    private final EntityManagerFactory entityManagerFactory;

    public Optional<Product> findById(long id) {
        return execute(entityManager -> Optional.ofNullable(entityManager.find(Product.class, id)));
    }

    public List<Product> findAll() {
        return execute(entityManager -> entityManager.createNamedQuery("Product.findAll", Product.class)
                .getResultList());
    }

    public void deleteById(long id) {
        executeInTransaction(entityManager -> {
            entityManager.createNamedQuery("Product.deleteById")
                    .setParameter("id", id)
                    .executeUpdate();
        });
    }

    public void saveOrUpdate(Product product) {
        executeInTransaction(entityManager -> {
            if (product.getId() == null) {
                entityManager.persist(product);
            } else {
                entityManager.merge(product);
            }
        });
    }

    private <R> R execute(Function<EntityManager, R> function) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            em.close();
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
