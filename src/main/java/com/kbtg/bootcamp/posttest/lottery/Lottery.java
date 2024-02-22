package com.kbtg.bootcamp.posttest.lottery;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "lottery", schema = "public")
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lt_ticket_id", nullable = false, length = 100)
    private String ticketId;

    @Column(name = "lt_price", nullable = false)
    private long price;

    @Column(name = "lt_amount", nullable = false)
    private long amount;

    @Column(name = "lt_create_user", length = 100)
    private String createUser;

    @Column(name = "lt_create_date")
    private Date createDate;

    @Column(name = "lt_update_user", length = 100)
    private String updateUser;

    @Column(name = "lt_update_date")
    private Date updateDate;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
