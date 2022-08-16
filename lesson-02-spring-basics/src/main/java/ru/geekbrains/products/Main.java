package ru.geekbrains.products;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProductsConfiguration.class)) {
            CartService cartService = context.getBean("cartService", CartService.class);
            cartService.run();
        }
    }
}
