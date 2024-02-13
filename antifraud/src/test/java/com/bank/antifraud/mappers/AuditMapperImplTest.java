package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditMapperImplTest {
    @InjectMocks
    private AuditMapperImpl mapper;
    @Mock
    private AuditEntity auditEntity;

    @Test
    @DisplayName("Маппинг в DTO")
    void toDtoTest() {
        when(auditEntity.getId()).thenReturn(1L);
        when(auditEntity.getEntityType()).thenReturn("User");
        when(auditEntity.getOperationType()).thenReturn("Create");
        when(auditEntity.getCreatedBy()).thenReturn("John");
        when(auditEntity.getModifiedBy()).thenReturn("James");
        when(auditEntity.getCreatedAt()).thenReturn(Timestamp.valueOf("2024-01-01 10:10:10.0000001"));
        when(auditEntity.getModifiedAt()).thenReturn(Timestamp.valueOf("2024-01-02 10:10:10"));
        when(auditEntity.getNewEntityJson()).thenReturn("{\"name\":\"John Doe\",\"age\":30}");
        when(auditEntity.getEntityJson()).thenReturn("{\"name\":\"John Doe\",\"age\":31}");

        AuditDto auditDto = mapper.toDto(auditEntity);

        assertNotNull(auditDto);
        assertEquals(1L, auditDto.getId());
        assertEquals("User", auditDto.getEntityType());
        assertEquals("Create", auditDto.getOperationType());
        assertEquals("John", auditDto.getCreatedBy());
        assertEquals("James", auditDto.getModifiedBy());
        assertEquals("2024-01-01 10:10:10.0000001", auditDto.getCreatedAt().toString());
        assertEquals("2024-01-02 10:10:10.0", auditDto.getModifiedAt().toString());
        assertEquals("{\"name\":\"John Doe\",\"age\":30}", auditDto.getNewEntityJson());
        assertEquals("{\"name\":\"John Doe\",\"age\":31}", auditDto.getEntityJson());
    }

    @Test
    @DisplayName("Маппинг в DTO, на вход подан null")
    void toDtoNullNullTest() {
        AuditDto auditDto = mapper.toDto(null);
        assertNull(auditDto);
    }
}
