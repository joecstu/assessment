package com.kbtg.bootcamp.posttest.user;


import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.UserTicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;

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

}
