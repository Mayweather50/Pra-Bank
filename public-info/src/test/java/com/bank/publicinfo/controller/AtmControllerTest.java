package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.AtmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
class AtmControllerTest {
    @Mock
    private AtmService atmService;
    @InjectMocks
    private AtmController atmController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(atmController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        AtmDto atmDto = getAtmDto();
        when(atmService.findById(1L)).thenReturn(atmDto);

        mockMvc.perform(get("/atm/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.address").value("Тестовый Адрес"))
                .andExpect(jsonPath("$.allHours").value(true))

                .andExpect(jsonPath("$.branch.id").value(1L))
                .andExpect(jsonPath("$.branch.address").value("тест улица"))
                .andExpect(jsonPath("$.branch.phoneNumber").value(123456L))
                .andExpect(jsonPath("$.branch.city").value("тест город"))
                .andExpect(jsonPath("$.branch.startOfWork[0]").value(2))
                .andExpect(jsonPath("$.branch.startOfWork[1]").value(3))
                .andExpect(jsonPath("$.branch.endOfWork[0]").value(4))
                .andExpect(jsonPath("$.branch.endOfWork[1]").value(5))

                .andExpect(jsonPath("$.startOfWork[0]").value(2))
                .andExpect(jsonPath("$.startOfWork[1]").value(3))
                .andExpect(jsonPath("$.endOfWork[0]").value(4))
                .andExpect(jsonPath("$.endOfWork[1]").value(5));

        verify(atmService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("чтение по несуществующему  id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/atm/{id}", id))
                .andExpect(jsonPath("$").doesNotHaveJsonPath());
    }

    @Test
    @DisplayName("чтение по листу ids, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<AtmDto> atmDtoList = List.of(getAtmDto(),getAtmDto2(), getAtmDto3());
        List<Long> ids = List.of(1L, 2L, 3L);
        when(atmService.findAllById(ids)).thenReturn(atmDtoList);

        mockMvc.perform(get("/atm/read/all")
                .param("ids", String.valueOf(1L), String.valueOf(2L), String.valueOf(3L)))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].address").value("Тестовый Адрес"))
                .andExpect(jsonPath("$[0].allHours").value(true))

                .andExpect(jsonPath("$[0].branch.id").value(1L))
                .andExpect(jsonPath("$[0].branch.address").value("тест улица"))
                .andExpect(jsonPath("$[0].branch.phoneNumber").value(123456L))
                .andExpect(jsonPath("$[0].branch.city").value("тест город"))
                .andExpect(jsonPath("$[0].branch.startOfWork[0]").value(2))
                .andExpect(jsonPath("$[0].branch.startOfWork[1]").value(3))
                .andExpect(jsonPath("$[0].branch.endOfWork[0]").value(4))
                .andExpect(jsonPath("$[0].branch.endOfWork[1]").value(5))

