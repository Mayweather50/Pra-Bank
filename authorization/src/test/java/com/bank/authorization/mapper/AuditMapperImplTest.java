package com.bank.authorization.mapper;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.entity.AuditEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class AuditMapperImplTest {

    @InjectMocks
    private AuditMapperImpl auditMapperImpl;

    @Mock
    private AuditMapper auditMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("маппинг в dto")
    public void toDtoTest() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setId(1L);
        auditEntity.setEntityType("User");
        auditEntity.setOperationType("Create");
        auditEntity.setCreatedBy("John");
        auditEntity.setModifiedBy("Jane");
        auditEntity.setCreatedAt(new Timestamp(1640995200000L));
        auditEntity.setModifiedAt(new Timestamp(1640995200000L));
        auditEntity.setNewEntityJson("{}");
        auditEntity.setEntityJson("{}");
        AuditDto expectedAuditDto = new AuditDto();
        expectedAuditDto.setId(1L);
        expectedAuditDto.setEntityType("User");
        expectedAuditDto.setOperationType("Create");
        expectedAuditDto.setCreatedBy("John");
        expectedAuditDto.setModifiedBy("Jane");
        expectedAuditDto.setCreatedAt(new Timestamp(1640995200000L));
        expectedAuditDto.setModifiedAt(new Timestamp(1640995200000L));
        expectedAuditDto.setNewEntityJson("{}");
        expectedAuditDto.setEntityJson("{}");

        when(auditMapper.toDto(auditEntity)).thenReturn(expectedAuditDto);

        AuditDto auditDto = auditMapperImpl.toDto(auditEntity);

        assertEquals(expectedAuditDto, auditDto);
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    public void toDtoNullTest() {
        AuditEntity auditEntity = null;

        AuditDto auditDto = auditMapperImpl.toDto(auditEntity);

        assertNull(auditDto);
    }
}