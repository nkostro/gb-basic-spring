package ru.geekbrains.products.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.products.persist.Product;
import ru.geekbrains.products.persist.ProductsRepository;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsRepository productsRepository;

    @GetMapping
    public String listAllProducts(Model model) {
        model.addAttribute("products", productsRepository.findAll());
        return "products";
    }

    @GetMapping("/{id}")
    public String listProduct(@PathVariable long id, Model model) {
        model.addAttribute("product", productsRepository.findById(id));
        return "product_form";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        Product product = new Product();
        product.setId(productsRepository.getNextAvailableId());
        model.addAttribute("product", product);
        return "product_form";
    }

    @GetMapping("/remove/{id}")
    public String removeProduct(@PathVariable long id, Model model) {
        productsRepository.remove(id);
        return "redirect:/products";
    }

    @PostMapping
    public String saveProduct(Product product) {
        productsRepository.update(product);
        return "redirect:/products";
    }
}
