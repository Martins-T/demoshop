package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Produkta ievades dati bez ID")
public class ProductRequest {

    @NotBlank(message = "Nosaukums ir obligāts")
    @Schema(description = "Produkta nosaukums", example = "Nosaukums")
    private String name;

    @Schema(description = "Produkta apraksts", example = "Apraksts")
    private String description;

    @NotNull(message = "Cena ir obligāta")
    @PositiveOrZero(message = "Cena nevar būt negatīva")
    @Schema(description = "Produkta cena", example = "99.99")
    private Double price;

    @NotNull(message = "Atlikums ir obligāts")
    @Min(value = 0, message = "Atlikums nevar būt mazāks par 0")
    @Schema(description = "Produkta atlikums", example = "50")
    private Integer stock;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
