package com.kbtg.bootcamp.posttest.userticket;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user_ticket", schema = "public")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ut_tran_id", nullable = false)
    private long transactionId;

    @Column(name = "ut_user_id", nullable = false, length = 10)
    private String userId;

    @Column(name = "ut_ticket_id", nullable = false, length = 100)
    private String ticketId;

    @Column(name = "ut_status_id", nullable = false, length = 1)
    private String statusId;

    @Column(name = "ut_create_user", length = 100)
    private String createUser;

    @Column(name = "ut_create_date")
    private Date createDate;

    @Column(name = "ut_update_user", length = 100)
    private String updateUser;

    @Column(name = "ut_update_date")
    private Date updateDate;

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
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