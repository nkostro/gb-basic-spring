package ru.geekbrains.products;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import ru.geekbrains.products.model.Product;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ProductsRepository productsRepository = new ProductsRepository(entityManager);
        productsRepository.insert(new Product("Bananas", 90));
        productsRepository.insert(new Product("Apples", 50));
        productsRepository.insert(new Product("Grape", 80));
        productsRepository.insert(new Product("Melon", 500));
        productsRepository.insert(new Product("Peaches", 85));
        for (Product p : productsRepository.findAll()) {
            System.out.println(p);
        }

        productsRepository.deleteById(4);
        for (Product p : productsRepository.findAll()) {
            System.out.println(p);
        }
        System.out.println();

        Product oranges = new Product("Oranges", 75);
        productsRepository.saveOrUpdate(oranges);
        for (Product p : productsRepository.findAll()) {
            System.out.println(p);
        }

        entityManagerFactory.close();
    }
}
