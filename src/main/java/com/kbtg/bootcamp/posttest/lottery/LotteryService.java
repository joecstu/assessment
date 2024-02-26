package com.kbtg.bootcamp.posttest.lottery;


import com.kbtg.bootcamp.posttest.admin.AdminRequestDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotteryService {


    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public List<String> getLotteryList() {
        return lotteryRepository.findAll().stream()
                .map(Lottery::getTicketId)
                .collect(Collectors.toList());
    }

    public void findLotteryByTicketId(String ticketId) {
        Optional<Lottery> lottery = lotteryRepository.findByTicketId(ticketId);
        if (lottery.isEmpty()) {
            throw new NotFoundException("Invalid ticketId");
        }
    }

    public String validateLotteryByTicketId(String ticketId) {
        Optional<Lottery> lottery = lotteryRepository.findByTicketId(ticketId);

        System.out.println(lottery.isPresent());
        System.out.println(lottery.isEmpty());

        if (lottery.isPresent()) {
            throw new BadRequestException("Invalid ticketId");
        }
        return ticketId;
    }

    @Transactional
    public String createLottery(AdminRequestDto adminRequestDto) {

        Lottery lottery = new Lottery();

        lottery.setTicketId(adminRequestDto.getTicket());
        lottery.setPrice(Long.parseLong(adminRequestDto.getPrice()));
        lottery.setAmount(Long.parseLong(adminRequestDto.getAmount()));
        lottery.setCreateUser("admin");
        lottery.setCreateDate(new Date());
        lottery.setUpdateUser("admin");
        lottery.setUpdateDate(new Date());

        lotteryRepository.save(lottery);

        lotteryRepository.flush();

        return adminRequestDto.getTicket();
    }

}
