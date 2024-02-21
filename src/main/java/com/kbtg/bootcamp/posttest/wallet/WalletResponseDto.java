package com.kbtg.bootcamp.posttest.wallet;


import com.kbtg.bootcamp.posttest.profile.Profile;

public class WalletResponseDto {

    private String walletName;
    private Boolean active;

    private Profile profile;

    public WalletResponseDto(String walletName, Boolean active, Profile profile) {
        this.walletName = walletName;
        this.active = active;
        this.profile = profile;
    }

    public String getWalletName() {
        return walletName;
    }

    public Boolean getActive() {
        return active;
    }

    public Profile getProfile() {
        return profile;
    }
}
