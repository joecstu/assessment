package com.kbtg.bootcamp.posttest.admin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AdminRequestDto {

    public AdminRequestDto() {
    }

    @NotNull
    @Pattern(regexp = "\\d{6}", message = "Invalid Input")
    private String ticket;

    @NotNull
    @Pattern(regexp = "\\d.*", message = "Invalid Input")
    private String price;

    @NotNull
    @Pattern(regexp = "\\d.*", message = "Invalid Input")
    private String amount;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
