package com.kbtg.bootcamp.posttest.user;


import java.util.List;

public class UserTicketTransactionResponseDto {

    private int count;
    private int cost;
    private List<String> tickets;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }
}
