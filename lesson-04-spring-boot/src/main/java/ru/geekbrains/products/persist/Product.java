package ru.geekbrains.products.persist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private long id;
    @NotBlank(message = "Can not be empty")
    private String title;
    @NotNull
    @DecimalMin(value = "0.0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal cost;
    @NotNull
    @Positive
    private int count;
    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = "[yyyy-MM-dd]")
    private LocalDate shipmentDate;

    public Product(long id, String title, BigDecimal cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }
}
