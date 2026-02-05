package com.sbravoc.productshexagonal.application.port.in;

import java.math.BigDecimal;

public class CreateProductCommand {
    private final String name;
    private final BigDecimal price;

    public CreateProductCommand(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
