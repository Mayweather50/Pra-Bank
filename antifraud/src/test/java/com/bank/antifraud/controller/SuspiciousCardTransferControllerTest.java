package com.bank.antifraud.controller;

import com.bank.antifraud.controller.impl.SuspiciousCardTransferServiceImpl;
import com.bank.antifraud.dto.SuspiciousCardTransferDto;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SuspiciousCardTransferControllerTest {
    @Mock
    private SuspiciousCardTransferServiceImpl service;
    @InjectMocks
    private SuspiciousCardTransferController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Чтение карточки по id, позитивный сценарий")
    void readByIdPositiveTest() {
        Long id = 1L;
        SuspiciousCardTransferDto expectedDto = new SuspiciousCardTransferDto();
        when(service.findById(id)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousCardTransferDto> response = controller.read(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
    }

    @Test
    @DisplayName("Чтение карточки по id, негативный сценарий")
    void readByIdNegativeTest() {
        Long id = 1L;
        when(service.findById(id)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.read(id));
    }

    @Test
    @DisplayName("Чтение всех карточек по id, позитивный сценарий")
    public void readAllByIdPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        List<SuspiciousCardTransferDto> expectedDtos = new ArrayList<>();
        when(service.findAllById(ids)).thenReturn(expectedDtos);

        ResponseEntity<List<SuspiciousCardTransferDto>> response = controller.readAll(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDtos, response.getBody());
    }

    @Test
    @DisplayName("Чтение всех карточек по id, негативный сценарий")
    public void readAllByIdNegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        when(service.findAllById(ids)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.readAll(ids));
    }

    @Test
    @DisplayName("Создание карточки, позитивный сценарий")
    public void createCardPositiveTest() {
        SuspiciousCardTransferDto suspiciousTransfer = new SuspiciousCardTransferDto();

        when(service.save(suspiciousTransfer))
                .thenReturn(suspiciousTransfer);

        ResponseEntity<SuspiciousCardTransferDto> response = controller.create(suspiciousTransfer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Создание карточки, негативный сценарий")
    public void createCardNegativeTest() {
        SuspiciousCardTransferDto suspiciousTransfer = new SuspiciousCardTransferDto();
        when(service.save(suspiciousTransfer)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.create(suspiciousTransfer));
    }

    @Test
    @DisplayName("Обновление данных карточки, позитивный сценарий")
    public void updateCardPositiveTest() throws Exception {
        Long id = 1L;
        Long transferId = 123L;
        String blockedReason = "Suspicious activity detected";

        SuspiciousCardTransferDto suspiciousTransfer = new SuspiciousCardTransferDto();
        suspiciousTransfer.setId(id);
        suspiciousTransfer.setCardTransferId(transferId);
        suspiciousTransfer.setBlockedReason(blockedReason);
        when(service.update(any(Long.class), any(SuspiciousCardTransferDto.class)))
                .thenReturn(suspiciousTransfer);

        mockMvc.perform(MockMvcRequestBuilders.put("/suspicious/card/transfer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"cardTransferId\"" +
                                 ": 123, \"blockedReason\": \"Suspicious activity detected\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cardTransferId").value(123))
                .andExpect(jsonPath("$.blockedReason").value("Suspicious activity detected"));

        verify(service).update(1L, suspiciousTransfer);
    }

    @Test
    @DisplayName("Обновление данных карточки, негативный сценарий")
    public void updateCardNegativeTest() {
        Long id = -1L;
        Long transferId = 123L;
        String blockedReason = "Suspicious activity detected";

        SuspiciousCardTransferDto suspiciousTransfer = new SuspiciousCardTransferDto();
        suspiciousTransfer.setId(id);
        suspiciousTransfer.setCardTransferId(transferId);
        suspiciousTransfer.setBlockedReason(blockedReason);

        when(service.update(id, suspiciousTransfer)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.update(suspiciousTransfer, id));
    }
}
