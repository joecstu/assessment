package com.kbtg.bootcamp.posttest.lottery;


import org.springframework.stereotype.Service;

import java.util.List;
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

}
