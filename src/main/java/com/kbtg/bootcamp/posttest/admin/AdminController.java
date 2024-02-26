package com.kbtg.bootcamp.posttest.admin;


import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.user.UserService;
import com.kbtg.bootcamp.posttest.user.UserTicketTransactionResponseDto;
import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@ControllerAdvice
@RequestMapping("/admin")
public class AdminController {

    private final LotteryService lotteryService;

    public AdminController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/lotteries")
    public ResponseEntity<Map<String, String>> createLottery(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        lotteryService.isLotteryByTicketIdNotExisting(adminRequestDto.getTicket());
        lotteryService.createLottery(adminRequestDto);

        Map<String, String> response = Collections.singletonMap("ticket", String.valueOf(adminRequestDto.getTicket()));
        return ResponseEntity.status(201).body(response);
    }

}


