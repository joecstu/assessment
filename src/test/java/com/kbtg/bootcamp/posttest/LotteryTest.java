package com.kbtg.bootcamp.posttest;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryController;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LotteryTest {

    private MockMvc mockMvc;

    @Mock
    private LotteryService lotteryService;
    @Mock
    private LotteryController lotteryController;
    @Mock
    private LotteryRepository lotteryRepository;


    @BeforeEach
    void setUp() {
        LotteryService lotteryService = new LotteryService(lotteryRepository);
        LotteryController lotteryController = new LotteryController(lotteryService);

        mockMvc = MockMvcBuilders.standaloneSetup(lotteryService, lotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("allUser canGetLotteriesIfThereAreSomeDataInDatabase")
    public void canGetLotteriesIfThereAreSomeDataInDatabase() throws Exception {
        String stringLoterry1 = "000001";
        String stringLoterry2 = "000002";
        String stringLoterry3 = "000003";

        Lottery lottery1 = new Lottery();
        Lottery lottery2 = new Lottery();
        Lottery lottery3 = new Lottery();

        lottery1.setTicketId(stringLoterry1);
        lottery2.setTicketId(stringLoterry2);
        lottery3.setTicketId(stringLoterry3);


        List<Lottery> lotteryList = new ArrayList<>();
        lotteryList.add(lottery1);
        lotteryList.add(lottery2);
        lotteryList.add(lottery3);

        //Mock there are three data in database
        when(lotteryRepository.findAll()).thenReturn(lotteryList);

        String responseExpect = "{\"tickets\":[\"" + stringLoterry1 + "\",\"" + stringLoterry2 + "\",\"" + stringLoterry3 + "\"]}";

        mockMvc.perform(get("/lotteries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpect));
    }

    @Test
    @DisplayName("allUser canGetLotteriesButThereIsNoDataInDatabase")
    public void canGetLotteriesButThereIsNoDataInDatabase() throws Exception {
        List<Lottery> lotteryList = new ArrayList<>();

        //Mock there is no data in database
        when(lotteryRepository.findAll()).thenReturn(lotteryList);

        String responseExpect = "{\"tickets\":[]}";

        mockMvc.perform(get("/lotteries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(responseExpect));
    }


}