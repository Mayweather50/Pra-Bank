package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.BranchService;
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

import java.time.LocalTime;
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
class BranchControllerTest {
    @Mock
    private BranchService branchService;
    @InjectMocks
    private BranchController branchController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    @DisplayName("Чтение информации о branch по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        BranchDto branchDto = getBranchDto();
        when(branchService.findById(1L)).thenReturn(branchDto);

        mockMvc.perform(get("/branch/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.address").value("тест улица"))
                .andExpect(jsonPath("$.phoneNumber").value(123456L))
                .andExpect(jsonPath("$.city").value("тест город"))
                .andExpect(jsonPath("$.startOfWork[0]").value(2))
                .andExpect(jsonPath("$.startOfWork[1]").value(3))
                .andExpect(jsonPath("$.endOfWork[0]").value(4))
                .andExpect(jsonPath("$.endOfWork[1]").value(5));

        verify(branchService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws Exception {
        Long id = 2L;
        mockMvc.perform(get("/branch/{id}", id))
                .andExpect(jsonPath("$").doesNotHaveJsonPath());
    }

    @Test
    @DisplayName("чтение по листу ids, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<BranchDto> branchDtoList = List.of(getBranchDto(),getBranchDto2(), getBranchDto3());
        List<Long> ids = List.of(1L, 2L, 3L);

        when(branchService.findAllById(ids)).thenReturn(branchDtoList);

        mockMvc.perform(get("/branch/read/all", 1L)
                .param("ids", String.valueOf(1L), String.valueOf(2L), String.valueOf(3L)))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].address").value("тест улица"))
                .andExpect(jsonPath("$[0].phoneNumber").value(123456L))
                .andExpect(jsonPath("$[0].city").value("тест город"))
                .andExpect(jsonPath("$[0].startOfWork[0]").value(2))
                .andExpect(jsonPath("$[0].startOfWork[1]").value(3))
                .andExpect(jsonPath("$[0].endOfWork[0]").value(4))
                .andExpect(jsonPath("$[0].endOfWork[1]").value(5))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].address").value("тест улица 2"))
                .andExpect(jsonPath("$[1].phoneNumber").value(123456L))
                .andExpect(jsonPath("$[1].city").value("тест город 2"))
                .andExpect(jsonPath("$[1].startOfWork[0]").value(2))
                .andExpect(jsonPath("$[1].startOfWork[1]").value(3))
                .andExpect(jsonPath("$[1].endOfWork[0]").value(4))
                .andExpect(jsonPath("$[1].endOfWork[1]").value(5))

                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].address").value("тест улица 3"))
                .andExpect(jsonPath("$[2].phoneNumber").value(123456L))
                .andExpect(jsonPath("$[2].city").value("тест город 3"))
                .andExpect(jsonPath("$[2].startOfWork[0]").value(2))
                .andExpect(jsonPath("$[2].startOfWork[1]").value(3))
                .andExpect(jsonPath("$[2].endOfWork[0]").value(4))
                .andExpect(jsonPath("$[2].endOfWork[1]").value(5));

        verify(branchService, times(1)).findAllById(ids);
    }

    @Test
    @DisplayName("Чтение без передачи ids, Негативный сценарий")
    void readAllByIdNoIdGivenNegativeTest() throws Exception {
        mockMvc.perform(get("/branch/read/all"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Создание Branch, позитивный сценарий")
    void createPositiveTest() throws Exception {
        BranchDto branchDto = getBranchDto();
        String branchJson = objectMapper.writeValueAsString(branchDto);

        mockMvc.perform(post("/branch/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(branchJson))
                .andExpect(status().isOk());

        verify(branchService, times(1)).create(branchDto);
    }

    @Test
    @DisplayName("Создание Branch без передачи сущности, негативный сценарий")
    void createNoEntityGivenNegativeTest() throws Exception {
        mockMvc.perform(post("/branch/create"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Обновление branch, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        BranchDto branchDto = getBranchDto();
        branchDto.setId(2L);
        String branchJson = objectMapper.writeValueAsString(branchDto);

        when(branchService.update(1L, branchDto)).thenReturn(getBranchDto());

        mockMvc.perform(put("/branch/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(branchJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.address").value("тест улица"))
                .andExpect(jsonPath("$.phoneNumber").value(123456L))
                .andExpect(jsonPath("$.city").value("тест город"))
                .andExpect(jsonPath("$.startOfWork[0]").value(2))
                .andExpect(jsonPath("$.startOfWork[1]").value(3))
                .andExpect(jsonPath("$.endOfWork[0]").value(4))
                .andExpect(jsonPath("$.endOfWork[1]").value(5));

        verify(branchService, times(1)).update(1L, branchDto);
    }

    @Test
    @DisplayName("Обновление branch без передачи сущности, негативный сценарий")
    void updateNoEntityGivenNegativeTest() throws Exception {
        mockMvc.perform(post("/branch/update/{id}", 2L))
                .andExpect(status().is4xxClientError());
    }

    BranchDto getBranchDto() {
        BranchDto branchDto = new BranchDto();
        branchDto.setId(1L);
        branchDto.setAddress("тест улица");
        branchDto.setPhoneNumber(123456L);
        branchDto.setCity("тест город");
        branchDto.setStartOfWork(LocalTime.of(2, 3));
        branchDto.setEndOfWork(LocalTime.of(4, 5));
        return branchDto;
    }

    BranchDto getBranchDto2() {
        BranchDto branchDto = new BranchDto();
        branchDto.setId(2L);
        branchDto.setAddress("тест улица 2");
        branchDto.setPhoneNumber(123456L);
        branchDto.setCity("тест город 2");
        branchDto.setStartOfWork(LocalTime.of(2, 3));
        branchDto.setEndOfWork(LocalTime.of(4, 5));
        return branchDto;
    }

    BranchDto getBranchDto3() {
        BranchDto branchDto = new BranchDto();
        branchDto.setId(3L);
        branchDto.setAddress("тест улица 3");
        branchDto.setPhoneNumber(123456L);
        branchDto.setCity("тест город 3");
        branchDto.setStartOfWork(LocalTime.of(2, 3));
        branchDto.setEndOfWork(LocalTime.of(4, 5));
        return branchDto;
    }
}