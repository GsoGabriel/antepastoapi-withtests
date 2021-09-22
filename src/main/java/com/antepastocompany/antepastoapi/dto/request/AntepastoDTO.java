package com.antepastocompany.antepastoapi.dto.request;

import com.antepastocompany.antepastoapi.enums.Spicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AntepastoDTO {

    private Long id;

    @NotEmpty
    private String flavor;

    private String spicy;

    @NotEmpty
    private int quantity;

    @NotEmpty
    private double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getSpicy() {
        return spicy;
    }

    public void setSpicy(String spicy) {
        this.spicy = spicy;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
