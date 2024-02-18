package com.bank.antifraud.controller;

import com.bank.antifraud.service.impl.SuspiciousAccountTransferServiceImpl;
import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
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
public class SuspiciousAccountTransferControllerTest {
    @Mock
    private SuspiciousAccountTransferServiceImpl service;
    @InjectMocks
    private SuspiciousAccountTransferController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Чтение аккаунта по id, позитивный сценарий")
    void readByIdPositiveTest() {
        Long id = 1L;
        SuspiciousAccountTransferDto expectedDto = new SuspiciousAccountTransferDto();
        when(service.findById(id)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousAccountTransferDto> response = controller.read(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
    }

    @Test
    @DisplayName("Чтение аккаунта по id, негативный сценарий")
    void readByIdNegativeTest() {
        Long id = 1L;
        when(service.findById(id)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.read(id));
    }

    @Test
    @DisplayName("Чтение всех аккаунтов по id, позитивный сценарий")
    public void readAllByIdPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        List<SuspiciousAccountTransferDto> expectedDtos = new ArrayList<>();
        when(service.findAllById(ids)).thenReturn(expectedDtos);

        ResponseEntity<List<SuspiciousAccountTransferDto>> response = controller.readAll(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDtos, response.getBody());
    }

    @Test
    @DisplayName("Чтение всех аккаунтов по id, негативный сценарий")
    public void readAllByIdNegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        when(service.findAllById(ids)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.readAll(ids));
    }

    @Test
    @DisplayName("Создание аккаунта, позитивный сценарий")
    public void createAccountPositiveTest() {
        SuspiciousAccountTransferDto suspiciousTransfer = new SuspiciousAccountTransferDto();

        when(service.save(suspiciousTransfer))
                .thenReturn(suspiciousTransfer);

        ResponseEntity<SuspiciousAccountTransferDto> response = controller.create(suspiciousTransfer);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Создание аккаунта, негативный сценарий")
    public void createAccountNegativeTest() {
        SuspiciousAccountTransferDto suspiciousTransfer = new SuspiciousAccountTransferDto();
        when(service.save(suspiciousTransfer)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.create(suspiciousTransfer));
    }

    @Test
    @DisplayName("Обновление данных аккаунта, позитивный сценарий")
    public void updateAccountPositiveTest() throws Exception {
        Long id = 1L;
        Long transferId = 123L;
        String blockedReason = "Suspicious activity detected";

        SuspiciousAccountTransferDto suspiciousTransfer = new SuspiciousAccountTransferDto();
        suspiciousTransfer.setId(id);
        suspiciousTransfer.setAccountTransferId(transferId);
        suspiciousTransfer.setBlockedReason(blockedReason);
        when(service.update(any(Long.class), any(SuspiciousAccountTransferDto.class)))
                .thenReturn(suspiciousTransfer);

        mockMvc.perform(MockMvcRequestBuilders.put("/suspicious/account/transfer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"accountTransferId\"" +
                                 ": 123, \"blockedReason\": \"Suspicious activity detected\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountTransferId").value(123))
                .andExpect(jsonPath("$.blockedReason").value("Suspicious activity detected"));

        verify(service).update(1L, suspiciousTransfer);
    }

    @Test
    @DisplayName("Обновление данных аккаунта, негативный сценарий")
    public void updateAccountNegativeTest() {
        Long id = -1L;
        Long transferId = 123L;
        String blockedReason = "Suspicious activity detected";

        SuspiciousAccountTransferDto suspiciousTransfer = new SuspiciousAccountTransferDto();
        suspiciousTransfer.setId(id);
        suspiciousTransfer.setAccountTransferId(transferId);
        suspiciousTransfer.setBlockedReason(blockedReason);

        when(service.update(id, suspiciousTransfer)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.update(suspiciousTransfer, id));
    }
}
