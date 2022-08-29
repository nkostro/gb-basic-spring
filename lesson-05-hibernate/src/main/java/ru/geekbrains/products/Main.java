package ru.geekbrains.products;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;
import ru.geekbrains.products.model.Product;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        ProductsRepository productsRepository = new ProductsRepository(entityManagerFactory);
        productsRepository.saveOrUpdate(new Product("Bananas", BigDecimal.valueOf(90)));
        productsRepository.saveOrUpdate(new Product("Apples", BigDecimal.valueOf(50)));
        productsRepository.saveOrUpdate(new Product("Grape", BigDecimal.valueOf(80)));
        productsRepository.saveOrUpdate(new Product("Melon", BigDecimal.valueOf(500)));
        productsRepository.saveOrUpdate(new Product("Peaches", BigDecimal.valueOf(85)));
        for (Product p : productsRepository.findAll()) {
            System.out.println(p);
        }

        productsRepository.deleteById(4);
        for (Product p : productsRepository.findAll()) {
            System.out.println(p);
        }
        System.out.println();

        Product oranges = new Product("Oranges", BigDecimal.valueOf(75));
        productsRepository.saveOrUpdate(oranges);
        for (Product p : productsRepository.findAll()) {
            System.out.println(p);
        }

        entityManagerFactory.close();
    }
}
