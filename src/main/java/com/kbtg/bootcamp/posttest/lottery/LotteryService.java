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

    public void isLotteryByTicketIdExisting(String ticketId) {
        Optional<Lottery> lottery = lotteryRepository.findByTicketId(ticketId);

        //If there is no ticket in Database Should return error

        if (lottery.isEmpty()) {
            throw new NotFoundException("Invalid ticketId");
        }
    }

    public void isLotteryByTicketIdNotExisting(String ticketId) {
        Optional<Lottery> lottery = lotteryRepository.findByTicketId(ticketId);

        //If there is a ticket in Database Should return error

        if (lottery.isPresent()) {
            throw new BadRequestException("Invalid ticketId");
        }
    }

    @Transactional
    public void createLottery(AdminRequestDto adminRequestDto) {

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

    }

}
