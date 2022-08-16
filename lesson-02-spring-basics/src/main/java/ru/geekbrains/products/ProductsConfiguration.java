package ru.geekbrains.products;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.geekbrains.products.persist.ProductsRepository;

@Configuration
public class ProductsConfiguration {
    @Bean
    public ProductsRepository productsRepository() {
        return new ProductsRepository();
    }

    @Bean
    @Scope(value = "prototype")
    public Cart cart(ProductsRepository productsRepository) {
        return new Cart(productsRepository());
    }

    @Bean
    public CartService cartService() {
        return new CartService();
    }
}
