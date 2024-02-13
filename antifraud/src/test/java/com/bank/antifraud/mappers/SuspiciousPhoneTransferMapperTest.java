package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
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
public class SuspiciousPhoneTransferMapperTest {
    @InjectMocks
    private SuspiciousPhoneTransferMapperImpl mapper;
    @Mock
    private SuspiciousPhoneTransferEntity entity;

    @Test
    @DisplayName("Маппинг в DTO")
    void toDtoByIdTest() {
        when(entity.getId()).thenReturn(1L);
        SuspiciousPhoneTransferDto dto = mapper.toDto(entity);
        assertEquals(entity.getId(), dto.getId());
    }

    @Test
    @DisplayName("Маппинг в DTO через id, негативный сценарий")
    void toDtoNullNegativeTest() {
        SuspiciousPhoneTransferDto dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    @DisplayName("Маппинг в entity")
    public void toEntityTest() {
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();
        dto.setPhoneTransferId(1L);
        dto.setIsBlocked(true);
        dto.setIsSuspicious(true);
        dto.setBlockedReason("Fraudulent activity");
        dto.setSuspiciousReason("Unusual transaction");

        SuspiciousPhoneTransferEntity entity = mapper.toEntity(dto);

        assertNotNull(entity);

        assertEquals(dto.getPhoneTransferId(), entity.getPhoneTransferId());
        assertEquals(dto.getIsBlocked(), entity.getIsBlocked());
        assertEquals(dto.getIsSuspicious(), entity.getIsSuspicious());
        assertEquals(dto.getBlockedReason(), entity.getBlockedReason());
        assertEquals(dto.getSuspiciousReason(), entity.getSuspiciousReason());
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    public void toEntityNullTest() {
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();
        dto.setPhoneTransferId(1L);
        dto.setIsBlocked(true);
        dto.setIsSuspicious(true);
        dto.setBlockedReason("Fraudulent activity");
        dto.setSuspiciousReason("Unusual transaction");

        SuspiciousPhoneTransferEntity entity = mapper.toEntity(null);

        assertNull(entity);

        if (entity != null) {
            assertEquals(dto.getPhoneTransferId(), entity.getPhoneTransferId());
            assertEquals(dto.getIsBlocked(), entity.getIsBlocked());
            assertEquals(dto.getIsSuspicious(), entity.getIsSuspicious());
            assertEquals(dto.getBlockedReason(), entity.getBlockedReason());
            assertEquals(dto.getSuspiciousReason(), entity.getSuspiciousReason());
        }
    }

    @Test
    @DisplayName("Маппинг в список DTO")
    void toListDtoTest() {
        List<SuspiciousPhoneTransferEntity> entities = new ArrayList<>();
        SuspiciousPhoneTransferEntity entity1 = new SuspiciousPhoneTransferEntity();
        entity1.setId(1L);
        entity1.setPhoneTransferId(100L);
        entity1.setIsBlocked(true);
        entity1.setIsSuspicious(true);
        entity1.setBlockedReason("Fraudulent activity");
        entity1.setSuspiciousReason("Unusual transaction");
        entities.add(entity1);

        SuspiciousPhoneTransferEntity entity2 = new SuspiciousPhoneTransferEntity();
        entity2.setId(2L);
        entity2.setPhoneTransferId(200L);
        entity2.setIsBlocked(false);
        entity2.setIsSuspicious(true);
        entity2.setBlockedReason(null);
        entity2.setSuspiciousReason("High amount transfer");
        entities.add(entity2);

        List<SuspiciousPhoneTransferDto> dtos = mapper.toListDto(entities);

        assertEquals(2, dtos.size());

        SuspiciousPhoneTransferDto dto1 = dtos.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals(100L, dto1.getPhoneTransferId());
        assertTrue(dto1.getIsBlocked());
        assertTrue(dto1.getIsSuspicious());
        assertEquals("Fraudulent activity", dto1.getBlockedReason());
        assertEquals("Unusual transaction", dto1.getSuspiciousReason());

        SuspiciousPhoneTransferDto dto2 = dtos.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals(200L, dto2.getPhoneTransferId());
        assertFalse(dto2.getIsBlocked());
        assertTrue(dto2.getIsSuspicious());
        assertNull(dto2.getBlockedReason());
        assertEquals("High amount transfer", dto2.getSuspiciousReason());

        List<SuspiciousPhoneTransferEntity> suspiciousTransfers = null;
        List<SuspiciousPhoneTransferDto> result = mapper.toListDto(suspiciousTransfers);
        assertNull(result);
    }

    @Test
    @DisplayName("Маппинг в список DTO, на вход подан null")
    void toListDtoNullTest() {
        List<SuspiciousPhoneTransferEntity> entities = new ArrayList<>();
        SuspiciousPhoneTransferEntity entity1 = new SuspiciousPhoneTransferEntity();
        entity1.setId(11L);
        entity1.setPhoneTransferId(1100L);
        entity1.setIsBlocked(true);
        entity1.setIsSuspicious(true);
        entity1.setBlockedReason(null);
        entity1.setSuspiciousReason(null);
        entities.add(entity1);

        SuspiciousPhoneTransferEntity entity2 = new SuspiciousPhoneTransferEntity();
        entity2.setId(20L);
        entity2.setPhoneTransferId(200L);
        entity2.setIsBlocked(true);
        entity2.setIsSuspicious(true);
        entity2.setBlockedReason("Nothing");
        entity2.setSuspiciousReason(null);
        entities.add(entity2);

        List<SuspiciousPhoneTransferDto> dtos = mapper.toListDto(entities);

        assertNotEquals(1, dtos.size());


        SuspiciousPhoneTransferDto dto1 = dtos.get(0);
        assertNotEquals(1L, dto1.getId());
        assertNotEquals(100L, dto1.getPhoneTransferId());
        assertTrue(dto1.getIsBlocked());
        assertTrue(dto1.getIsSuspicious());
        assertNotEquals("Fraudulent activity", dto1.getBlockedReason());
        assertNotEquals("Unusual transaction", dto1.getSuspiciousReason());

        SuspiciousPhoneTransferDto dto2 = dtos.get(1);
        assertNotEquals(2L, dto2.getId());
        assertNotEquals(20L, dto2.getPhoneTransferId());
        assertTrue(dto2.getIsBlocked());
        assertTrue(dto2.getIsSuspicious());
        assertNotEquals("Fraudulent activity", dto2.getBlockedReason());
        assertNotEquals("High amount transfer", dto2.getSuspiciousReason());
    }

    @Test
    @DisplayName("Слияние DTO и сущности")
    void mergeToEntityTest() {
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();
        dto.setPhoneTransferId(100L);
        dto.setIsBlocked(true);
        dto.setIsSuspicious(true);
        dto.setBlockedReason("Fraudulent activity");
        dto.setSuspiciousReason("Unusual transaction");

        SuspiciousPhoneTransferEntity entity = new SuspiciousPhoneTransferEntity();
        entity.setId(1L);
        entity.setPhoneTransferId(200L);
        entity.setIsBlocked(false);
        entity.setIsSuspicious(false);
        entity.setBlockedReason(null);
        entity.setSuspiciousReason(null);

        assertNotNull(dto);
        assertNotNull(entity);
        SuspiciousPhoneTransferEntity mergedEntity = mapper.mergeToEntity(dto, entity);

        assertEquals(1L, mergedEntity.getId());
        assertEquals(100L, mergedEntity.getPhoneTransferId());
        assertTrue(mergedEntity.getIsBlocked());
        assertTrue(mergedEntity.getIsSuspicious());
        assertEquals("Fraudulent activity", mergedEntity.getBlockedReason());
        assertEquals("Unusual transaction", mergedEntity.getSuspiciousReason());

        SuspiciousPhoneTransferDto phoneTransfer = null;
        SuspiciousPhoneTransferEntity suspiciousTransfer = new SuspiciousPhoneTransferEntity();
        SuspiciousPhoneTransferEntity result = mapper.mergeToEntity(phoneTransfer, suspiciousTransfer);
        assertSame(suspiciousTransfer, result);
    }

    @Test
    @DisplayName("Слияние DTO и сущности, на вход подан null")
    void mergeToEntityNullTest() {
        SuspiciousPhoneTransferDto dto = new SuspiciousPhoneTransferDto();
        dto.setPhoneTransferId(100L);
        dto.setIsBlocked(false);
        dto.setIsSuspicious(false);
        dto.setBlockedReason("Fraudulent activity");
        dto.setSuspiciousReason("Unusual transaction");

        SuspiciousPhoneTransferEntity entity = new SuspiciousPhoneTransferEntity();
        entity.setId(1L);
        entity.setPhoneTransferId(200L);
        entity.setIsBlocked(false);
        entity.setIsSuspicious(false);
        entity.setBlockedReason(null);
        entity.setSuspiciousReason(null);

        SuspiciousPhoneTransferEntity mergedEntity = mapper.mergeToEntity(dto, entity);

        assertNotEquals(10L, mergedEntity.getId());
        assertNotEquals(1000L, mergedEntity.getPhoneTransferId());
        assertFalse(mergedEntity.getIsBlocked());
        assertFalse(mergedEntity.getIsSuspicious());
        assertNotEquals(null, mergedEntity.getBlockedReason());
        assertNotEquals(null, mergedEntity.getSuspiciousReason());
    }
}