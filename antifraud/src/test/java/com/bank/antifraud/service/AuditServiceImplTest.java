package com.bank.antifraud.service;

import com.bank.antifraud.controller.AuditController;
import com.bank.antifraud.controller.impl.AuditServiceImpl;
import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import com.bank.antifraud.mappers.AuditMapper;
import com.bank.antifraud.repository.AuditRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AuditServiceImplTest {
    @InjectMocks
    private AuditServiceImpl service;
    @Mock
    private AuditRepository repository;
    @Mock
    private AuditMapper mapper;
    @InjectMocks
    private AuditController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Поиск аудита по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setId(id);
        AuditDto expectedDto = new AuditDto();
        expectedDto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(auditEntity));
        when(mapper.toDto(auditEntity)).thenReturn(expectedDto);

        AuditDto actualDto = service.findById(id);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    @DisplayName("Поиск аудита по id, негативный сценарий")
    public void findByIdNegativeTest() {
        Long id = 1L;
        AuditRepository repository = mock(AuditRepository.class);
        when(repository.findById(id)).thenReturn(Optional.empty());
        AuditServiceImpl service = new AuditServiceImpl(repository, mapper);
        try {
            service.findById(id);
        } catch (EntityNotFoundException e) {
            assertEquals("Не найден аудит с ID  " + id, e.getMessage());
        }
        verify(repository, times(1)).findById(id);
    }
}