package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.service.AuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.sql.Timestamp;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuditControllerTest {
    @Mock
    private AuditService auditService;
    @InjectMocks
    private AuditController auditController;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(auditController).build();
    }

    @Test
    @DisplayName("Чтение по id, Позитивный сценарий")
    void readPositiveTest() throws Exception {
        AuditDto auditDto = getAuditDto();
        when(auditService.findById(1L)).thenReturn(auditDto);

        mockMvc.perform(get("/audit/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.entityType").value("Тест entityType"))
                .andExpect(jsonPath("$.operationType").value("Тест operationType"))
                .andExpect(jsonPath("$.createdBy").value("Тест createdBy"))
                .andExpect(jsonPath("$.modifiedBy").value("Тест modifiedBy"))
                .andExpect(jsonPath("$.createdAt").value("1704093010000"))
                .andExpect(jsonPath("$.modifiedAt").value("1704179410000"))
                .andExpect(jsonPath("$.newEntityJson").value("{}"))
                .andExpect(jsonPath("$.entityJson").value("{}"));

        verify(auditService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, Негативный сценарий")
    void readByNonExistNegativeTest() throws Exception {
        Long id = 2L;
        mockMvc.perform(get("/audit/{id}", id))
                .andExpect(jsonPath("$").doesNotHaveJsonPath());
    }

    private AuditDto getAuditDto() {
        AuditDto auditDto = new AuditDto();
        auditDto.setId(1L);
        auditDto.setEntityType("Тест entityType");
        auditDto.setOperationType("Тест operationType");
        auditDto.setCreatedBy("Тест createdBy");
        auditDto.setModifiedBy("Тест modifiedBy");
        auditDto.setCreatedAt(new Timestamp(1704093010000L));
        auditDto.setModifiedAt(new Timestamp(1704179410000L));
        auditDto.setNewEntityJson("{}");
        auditDto.setEntityJson("{}");
        return auditDto;
    }
}