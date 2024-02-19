package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HistoryMapperImplTest {

    @InjectMocks
    private HistoryMapperImpl historyMapperImpl;

    @Test
    @DisplayName("Маппинг в Entity")
    void toEntityTest() {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setTransferAuditId(3L);
        historyDto.setProfileAuditId(2L);
        historyDto.setAccountAuditId(1L);
        historyDto.setAntiFraudAuditId(3L);
        historyDto.setPublicBankInfoAuditId(2L);
        historyDto.setAuthorizationAuditId(3L);

        HistoryEntity entity = historyMapperImpl.toEntity(historyDto);

        assertNotNull(entity);
        assertEquals(historyDto.getTransferAuditId(), entity.getTransferAuditId());
        assertEquals(historyDto.getProfileAuditId(), entity.getProfileAuditId());
        assertEquals(historyDto.getAccountAuditId(), entity.getAccountAuditId());
        assertEquals(historyDto.getAntiFraudAuditId(), entity.getAntiFraudAuditId());
        assertEquals(historyDto.getPublicBankInfoAuditId(), entity.getPublicBankInfoAuditId());
        assertEquals(historyDto.getAuthorizationAuditId(), entity.getAuthorizationAuditId());
    }

    @Test
    @DisplayName("Маппинг в Entity, на вход подан null")
    void toEntityNullTest() {
        HistoryEntity entity = historyMapperImpl.toEntity(null);

        assertNull(entity);
    }

    @Test
    @DisplayName("Маппинг в Dto")
    void toDtoTest() {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(1L);
        historyEntity.setTransferAuditId(3L);
        historyEntity.setProfileAuditId(2L);
        historyEntity.setAccountAuditId(1L);
        historyEntity.setAntiFraudAuditId(3L);
        historyEntity.setPublicBankInfoAuditId(2L);
        historyEntity.setAuthorizationAuditId(3L);

        HistoryDto dto = historyMapperImpl.toDto(historyEntity);

        assertNotNull(dto);
        assertEquals(historyEntity.getId(), dto.getId());
        assertEquals(historyEntity.getTransferAuditId(), dto.getTransferAuditId());
        assertEquals(historyEntity.getProfileAuditId(), dto.getProfileAuditId());
        assertEquals(historyEntity.getAccountAuditId(), dto.getAccountAuditId());
        assertEquals(historyEntity.getAntiFraudAuditId(), dto.getAntiFraudAuditId());
        assertEquals(historyEntity.getPublicBankInfoAuditId(), dto.getPublicBankInfoAuditId());
        assertEquals(historyEntity.getAuthorizationAuditId(), dto.getAuthorizationAuditId());
    }

    @Test
    @DisplayName("Маппинг в Dto, на вход подан null")
    void toDtoNullTest() {
        HistoryDto dto = historyMapperImpl.toDto(null);

        assertNull(dto);
    }

    @Test
    @DisplayName("Слияние в Entity")
    void mergeToEntityTest() {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setTransferAuditId(1L);
        historyDto.setProfileAuditId(2L);
        historyDto.setAccountAuditId(3L);
        historyDto.setAntiFraudAuditId(4L);
        historyDto.setPublicBankInfoAuditId(5L);
        historyDto.setAuthorizationAuditId(6L);

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(1L);
        historyEntity.setTransferAuditId(31L);
        historyEntity.setProfileAuditId(22L);
        historyEntity.setAccountAuditId(14L);
        historyEntity.setAntiFraudAuditId(35L);
        historyEntity.setPublicBankInfoAuditId(26L);
        historyEntity.setAuthorizationAuditId(33L);

        HistoryEntity entity = historyMapperImpl.mergeToEntity(historyDto, historyEntity);

        assertEquals(1L, entity.getId());
        assertEquals(1L, entity.getTransferAuditId());
        assertEquals(2L, entity.getProfileAuditId());
        assertEquals(3L, entity.getAccountAuditId());
        assertEquals(4L, entity.getAntiFraudAuditId());
        assertEquals(5L, entity.getPublicBankInfoAuditId());
        assertEquals(6L, entity.getAuthorizationAuditId());
    }

    @Test
    @DisplayName("Слияние в Entity, на вход подан null")
    void mergeToEntityNullTest() {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(1L);
        historyEntity.setTransferAuditId(31L);
        historyEntity.setProfileAuditId(22L);
        historyEntity.setAccountAuditId(14L);
        historyEntity.setAntiFraudAuditId(35L);
        historyEntity.setPublicBankInfoAuditId(26L);
        historyEntity.setAuthorizationAuditId(33L);

        HistoryEntity mergedEntity = historyMapperImpl.mergeToEntity(null, historyEntity);

        assertEquals(historyEntity, mergedEntity);
    }

    @Test
    @DisplayName("Маппинг в список Dto")
    void toListDtoTest() {
        List<HistoryEntity> historyEntities = new ArrayList<>();
        HistoryEntity historyEntity1 = new HistoryEntity();
        historyEntity1.setId(1L);
        historyEntity1.setTransferAuditId(3L);
        historyEntity1.setProfileAuditId(2L);
        historyEntity1.setAccountAuditId(1L);
        historyEntity1.setAntiFraudAuditId(3L);
        historyEntity1.setPublicBankInfoAuditId(2L);
        historyEntity1.setAuthorizationAuditId(3L);
        historyEntities.add(historyEntity1);

        HistoryEntity historyEntity2 = new HistoryEntity();
        historyEntity2.setId(1L);
        historyEntity2.setTransferAuditId(3L);
        historyEntity2.setProfileAuditId(2L);
        historyEntity2.setAccountAuditId(1L);
        historyEntity2.setAntiFraudAuditId(3L);
        historyEntity2.setPublicBankInfoAuditId(2L);
        historyEntity2.setAuthorizationAuditId(3L);
        historyEntities.add(historyEntity2);

        List<HistoryDto> historyDtos = historyMapperImpl.toListDto(historyEntities);

        HistoryDto historyDto1 = historyDtos.get(0);
        assertNotNull(historyDto1);
        assertEquals(historyEntity1.getId(), historyDto1.getId());
        assertEquals(historyEntity1.getTransferAuditId(), historyDto1.getTransferAuditId());
        assertEquals(historyEntity1.getProfileAuditId(), historyDto1.getProfileAuditId());
        assertEquals(historyEntity1.getAccountAuditId(), historyDto1.getAccountAuditId());
        assertEquals(historyEntity1.getAntiFraudAuditId(), historyDto1.getAntiFraudAuditId());
        assertEquals(historyEntity1.getPublicBankInfoAuditId(), historyDto1.getPublicBankInfoAuditId());
        assertEquals(historyEntity1.getAuthorizationAuditId(), historyDto1.getAuthorizationAuditId());

        HistoryDto historyDto2 = historyDtos.get(1);
        assertNotNull(historyDto2);
        assertEquals(historyEntity1.getId(), historyDto2.getId());
        assertEquals(historyEntity1.getTransferAuditId(), historyDto2.getTransferAuditId());
        assertEquals(historyEntity1.getProfileAuditId(), historyDto2.getProfileAuditId());
        assertEquals(historyEntity1.getAccountAuditId(), historyDto2.getAccountAuditId());
        assertEquals(historyEntity1.getAntiFraudAuditId(), historyDto2.getAntiFraudAuditId());
        assertEquals(historyEntity1.getPublicBankInfoAuditId(), historyDto2.getPublicBankInfoAuditId());
        assertEquals(historyEntity1.getAuthorizationAuditId(), historyDto2.getAuthorizationAuditId());
    }

    @Test
    @DisplayName("Маппинг в список Dto, на вход подан null")
    void toDtoListNullTest() {
        List<HistoryDto> historyDtos = historyMapperImpl.toListDto(null);

        assertNull(historyDtos);
    }
}
