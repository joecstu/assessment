package com.kbtg.bootcamp.posttest.wallet;


import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.profile.Profile;
import com.kbtg.bootcamp.posttest.profile.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {


    private final WalletRepository walletRepository;
    private final ProfileRepository profileRepository;

    public WalletService(WalletRepository walletRepository, ProfileRepository profileRepository) {
        this.walletRepository = walletRepository;
        this.profileRepository = profileRepository;
    }

    public List<Wallet> getWalletList() {
        List<Wallet> wallets = walletRepository.findAll();
        return wallets;
    }


    @Transactional
    public Wallet createWallet(WalletRequestDto requestDto) throws Exception {
        Optional<Profile> optionalProfile = profileRepository.findByEmail(requestDto.getEmail());
        Profile profile;
        if (optionalProfile.isPresent()) {
            profile = optionalProfile.get();
        } else {
            profile = new Profile();
            profile.setEmail(requestDto.getEmail());
            profile.setName("someone");
            profileRepository.save(profile);
        }

        Wallet wallet = new Wallet();
        wallet.setWalletName(requestDto.getName());
        wallet.setActive(true);
        wallet.setProfile(profile);
        walletRepository.save(wallet);
        return wallet;
    }


    public Wallet getWalletById(Integer id) {
//        return walletList.stream()
//                .filter(wallet -> wallet.getId().equals(id))
//                .findFirst()
//                .orElseThrow(() -> new NotFoundException("Wallet " + id +" not found"));
        return null;
    }

    public Wallet editWalletById(Integer id, WalletRequestDto requestDto) {
        Optional<Wallet> optionalWallet = walletRepository.findById(Long.valueOf(id));
        if (optionalWallet.isEmpty()) {
            throw new BadRequestException("Invalid wallet id");
        }
        Wallet wallet = optionalWallet.get();
        wallet.setWalletName(requestDto.getName());
        walletRepository.save(wallet);
        return wallet;
    }

    //    @Transactional

    public void deleteWalletById(Integer id) {
//        walletRepository.setAllWalletsActive();

        walletRepository.deleteById(Long.valueOf(id));
    }
}
