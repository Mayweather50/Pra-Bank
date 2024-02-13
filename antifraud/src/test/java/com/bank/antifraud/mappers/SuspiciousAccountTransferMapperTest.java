package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuspiciousAccountTransferMapperTest {
    @InjectMocks
    private SuspiciousAccountTransferMapperImpl mapper;
    @Mock
    private SuspiciousAccountTransferEntity entity;

    @Test
    @DisplayName("Маппинг в DT")
    void toDtoByIdTest() {
        when(entity.getId()).thenReturn(1L);
        SuspiciousAccountTransferDto dto = mapper.toDto(entity);
        assertEquals(entity.getId(), dto.getId());
    }

    @Test
    @DisplayName("Маппинг в DTO, на вход подан null")
    void toDtoNullNullTest() {
        SuspiciousAccountTransferDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setAccountTransferId(1L);
        dto.setIsBlocked(true);
        dto.setIsSuspicious(true);
        dto.setBlockedReason("Fraudulent activity");
        dto.setSuspiciousReason("Unusual transaction");

        SuspiciousAccountTransferEntity entity = mapper.toEntity(dto);

        assertNotNull(entity);

        assertEquals(dto.getAccountTransferId(), entity.getAccountTransferId());
        assertEquals(dto.getIsBlocked(), entity.getIsBlocked());
        assertEquals(dto.getIsSuspicious(), entity.getIsSuspicious());
        assertEquals(dto.getBlockedReason(), entity.getBlockedReason());
        assertEquals(dto.getSuspiciousReason(), entity.getSuspiciousReason());
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void toEntityNullTest() {
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setAccountTransferId(1L);
        dto.setIsBlocked(true);
        dto.setIsSuspicious(true);
        dto.setBlockedReason("Fraudulent activity");
        dto.setSuspiciousReason("Unusual transaction");

        SuspiciousAccountTransferEntity entity = mapper.toEntity(null);

        assertNull(entity);

        if (entity != null) {
            assertEquals(dto.getAccountTransferId(), entity.getAccountTransferId());
            assertEquals(dto.getIsBlocked(), entity.getIsBlocked());
            assertEquals(dto.getIsSuspicious(), entity.getIsSuspicious());
            assertEquals(dto.getBlockedReason(), entity.getBlockedReason());
            assertEquals(dto.getSuspiciousReason(), entity.getSuspiciousReason());
        }
    }

    @Test
    @DisplayName("Маппинг в список DTO")
    void toListDtoTest() {
        List<SuspiciousAccountTransferEntity> entities = new ArrayList<>();
        SuspiciousAccountTransferEntity entity1 = new SuspiciousAccountTransferEntity();
        entity1.setId(1L);
        entity1.setAccountTransferId(100L);
        entity1.setIsBlocked(true);
        entity1.setIsSuspicious(true);
        entity1.setBlockedReason("Fraudulent activity");
        entity1.setSuspiciousReason("Unusual transaction");
        entities.add(entity1);

        SuspiciousAccountTransferEntity entity2 = new SuspiciousAccountTransferEntity();
        entity2.setId(2L);
        entity2.setAccountTransferId(200L);
        entity2.setIsBlocked(false);
        entity2.setIsSuspicious(true);
        entity2.setBlockedReason(null);
        entity2.setSuspiciousReason("High amount transfer");
        entities.add(entity2);

        List<SuspiciousAccountTransferDto> dtos = mapper.toListDto(entities);

        assertEquals(2, dtos.size());

        SuspiciousAccountTransferDto dto1 = dtos.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals(100L, dto1.getAccountTransferId());
        assertTrue(dto1.getIsBlocked());
        assertTrue(dto1.getIsSuspicious());
        assertEquals("Fraudulent activity", dto1.getBlockedReason());
        assertEquals("Unusual transaction", dto1.getSuspiciousReason());

        SuspiciousAccountTransferDto dto2 = dtos.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals(200L, dto2.getAccountTransferId());
        assertFalse(dto2.getIsBlocked());
        assertTrue(dto2.getIsSuspicious());
        assertNull(dto2.getBlockedReason());
        assertEquals("High amount transfer", dto2.getSuspiciousReason());

        List<SuspiciousAccountTransferEntity> suspiciousTransfers = null;
        List<SuspiciousAccountTransferDto> result = mapper.toListDto(suspiciousTransfers);
        assertNull(result);
    }

    @Test
    @DisplayName("Маппинг в список DTO, на вход подан null")
    void toListDtoNullTest() {
        List<SuspiciousAccountTransferEntity> entities = new ArrayList<>();
        SuspiciousAccountTransferEntity entity1 = new SuspiciousAccountTransferEntity();
        entity1.setId(11L);
        entity1.setAccountTransferId(1100L);
        entity1.setIsBlocked(true);
        entity1.setIsSuspicious(true);
        entity1.setBlockedReason(null);
        entity1.setSuspiciousReason(null);
        entities.add(entity1);

        SuspiciousAccountTransferEntity entity2 = new SuspiciousAccountTransferEntity();
        entity2.setId(20L);
        entity2.setAccountTransferId(200L);
        entity2.setIsBlocked(true);
        entity2.setIsSuspicious(true);
        entity2.setBlockedReason("Nothing");
        entity2.setSuspiciousReason(null);
        entities.add(entity2);

        List<SuspiciousAccountTransferDto> dtos = mapper.toListDto(entities);

        assertNotEquals(1, dtos.size());


        SuspiciousAccountTransferDto dto1 = dtos.get(0);
        assertNotEquals(1L, dto1.getId());
        assertNotEquals(100L, dto1.getAccountTransferId());
        assertTrue(dto1.getIsBlocked());
        assertTrue(dto1.getIsSuspicious());
        assertNotEquals("Fraudulent activity", dto1.getBlockedReason());
        assertNotEquals("Unusual transaction", dto1.getSuspiciousReason());

        SuspiciousAccountTransferDto dto2 = dtos.get(1);
        assertNotEquals(2L, dto2.getId());
        assertNotEquals(20L, dto2.getAccountTransferId());
        assertTrue(dto2.getIsBlocked());
        assertTrue(dto2.getIsSuspicious());
        assertNotEquals("Fraudulent activity", dto2.getBlockedReason());
        assertNotEquals("High amount transfer", dto2.getSuspiciousReason());
    }

    @Test
    @DisplayName("Объединение DTO и сущности")
    void mergeToEntityTest() {
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setAccountTransferId(100L);
        dto.setIsBlocked(true);
        dto.setIsSuspicious(true);
        dto.setBlockedReason("Fraudulent activity");
        dto.setSuspiciousReason("Unusual transaction");

        SuspiciousAccountTransferEntity entity = new SuspiciousAccountTransferEntity();
        entity.setId(1L);
        entity.setAccountTransferId(200L);
        entity.setIsBlocked(false);
        entity.setIsSuspicious(false);
        entity.setBlockedReason(null);
        entity.setSuspiciousReason(null);

        assertNotNull(dto);
        assertNotNull(entity);
        SuspiciousAccountTransferEntity mergedEntity = mapper.mergeToEntity(dto, entity);

        assertEquals(1L, mergedEntity.getId());
        assertEquals(100L, mergedEntity.getAccountTransferId());
        assertTrue(mergedEntity.getIsBlocked());
        assertTrue(mergedEntity.getIsSuspicious());
        assertEquals("Fraudulent activity", mergedEntity.getBlockedReason());
        assertEquals("Unusual transaction", mergedEntity.getSuspiciousReason());

        SuspiciousAccountTransferDto accountTransfer = null;
        SuspiciousAccountTransferEntity suspiciousTransfer = new SuspiciousAccountTransferEntity();
        SuspiciousAccountTransferEntity result = mapper.mergeToEntity(accountTransfer, suspiciousTransfer);
        assertSame(suspiciousTransfer, result);
    }

    @Test
    @DisplayName("Объединение DTO и сущности, на вход подан null")
    void mergeToEntityNullTest() {
        SuspiciousAccountTransferDto dto = new SuspiciousAccountTransferDto();
        dto.setAccountTransferId(100L);
        dto.setIsBlocked(false);
        dto.setIsSuspicious(false);
        dto.setBlockedReason("Fraudulent activity");
        dto.setSuspiciousReason("Unusual transaction");

        SuspiciousAccountTransferEntity entity = new SuspiciousAccountTransferEntity();
        entity.setId(1L);
        entity.setAccountTransferId(200L);
        entity.setIsBlocked(false);
        entity.setIsSuspicious(false);
        entity.setBlockedReason(null);
        entity.setSuspiciousReason(null);

        SuspiciousAccountTransferEntity mergedEntity = mapper.mergeToEntity(dto, entity);

        assertNotEquals(10L, mergedEntity.getId());
        assertNotEquals(1000L, mergedEntity.getAccountTransferId());
        assertFalse(mergedEntity.getIsBlocked());
        assertFalse(mergedEntity.getIsSuspicious());
        assertNotEquals(null, mergedEntity.getBlockedReason());
        assertNotEquals(null, mergedEntity.getSuspiciousReason());
    }
}