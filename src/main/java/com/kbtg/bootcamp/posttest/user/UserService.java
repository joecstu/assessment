package com.kbtg.bootcamp.posttest.user;


import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.UserTicketRepository;
import com.kbtg.bootcamp.posttest.userticket.UserTicketTransaction;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserTicketRepository userTicketRepository;

    public UserService(UserTicketRepository userTicketRepository) {
        this.userTicketRepository = userTicketRepository;
    }

    @Transactional
    public UserTicket buyLottery(String userId, String ticketId) {
        UserTicket userTicket = new UserTicket();

        userTicket.setUserId(userId);
        userTicket.setTicketId(ticketId);
        userTicket.setStatusId("1");
        userTicket.setCreateUser(userId);
        userTicket.setCreateDate(new Date());
        userTicket.setUpdateUser(userId);
        userTicket.setUpdateDate(new Date());

        userTicketRepository.save(userTicket);

        userTicketRepository.flush();
        return userTicket;
    }

    public UserTicketTransactionResponseDto getTransactionsUser(String userId) {
        UserTicketTransactionResponseDto userTicketTransactionResponseDto = new UserTicketTransactionResponseDto();

        int count;
        int cost;
        Set<String> tickets = new HashSet<>();

        List<UserTicketTransaction> userTicketList = userTicketRepository.getUserTicketTransactionByUserIdAndStatusId(userId, "1");

        count = userTicketList.size();

        cost = userTicketList.stream()
                .map(UserTicketTransaction::getPrice)
                .reduce(0, Integer::sum);

        for (UserTicketTransaction userTicketTransaction : userTicketList) {
            tickets.add(userTicketTransaction.getTicketId());
        }

        userTicketTransactionResponseDto.setCount(count);
        userTicketTransactionResponseDto.setCost(cost);
        userTicketTransactionResponseDto.setTickets(new ArrayList<>(tickets));

        return userTicketTransactionResponseDto;
    }


    public void validateTransactionUser(String userId, String ticketId) {
        List<UserTicket> userTicketList = userTicketRepository.findByUserIdAndTicketIdAndStatusId(userId, ticketId, "1");

        if (userTicketList.isEmpty()) {
            throw new NotFoundException("TransactionUser is not found");
        }
    }

    public void voidTransactionUser(String userId, String ticketId) {
        int rowUpdated = userTicketRepository.updateStatusId(userId, ticketId, "1", "2");
        System.out.println("voidTransactionUser rowUpdated : " + String.valueOf(rowUpdated));

    }

}
