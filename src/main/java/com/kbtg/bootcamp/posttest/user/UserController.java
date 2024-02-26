package com.kbtg.bootcamp.posttest.user;


import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@ControllerAdvice
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final LotteryService lotteryService;

    public UserController(UserService userService, LotteryService lotteryService) {
        this.userService = userService;
        this.lotteryService = lotteryService;
    }

    @GetMapping("/{userId}/lotteries")
    public UserTicketTransactionResponseDto getTransactionsUser(
            @PathVariable("userId")
            @Pattern(regexp = "\\d{10}", message = "Invalid Input")
            String userId) {

        return userService.getTransactionsUser(userId);
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> buyLottery(
            @PathVariable("userId")
            @Pattern(regexp = "\\d{10}", message = "Invalid Input")
            String userId,
            @PathVariable("ticketId")
            @Pattern(regexp = "\\d{6}", message = "Invalid Input")
            String ticketId) {

        lotteryService.isLotteryByTicketIdExisting(ticketId);
        UserTicket userTicket = userService.buyLottery(userId, ticketId);

        Map<String, String> response = Collections.singletonMap("id", String.valueOf(userTicket.getTransactionId()));
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<Map<String, String>> voidLottery(
            @PathVariable("userId")
            @Pattern(regexp = "\\d{10}", message = "Invalid Input")
            String userId,
            @PathVariable("ticketId")
            @Pattern(regexp = "\\d{6}", message = "Invalid Input")
            String ticketId) {

        userService.validateTransactionUser(userId, ticketId);
        userService.voidTransactionUser(userId, ticketId);

        Map<String, String> response = Collections.singletonMap("ticket", String.valueOf(ticketId));
        return ResponseEntity.ok(response);

    }

}


