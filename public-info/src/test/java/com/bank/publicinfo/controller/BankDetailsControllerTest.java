package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.service.BankDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

class BankDetailsControllerTest {
    @Mock
    private BankDetailsService bankDetailsService;
    @InjectMocks
    private BankDetailsController bankDetailsController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankDetailsController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Создание информации о банке, позитивный сценарий")
    void createPositiveTest() throws Exception {
        BankDetailsDto bankDetailsDto = getBankDetailsDto();
        String bankDetailsJson = objectMapper.writeValueAsString(bankDetailsDto);

        mockMvc.perform(post("/bank/details/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bankDetailsJson))
                .andExpect(status().isOk());

        verify(bankDetailsService, times(1)).create(bankDetailsDto);
    }

    @Test
    @DisplayName("Создание информации о банке без передачи сущности, негативный сценарий")
    void createNoEntityGivenNegativeTest() throws Exception {
        mockMvc.perform(post("/bank/details/create"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Чтение по id, Позитивный сценарий")
    void findByIdPositiveTest() throws Exception {
        BankDetailsDto bankDetailsDto = getBankDetailsDto();
        when(bankDetailsService.findById(1L)).thenReturn(bankDetailsDto);

        mockMvc.perform(get("/bank/details/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.bik").value(2L))
                .andExpect(jsonPath("$.inn").value(3L))
                .andExpect(jsonPath("$.kpp").value(4L))
                .andExpect(jsonPath("$.corAccount").value(BigDecimal.ONE))
                .andExpect(jsonPath("$.city").value("Тест Улица"))
                .andExpect(jsonPath("$.jointStockCompany").value("Тест Акционерная Компания"))
                .andExpect(jsonPath("$.name").value("Тест Название"));

        verify(bankDetailsService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() throws Exception {
        Long id = 2L;
        mockMvc.perform(get("/bank/details/{id}", id))
                .andExpect(jsonPath("$").doesNotHaveJsonPath());
    }

    @Test
    @DisplayName("Чтение по листу ids, Позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<BankDetailsDto> bankDetailsDtoList = List.of(
                getBankDetailsDto(),getBankDetailsDto2(), getBankDetailsDto3()
        );
        List<Long> ids = List.of(1L, 11L, 111L);
        when(bankDetailsService.findAllById(ids)).thenReturn(bankDetailsDtoList);

        mockMvc.perform(get("/bank/details/read/all")
                        .param("ids", String.valueOf(1L), String.valueOf(11L), String.valueOf(111L)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].bik").value(2L))
                .andExpect(jsonPath("$[0].inn").value(3L))
                .andExpect(jsonPath("$[0].kpp").value(4L))
                .andExpect(jsonPath("$[0].corAccount").value(BigDecimal.ONE))
                .andExpect(jsonPath("$[0].city").value("Тест Улица"))
                .andExpect(jsonPath("$[0].jointStockCompany").value("Тест Акционерная Компания"))
                .andExpect(jsonPath("$[0].name").value("Тест Название"))

                .andExpect(jsonPath("$[1].id").value(11L))
                .andExpect(jsonPath("$[1].bik").value(22L))
                .andExpect(jsonPath("$[1].inn").value(33L))
                .andExpect(jsonPath("$[1].kpp").value(44L))
                .andExpect(jsonPath("$[1].corAccount").value(BigDecimal.ZERO))
                .andExpect(jsonPath("$[1].city").value("Тест Улица 2"))
                .andExpect(jsonPath("$[1].jointStockCompany").value("Тест Акционерная Компания 2"))
                .andExpect(jsonPath("$[1].name").value("Тест Название 2"))

                .andExpect(jsonPath("$[2].id").value(111L))
                .andExpect(jsonPath("$[2].bik").value(222L))
                .andExpect(jsonPath("$[2].inn").value(333L))
                .andExpect(jsonPath("$[2].kpp").value(444L))
                .andExpect(jsonPath("$[2].corAccount").value(BigDecimal.TEN))
                .andExpect(jsonPath("$[2].city").value("Тест Улица 3"))
                .andExpect(jsonPath("$[2].jointStockCompany").value("Тест Акционерная Компания 3"))
                .andExpect(jsonPath("$[2].name").value("Тест Название 3"));

        verify(bankDetailsService, times(1)).findAllById(ids);
    }

    @Test
    @DisplayName("Чтение без передачи ids, Негативный сценарий")
    void readAllNoIdGivenNegativeTest() throws Exception {
        mockMvc.perform(get("/bank/details/read/all"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Обновление информации о банке по id, Позитивный сценарий")
    void updatePositiveTest() throws Exception {
        BankDetailsDto bankDetailsDto = getBankDetailsDto();
        bankDetailsDto.setId(2L);
        String bankDetailsJson = objectMapper.writeValueAsString(bankDetailsDto);

        when(bankDetailsService.update(1L, bankDetailsDto)).thenReturn(getBankDetailsDto());

        mockMvc.perform(put("/bank/details/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bankDetailsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.bik").value(2L))
                .andExpect(jsonPath("$.inn").value(3L))
                .andExpect(jsonPath("$.kpp").value(4L))
                .andExpect(jsonPath("$.corAccount").value(BigDecimal.ONE))
                .andExpect(jsonPath("$.city").value("Тест Улица"))
                .andExpect(jsonPath("$.jointStockCompany").value("Тест Акционерная Компания"))
                .andExpect(jsonPath("$.name").value("Тест Название"));

        verify(bankDetailsService, times(1)).update(1L, bankDetailsDto);
    }

    @Test
    @DisplayName("Обновление информацию о банке без передачи сущности, негативный сценарий")
    void updateNoEntityGivenNegativeTest() throws Exception {
        mockMvc.perform(post("/bank/details/update/{id}", 2L))
                .andExpect(status().is4xxClientError());
    }

    BankDetailsDto getBankDetailsDto() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(1L);
        bankDetailsDto.setBik(2L);
        bankDetailsDto.setInn(3L);
        bankDetailsDto.setKpp(4L);
        bankDetailsDto.setCorAccount(BigDecimal.ONE);
        bankDetailsDto.setCity("Тест Улица");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания");
        bankDetailsDto.setName("Тест Название");
        return bankDetailsDto;
    }

    BankDetailsDto getBankDetailsDto2() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(11L);
        bankDetailsDto.setBik(22L);
        bankDetailsDto.setInn(33L);
        bankDetailsDto.setKpp(44L);
        bankDetailsDto.setCorAccount(BigDecimal.ZERO);
        bankDetailsDto.setCity("Тест Улица 2");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания 2");
        bankDetailsDto.setName("Тест Название 2");
        return bankDetailsDto;
    }

    BankDetailsDto getBankDetailsDto3() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(111L);
        bankDetailsDto.setBik(222L);
        bankDetailsDto.setInn(333L);
        bankDetailsDto.setKpp(444L);
        bankDetailsDto.setCorAccount(BigDecimal.TEN);
        bankDetailsDto.setCity("Тест Улица 3");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания 3");
        bankDetailsDto.setName("Тест Название 3");
        return bankDetailsDto;
    }
}