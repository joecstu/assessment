package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.admin.AdminController;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminTest {

    private MockMvc mockMvc;

    @Mock
    private LotteryService lotteryService;
    @Mock
    private AdminController adminController;
    @Mock
    private LotteryRepository lotteryRepository;


    @BeforeEach
    void setUp() {
        LotteryService lotteryService = new LotteryService(lotteryRepository);
        AdminController adminController = new AdminController(lotteryService);

        mockMvc = MockMvcBuilders.standaloneSetup(lotteryService, adminController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("admin carCreateLotteryShouldSuccessBecauseThereIsNoDataInDatabase")
    public void carCreateLotteryShouldSuccessBecauseThereIsNoDataInDatabase() throws Exception {
        String ticketId = "123456";
        String price = "80";
        String amount = "1";

        String bodyRequest = "{\n\t\"ticket\": \"" + ticketId + "\",\n\t\"price\": " + price + ",\n\t\"amount\": " + amount + "\n}";
        String responseExpect = "{\"ticket\":\"" + ticketId + "\"}";

        //Mock there is no data in database
        when(lotteryRepository.findByTicketId(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyRequest))
                .andExpect(status().isCreated())
                .andExpect(content().string(responseExpect));
    }

    @Test
    @DisplayName("admin canNotCreateLotteryShouldNotSuccessBecauseThereIsADataInDatabase")
    public void canNotCreateLotteryShouldNotSuccessBecauseThereIsADataInDatabase() {
        String ticketId = "123456";
        String price = "80";
        String amount = "1";

        Lottery lottery = new Lottery();

        lottery.setTicketId(ticketId);
        lottery.setPrice(Long.parseLong(price));
        lottery.setAmount(Long.parseLong(amount));

        //Mock there is a data in database
        when(lotteryRepository.findByTicketId(anyString())).thenReturn(Optional.of(lottery));

        String bodyRequest = "{\n\t\"ticket\": \"" + ticketId + "\",\n\t\"price\": " + price + ",\n\t\"amount\": " + amount + "\n}";
        String errorExpect = "Request processing failed: com.kbtg.bootcamp.posttest.exception.BadRequestException: Invalid ticketId";

        try {
            mockMvc.perform(post("/admin/lotteries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(bodyRequest));
        } catch (Exception e) {
            assertEquals(errorExpect, e.getMessage());
        }
    }

    @Test
    @DisplayName("admin canNotCreateLotteryShouldReturnInvalidInputTicketId")
    public void canNotCreateLotteryShouldReturnInvalidInputTicketId() throws Exception {
        // ticketId length 5 is invalid
        String ticketId = "12345";
        String price = "80";
        String amount = "1";

        String bodyRequest = "{\n\t\"ticket\": \"" + ticketId + "\",\n\t\"price\": " + price + ",\n\t\"amount\": " + amount + "\n}";

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyRequest))
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("admin canNotCreateLotteryShouldReturnInvalidInputPrice")
    public void canNotCreateLotteryShouldReturnInvalidInputPrice() throws Exception {
        // price is empty
        String ticketId = "123456";
        String price = "";
        String amount = "1";

        String bodyRequest = "{\n\t\"ticket\": \"" + ticketId + "\",\n\t\"price\": " + price + ",\n\t\"amount\": " + amount + "\n}";

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyRequest))
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("admin canNotCreateLotteryShouldReturnInvalidInputAmount")
    public void canNotCreateLotteryShouldReturnInvalidInputAmount() throws Exception {
        // amount is empty
        String ticketId = "123456";
        String price = "80";
        String amount = "";

        String bodyRequest = "{\n\t\"ticket\": \"" + ticketId + "\",\n\t\"price\": " + price + ",\n\t\"amount\": " + amount + "\n}";

        mockMvc.perform(post("/admin/lotteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyRequest))
                .andExpect(status().is(400));
    }


}