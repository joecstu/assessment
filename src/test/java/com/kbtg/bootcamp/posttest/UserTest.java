package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import com.kbtg.bootcamp.posttest.user.UserController;
import com.kbtg.bootcamp.posttest.user.UserService;
import com.kbtg.bootcamp.posttest.userticket.UserTicket;
import com.kbtg.bootcamp.posttest.userticket.UserTicketRepository;
import com.kbtg.bootcamp.posttest.userticket.UserTicketTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private LotteryService lotteryService;
    @Mock
    private UserController userController;
    @Mock
    private UserTicketRepository userTicketRepository;
    @Mock
    private LotteryRepository lotteryRepository;


    @BeforeEach
    void setUp() {
        UserService userService = new UserService(userTicketRepository);
        LotteryService lotteryService = new LotteryService(lotteryRepository);
        UserController userController = new UserController(userService, lotteryService);

        mockMvc = MockMvcBuilders.standaloneSetup(userService, userController, lotteryService).alwaysDo(print()).build();
    }

    @Test
    @DisplayName("allUser canGetTransactionsUser")
    public void canGetTransactionsUser() throws Exception {
        String userId = "0100000001";

        UserTicketTransaction transaction1 = mock(UserTicketTransaction.class);
        when(transaction1.getPrice()).thenReturn(80);
        when(transaction1.getTicketId()).thenReturn("000001");

        UserTicketTransaction transaction2 = mock(UserTicketTransaction.class);
        when(transaction2.getPrice()).thenReturn(80);
        when(transaction2.getTicketId()).thenReturn("000001");

        UserTicketTransaction transaction3 = mock(UserTicketTransaction.class);
        when(transaction3.getPrice()).thenReturn(80);
        when(transaction3.getTicketId()).thenReturn("000002");


        List<UserTicketTransaction> userTicketTransactionList = new ArrayList<>();

        userTicketTransactionList.add(transaction1);
        userTicketTransactionList.add(transaction2);
        userTicketTransactionList.add(transaction3);

        String responseExpect = "{\"count\":3,\"cost\":240,\"tickets\":[\"000002\",\"000001\"]}";


        //Mock there are three data in database
        when(userTicketRepository.getUserTicketTransactionByUserIdAndStatusId(anyString(), anyString())).thenReturn(userTicketTransactionList);

        mockMvc.perform(get("/users/" + userId + "/lotteries").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(responseExpect));
    }

    @Test
    @DisplayName("allUser canGetTransactionsUserButThereIsNoDataInDatabase")
    public void canGetTransactionsUserButThereIsNoDataInDatabase() throws Exception {
        String userId = "0100000001";

        List<UserTicketTransaction> userTicketTransactionList = new ArrayList<>();

        String responseExpect = "{\"count\":0,\"cost\":0,\"tickets\":[]}";

        //Mock there is no data in database
        when(userTicketRepository.getUserTicketTransactionByUserIdAndStatusId(anyString(), anyString())).thenReturn(userTicketTransactionList);

        mockMvc.perform(get("/users/" + userId + "/lotteries").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(responseExpect));
    }

    @Test
    @DisplayName("allUser canNotGetTransactionsUserBecauseUserIdIsInValid")
    public void canNotGetTransactionsUserBecauseUserIdIsInValid() throws Exception {
        // set userId length 9 but should be 10
        String userId = "010000000";

        mockMvc.perform(get("/users/" + userId + "/lotteries").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }

    @Test
    @DisplayName("allUser canBuyLotteryIfThereIsALotteryInDatabase")
    public void canBuyLotteryIfThereIsALotteryInDatabase() throws Exception {
        String userId = "0100000001";
        String ticketId = "123456";

        Lottery lottery = new Lottery();
        lottery.setTicketId(ticketId);
        lottery.setPrice(80);
        lottery.setAmount(1);
        lottery.setCreateUser("test");
        lottery.setCreateDate(new Date());
        lottery.setUpdateUser("test");
        lottery.setUpdateDate(new Date());

        //Mock there is a data in database
        when(lotteryRepository.findByTicketId(ticketId)).thenReturn(Optional.of(lottery));

        String responseExpect = "{\"id\":\"0\"}";

        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(content().string(responseExpect));
    }

    @Test
    @DisplayName("allUser canNotBuyLotteryIfThereIsNoDataInDatabase")
    public void canNotBuyLotteryIfThereIsNoDataInDatabase() throws Exception {
        String userId = "0100000001";
        String ticketId = "123456";

        //Mock there is no data in database
        when(lotteryRepository.findByTicketId(ticketId)).thenReturn(Optional.empty());

        String errorExpect = "Request processing failed: com.kbtg.bootcamp.posttest.exception.NotFoundException: Invalid ticketId";

        try {
            mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId).contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            assertEquals(errorExpect, e.getMessage());
        }
    }

    @Test
    @DisplayName("allUser canNotBuyLotteryBecauseUserIdIsInValid")
    public void canNotBuyLotteryBecauseUserIdIsInValid() throws Exception {
        // set userId length 9 but should be 10
        String userId = "010000000";
        String ticketId = "123456";

        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }

    @Test
    @DisplayName("allUser canNotBuyLotteryBecauseTicketIdIsInValid")
    public void canNotBuyLotteryBecauseTicketIdIsInValid() throws Exception {
        // set ticketId length 5 but should be 6
        String userId = "0100000001";
        String ticketId = "12345";

        mockMvc.perform(post("/users/" + userId + "/lotteries/" + ticketId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }

    @Test
    @DisplayName("allUser canVoidTransactionLotteryIfThereIsTransactionInDatabase")
    public void canVoidTransactionLotteryIfThereIsTransactionInDatabase() throws Exception {
        String userId = "0100000001";
        String ticketId = "123456";
        String statusActive = "1";
        String statusInActive = "2";

        List<UserTicket> userTicketList = new ArrayList<>();

        UserTicket userTicket = new UserTicket();
        userTicket.setTransactionId(1);
        userTicket.setUserId(userId);
        userTicket.setTicketId(ticketId);
        userTicket.setStatusId(statusActive);

        userTicketList.add(userTicket);

        //Mock there is a data in database
        when(userTicketRepository.findByUserIdAndTicketIdAndStatusId(userId, ticketId, statusActive)).thenReturn(userTicketList);

        //Mock response when update database success
        when(userTicketRepository.updateStatusId(userId, ticketId, statusActive, statusInActive)).thenReturn(1);

        String responseExpect = "{\"ticket\":\"" + ticketId + "\"}";


        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(responseExpect));
    }


    @Test
    @DisplayName("allUser canNotVoidTransactionLotteryIfThereIsNoTransactionInDatabase")
    public void canNotVoidTransactionLotteryIfThereIsNoTransactionInDatabase() throws Exception {
        String userId = "0100000001";
        String ticketId = "123456";
        String statusActive = "1";

        List<UserTicket> userTicketList = new ArrayList<>();

        //Mock there is no data in database
        when(userTicketRepository.findByUserIdAndTicketIdAndStatusId(userId, ticketId, statusActive)).thenReturn(userTicketList);

        String errorExpect = "Request processing failed: com.kbtg.bootcamp.posttest.exception.NotFoundException: TransactionUser is not found";
        try {
            mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId).contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            assertEquals(errorExpect, e.getMessage());
        }

    }

    @Test
    @DisplayName("allUser canNotVoidTransactionLotteryBecauseUserIdIsInValid")
    public void canNotVoidTransactionLotteryBecauseUserIdIsInValid() throws Exception {
        // set userId length 9 but should be 10
        String userId = "010000000";
        String ticketId = "123456";

        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }

    @Test
    @DisplayName("allUser canNotVoidTransactionLotteryBecauseTicketIdIsInValid")
    public void canNotVoidTransactionLotteryBecauseTicketIdIsInValid() throws Exception {
        // set ticketId length 5 but should be 6
        String userId = "0100000001";
        String ticketId = "12345";

        mockMvc.perform(delete("/users/" + userId + "/lotteries/" + ticketId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }


}