package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AuditDto;
import com.bank.publicinfo.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AuditMapperTest {
    @InjectMocks
    AuditMapperImpl mapper;
    @Mock
    AuditDto dto;
    @Mock
    AuditEntity entity;

    @Test
    @DisplayName("Маппинг к dto")
    void toDtoTest() {
        when(entity.getId()).thenReturn(1L);
        when(entity.getEntityType()).thenReturn("тест");
        when(entity.getOperationType()).thenReturn("тест 2");
        when(entity.getCreatedBy()).thenReturn("тест 3");
        when(entity.getModifiedBy()).thenReturn("тест 4");
        when(entity.getCreatedAt()).thenReturn(new Timestamp(1704093010000L));
        when(entity.getModifiedAt()).thenReturn(new Timestamp(1704179410000L));
        when(entity.getNewEntityJson()).thenReturn("{}");
        when(entity.getEntityJson()).thenReturn("{}");

        dto = mapper.toDto(entity);

        assertEquals(dto.getId(), 1L);
        assertEquals(dto.getEntityType(), "тест");
        assertEquals(dto.getOperationType(), "тест 2");
        assertEquals(dto.getCreatedBy(), "тест 3");
        assertEquals(dto.getModifiedBy(), "тест 4");
        assertEquals(dto.getCreatedAt(), new Timestamp(1704093010000L));
        assertEquals(dto.getModifiedAt(), new Timestamp(1704179410000L));
        assertEquals(dto.getNewEntityJson(), "{}");
        assertEquals(dto.getEntityJson(), "{}");
    }

    @Test
    @DisplayName("Маппинг к dto, на вход подан null")
    void toDtoNullTest() {
        dto = mapper.toDto(null);

        assertNull(dto);
    }
}