                .andExpect(jsonPath("$[0].startOfWork[0]").value(2))
                .andExpect(jsonPath("$[0].startOfWork[1]").value(3))
                .andExpect(jsonPath("$[0].endOfWork[0]").value(4))
                .andExpect(jsonPath("$[0].endOfWork[1]").value(5))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].address").value("Тестовый Адрес 2"))
                .andExpect(jsonPath("$[1].allHours").value(true))

                .andExpect(jsonPath("$[1].branch.id").value(1L))
                .andExpect(jsonPath("$[1].branch.address").value("тест улица"))
                .andExpect(jsonPath("$[1].branch.phoneNumber").value(123456L))
                .andExpect(jsonPath("$[1].branch.city").value("тест город"))
                .andExpect(jsonPath("$[1].branch.startOfWork[0]").value(2))
                .andExpect(jsonPath("$[1].branch.startOfWork[1]").value(3))
                .andExpect(jsonPath("$[1].branch.endOfWork[0]").value(4))
                .andExpect(jsonPath("$[1].branch.endOfWork[1]").value(5))

                .andExpect(jsonPath("$[1].startOfWork[0]").value(2))
                .andExpect(jsonPath("$[1].startOfWork[1]").value(3))
                .andExpect(jsonPath("$[1].endOfWork[0]").value(4))
                .andExpect(jsonPath("$[1].endOfWork[1]").value(5))

                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].address").value("Тестовый Адрес 3"))
                .andExpect(jsonPath("$[2].allHours").value(true))

                .andExpect(jsonPath("$[2].branch.id").value(1L))
                .andExpect(jsonPath("$[2].branch.address").value("тест улица"))
                .andExpect(jsonPath("$[2].branch.phoneNumber").value(123456L))
                .andExpect(jsonPath("$[2].branch.city").value("тест город"))
                .andExpect(jsonPath("$[2].branch.startOfWork[0]").value(2))
                .andExpect(jsonPath("$[2].branch.startOfWork[1]").value(3))
                .andExpect(jsonPath("$[2].branch.endOfWork[0]").value(4))
                .andExpect(jsonPath("$[2].branch.endOfWork[1]").value(5))

                .andExpect(jsonPath("$[2].startOfWork[0]").value(2))
                .andExpect(jsonPath("$[2].startOfWork[1]").value(3))
                .andExpect(jsonPath("$[2].endOfWork[0]").value(4))
                .andExpect(jsonPath("$[2].endOfWork[1]").value(5));

                verify(atmService, times(1)).findAllById(ids);
    }

    @Test
    @DisplayName("Чтение без передачи ids, Негативный сценарий")
    void readAllNoIdGivenNegativeTest() throws Exception {
        mockMvc.perform(get("/atm/read/all"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Создание банкомата, позитивный сценарий")
    void createPositiveTest() throws Exception {
        AtmDto atmDto = getAtmDto();

        String atmJson = objectMapper.writeValueAsString(atmDto);
        ResponseEntity<AtmDto> expectedAtmDto = new ResponseEntity<>(atmDto, HttpStatus.OK);

        when(atmService.create(atmDto)).thenReturn(expectedAtmDto.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/atm/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(atmJson))
                    .andExpect(MockMvcResultMatchers.status().isOk());

        verify(atmService, times(1)).create(atmDto);
    }

    @Test
    @DisplayName("Создание банкомата без передачи сущности, негативный сценарий")
    void createNoEntityGivenNegativeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/atm/create"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("Обновление информацию о банкомате, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        AtmDto atmDto = getAtmDto();
        atmDto.setId(2L);
        String atmJson = objectMapper.writeValueAsString(atmDto);

        when(atmService.update(1L, atmDto)).thenReturn(getAtmDto());

        mockMvc.perform(put("/atm/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(atmJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.address").value("Тестовый Адрес"))
                .andExpect(jsonPath("$.allHours").value(true))

                .andExpect(jsonPath("$.branch.id").value(1L))
                .andExpect(jsonPath("$.branch.address").value("тест улица"))
                .andExpect(jsonPath("$.branch.phoneNumber").value(123456L))
                .andExpect(jsonPath("$.branch.city").value("тест город"))
                .andExpect(jsonPath("$.branch.startOfWork[0]").value(2))
                .andExpect(jsonPath("$.branch.startOfWork[1]").value(3))
                .andExpect(jsonPath("$.branch.endOfWork[0]").value(4))
                .andExpect(jsonPath("$.branch.endOfWork[1]").value(5))

                .andExpect(jsonPath("$.startOfWork[0]").value(2))
                .andExpect(jsonPath("$.startOfWork[1]").value(3))
                .andExpect(jsonPath("$.endOfWork[0]").value(4))
                .andExpect(jsonPath("$.endOfWork[1]").value(5));

        verify(atmService, times(1)).update(1L, atmDto);
    }

    @Test
    @DisplayName("Обновление информацию о банкомате без передачи сущности, негативный сценарий")
    void updateNoEntityGivenNegativeTest() throws Exception{
        mockMvc.perform(post("/atm/update/{id}", 2L))
                .andExpect(status().is4xxClientError());
    }

    AtmDto getAtmDto() {
        AtmDto atmDto = new AtmDto();
        atmDto.setId(1L);
        atmDto.setAddress("Тестовый Адрес");
        atmDto.setAllHours(true);
        atmDto.setBranch(getBranchDto());
        atmDto.setStartOfWork(LocalTime.of(2, 3));
        atmDto.setEndOfWork(LocalTime.of(4, 5));
        return atmDto;
    }

    private AtmDto getAtmDto2() {
        AtmDto atmDto = new AtmDto();
        atmDto.setId(2L);
        atmDto.setAddress("Тестовый Адрес 2");
        atmDto.setAllHours(true);
        atmDto.setBranch(getBranchDto());
        atmDto.setStartOfWork(LocalTime.of(2, 3));
        atmDto.setEndOfWork(LocalTime.of(4, 5));
        return atmDto;
    }

    private AtmDto getAtmDto3() {
        AtmDto atmDto = new AtmDto();
        atmDto.setId(3L);
        atmDto.setAddress("Тестовый Адрес 3");
        atmDto.setAllHours(true);
        atmDto.setBranch(getBranchDto());
        atmDto.setStartOfWork(LocalTime.of(2, 3));
        atmDto.setEndOfWork(LocalTime.of(4, 5));
        return atmDto;
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
}