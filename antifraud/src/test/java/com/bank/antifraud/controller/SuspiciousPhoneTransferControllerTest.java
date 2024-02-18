package com.bank.antifraud.controller;

import com.bank.antifraud.service.impl.SuspiciousPhoneTransferServiceImpl;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SuspiciousPhoneTransferControllerTest {
    @Mock
    private SuspiciousPhoneTransferServiceImpl service;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private SuspiciousPhoneTransferController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Чтение телефона по id, позитивный сценарий")
    void readByIdPositiveTest() {
        Long id = 1L;
        SuspiciousPhoneTransferDto expectedDto = new SuspiciousPhoneTransferDto();
        when(service.findById(id)).thenReturn(expectedDto);

        ResponseEntity<SuspiciousPhoneTransferDto> response = controller.read(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
    }

    @Test
    @DisplayName("Чтение телефона по id, негативный сценарий")
    void readByIdNegativeTest() {
        Long id = 1L;
        when(service.findById(id)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.read(id));
    }

    @Test
    @DisplayName("Чтение всех телефонов по id, позитивный сценарий")
    public void readAllByIdPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        List<SuspiciousPhoneTransferDto> expectedDtos = new ArrayList<>();
        when(service.findAllById(ids)).thenReturn(expectedDtos);

        ResponseEntity<List<SuspiciousPhoneTransferDto>> response = controller.readAll(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDtos, response.getBody());
    }

    @Test
    @DisplayName("Чтение всех телефонов по id, негативный сценарий")
    public void readAllByIdNegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        when(service.findAllById(ids)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.readAll(ids));
    }

    @Test
    @DisplayName("Создание телефона, позитивный сценарий")
    public void createPhonePositiveTest() {
        SuspiciousPhoneTransferDto suspiciousTransfer = new SuspiciousPhoneTransferDto();

        when(service.save(suspiciousTransfer))
                .thenReturn(suspiciousTransfer);

        ResponseEntity<SuspiciousPhoneTransferDto> response = controller.create(suspiciousTransfer);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @DisplayName("Создание телефона, негативный сценарий")
    public void createPhoneNegativeTest() {
        SuspiciousPhoneTransferDto suspiciousTransfer = new SuspiciousPhoneTransferDto();
        when(service.save(suspiciousTransfer)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.create(suspiciousTransfer));
    }

    @Test
    @DisplayName("Обновление данных телефона, позитивный сценарий")
    public void updatePhonePositiveTest() throws Exception {
        Long id = 1L;
        Long transferId = 123L;
        String blockedReason = "Suspicious activity detected";

        SuspiciousPhoneTransferDto suspiciousTransfer = new SuspiciousPhoneTransferDto();
        suspiciousTransfer.setId(id);
        suspiciousTransfer.setPhoneTransferId(transferId);
        suspiciousTransfer.setBlockedReason(blockedReason);
        when(service.update(any(Long.class), any(SuspiciousPhoneTransferDto.class)))
                .thenReturn(suspiciousTransfer);

        mockMvc.perform(MockMvcRequestBuilders.put("/suspicious/phone/transfer/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"phoneTransferId\"" +
                                 ": 123, \"blockedReason\": \"Suspicious activity detected\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.phoneTransferId").value(123))
                .andExpect(jsonPath("$.blockedReason").value("Suspicious activity detected"));

        verify(service).update(1L, suspiciousTransfer);
    }

    @Test
    @DisplayName("Обновление данных телефона, негативный сценарий")
    public void updatePhoneNegativeTest() {
        Long id = -1L;
        Long transferId = 123L;
        String blockedReason = "Suspicious activity detected";

        SuspiciousPhoneTransferDto suspiciousTransfer = new SuspiciousPhoneTransferDto();
        suspiciousTransfer.setId(id);
        suspiciousTransfer.setPhoneTransferId(transferId);
        suspiciousTransfer.setBlockedReason(blockedReason);

        when(service.update(id, suspiciousTransfer)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> controller.update(suspiciousTransfer, id));
    }
}
