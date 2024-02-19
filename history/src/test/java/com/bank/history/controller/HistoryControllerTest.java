package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.service.HistoryServiceImpl;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HistoryControllerTest {

    @Mock
    private HistoryServiceImpl service;

    @InjectMocks
    private HistoryController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(id);
        historyDto.setPublicBankInfoAuditId(5L);
        ResponseEntity<HistoryDto> expectedHistoryDto = new ResponseEntity<>(historyDto, HttpStatus.OK);

        when(service.readById(id)).thenReturn(expectedHistoryDto.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/history/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(historyDto.getId()))
                .andExpect(jsonPath("$.publicBankInfoAuditId").value(historyDto.getPublicBankInfoAuditId()));
    }

    @Test
    @DisplayName("Чтение по id, негативный сценарий")
    void readByIdNegativeTest() {
        Long id = 1L;
        when(service.readById(id)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.read(id));
    }

    @Test
    @DisplayName("Чтение всех записей по id, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<Long> ids = List.of(1L, 2L);

        HistoryDto historyDto1 = new HistoryDto();
        historyDto1.setId(ids.get(0));
        historyDto1.setPublicBankInfoAuditId(5L);

        HistoryDto historyDto2 = new HistoryDto();
        historyDto2.setId(ids.get(1));
        historyDto2.setAccountAuditId(2L);

        List<HistoryDto> historyDtos = List.of(historyDto1, historyDto2);
        ResponseEntity<List<HistoryDto>> expectedHistoryDtos = new ResponseEntity<>(historyDtos, HttpStatus.OK);

        when(service.readAllById(ids)).thenReturn(expectedHistoryDtos.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/history")
                        .param("id", String.valueOf(ids.get(0)), String.valueOf(ids.get(1)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(historyDto1.getId()))
                .andExpect(jsonPath("$[0].publicBankInfoAuditId")
                        .value(historyDto1.getPublicBankInfoAuditId()))
                .andExpect(jsonPath("$[1].id").value(historyDto2.getId()))
                .andExpect(jsonPath("$[1].accountAuditId").value(historyDto2.getAccountAuditId()));
    }

    @Test
    @DisplayName("Чтение всех записей по id, негативный сценарий")
    void readAllByIdNegativeTest() {
        List<Long> ids = List.of(1L, 2L);
        when(service.readAllById(ids)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.readAll(ids));
    }

    @Test
    @DisplayName("Создание записи, позитивный сценарий")
    void createHistoryPositiveTest() throws Exception {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(1L);
        historyDto.setPublicBankInfoAuditId(5L);
        ResponseEntity<HistoryDto> expectedHistoryDto = new ResponseEntity<>(historyDto, HttpStatus.OK);

        when(service.create(historyDto)).thenReturn(expectedHistoryDto.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(historyDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(historyDto.getId()))
                .andExpect(jsonPath("$.publicBankInfoAuditId").value(historyDto.getPublicBankInfoAuditId()));
    }

    @Test
    @DisplayName("Создание записи, негативный сценарий")
    void createHistoryNegativeTest() {
        HistoryDto historyDto = new HistoryDto();
        when(service.create(historyDto)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.create(historyDto));
    }

    @Test
    @DisplayName("Обновление записи, позитивный сценарий")
    void updateHistoryPositiveTest() throws Exception {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(id);
        historyDto.setAntiFraudAuditId(3L);
        ResponseEntity<HistoryDto> expectedHistoryDto = new ResponseEntity<>(historyDto, HttpStatus.OK);

        when(service.update(id, historyDto)).thenReturn(expectedHistoryDto.getBody());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/history/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(historyDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(historyDto.getId()))
                .andExpect(jsonPath("$.antiFraudAuditId").value(historyDto.getAntiFraudAuditId()));
    }

    @Test
    @DisplayName("Обновление записи, негативный сценарий")
    void updateHistoryNegativeTest() {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(id);
        historyDto.setAntiFraudAuditId(3L);

        when(service.update(id, historyDto)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.update(id, historyDto));
    }
}