package com.kbtg.bootcamp.posttest.profile;


import com.kbtg.bootcamp.posttest.wallet.Wallet;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    private String email;

    private String name;

    @OneToMany(mappedBy = "profile")
    private List<Wallet> wallets;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter, Setter, Constructors, equals, hashCode and toString
    // ...
}