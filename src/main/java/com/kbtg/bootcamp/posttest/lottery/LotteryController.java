package com.kbtg.bootcamp.posttest.lottery;


import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    private final LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("")
    public ResponseEntity<Map<String, List<String>>> getLotteryTicketIds() {
        List<String> ticketIds = lotteryService.getLotteryList();
        Map<String, List<String>> response = Collections.singletonMap("tickets", ticketIds);
        return ResponseEntity.ok(response);
    }


}


