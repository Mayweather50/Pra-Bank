package com.bank.antifraud.controller;

import com.bank.antifraud.controller.impl.AuditServiceImpl;
import com.bank.antifraud.dto.AuditDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditControllerTest {
    @Mock
    private AuditServiceImpl service;
    @InjectMocks
    private AuditController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Чтение аудита по id, позитивный сценарий")
    void readPositiveTest() throws Exception {
        Long id = 1L;
        AuditDto expectedAuditDto = new AuditDto();
        expectedAuditDto.setId(id);

        when(service.findById(id)).thenReturn(expectedAuditDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("Чтение аудита по несуществующему id, негативный сценарий")
    void readNegativeTest() throws Exception {
        Long id = null;

        mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}
