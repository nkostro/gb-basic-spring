package ru.geekbrains.products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.products.exceptions.EntityNotFoundException;
import ru.geekbrains.products.persist.Product;
import ru.geekbrains.products.persist.ProductsRepository;

import javax.validation.Valid;

@Slf4j
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
        model.addAttribute("product", productsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s is not found in repository", id))));
        return "product_form";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        Product product = new Product();
        product.setId(productsRepository.getNextAvailableId());
        model.addAttribute("product", product);
        return "product_form";
    }

    @DeleteMapping("{id}")
    public String removeProduct(@PathVariable long id) {
        productsRepository.remove(id);
        log.info("removed product with id=" + id);
        return "redirect:/products";
    }

    @PostMapping
    public String saveProduct(@RequestBody @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product_form";
        }
        productsRepository.update(product);
        return "redirect:/products";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(Model model, EntityNotFoundException e) {
        model.addAttribute("message", e.getMessage());
        return "not_found";
    }
}
