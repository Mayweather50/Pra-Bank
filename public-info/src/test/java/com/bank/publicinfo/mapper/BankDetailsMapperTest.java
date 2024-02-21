package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankDetailsMapperTest {
    @InjectMocks
    BankDetailsMapperImpl mapper;
    @Mock
    BankDetailsDto dto;
    @Mock
    BankDetailsEntity entity;

    @Test
    @DisplayName("Маппинг в Entity")
    void toEntityTest() {
        when(dto.getInn()).thenReturn(2L);
        when(dto.getBik()).thenReturn(3L);
        when(dto.getKpp()).thenReturn(4L);
        when(dto.getCorAccount()).thenReturn(BigDecimal.ONE);
        when(dto.getCity()).thenReturn("Тест Улица");
        when(dto.getJointStockCompany()).thenReturn("Тест Акционерная Компания");
        when(dto.getName()).thenReturn("Тест Название");

        entity = mapper.toEntity(dto);

        assertNull(entity.getId());
        assertEquals(2L, entity.getInn());
        assertEquals(3L, entity.getBik());
        assertEquals(4L, entity.getKpp());
        assertEquals(BigDecimal.ONE, entity.getCorAccount());
        assertEquals("Тест Улица", entity.getCity());
        assertEquals("Тест Акционерная Компания", entity.getJointStockCompany());
        assertEquals("Тест Название", entity.getName());
    }

    @Test
    @DisplayName("Маппинг в Entity, на вход подан null")
    void toEntityNullTest() {
        entity = mapper.toEntity(null);
        assertNull(entity);
    }

    @Test
    @DisplayName("Маппинг в Dto")
    void toDtoTest() {
        when(entity.getId()).thenReturn(1L);
        when(entity.getInn()).thenReturn(2L);
        when(entity.getBik()).thenReturn(3L);
        when(entity.getKpp()).thenReturn(4L);
        when(entity.getCorAccount()).thenReturn(BigDecimal.ONE);
        when(entity.getCity()).thenReturn("Тест Улица");
        when(entity.getJointStockCompany()).thenReturn("Тест Акционерная Компания");
        when(entity.getName()).thenReturn("Тест Название");

        dto = mapper.toDto(entity);

        assertEquals(1L, dto.getId());
        assertEquals(2L, dto.getInn());
        assertEquals(3L, dto.getBik());
        assertEquals(4L, dto.getKpp());
        assertEquals(BigDecimal.ONE, dto.getCorAccount());
        assertEquals("Тест Улица", dto.getCity());
        assertEquals("Тест Акционерная Компания", dto.getJointStockCompany());
        assertEquals("Тест Название", dto.getName());
    }

    @Test
    @DisplayName("Маппинг в Dto, на вход подан null")
    void toDtoNullTest() {
        dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        dto = getDto();

        entity = getEntity();
        dto.setBik(null);
        dto.setInn(null);

        entity = mapper.mergeToEntity(dto, entity);

        assertEquals(1L, entity.getId());
        assertNull(entity.getBik());
        assertNull(entity.getInn());
        assertEquals(4L, entity.getKpp());
        assertEquals(BigDecimal.ONE, entity.getCorAccount());
        assertEquals("Тест Улица", entity.getCity());
        assertEquals("Тест Акционерная Компания", entity.getJointStockCompany());
        assertEquals("Тест Название", entity.getName());

    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        dto = new BankDetailsDto();
        entity = getEntity();

        entity = mapper.mergeToEntity(dto, entity);

        assertNotNull(entity.getId());
        assertNull(entity.getBik());
        assertNull(entity.getInn());
        assertNull(entity.getKpp());
        assertNull(entity.getCorAccount());
        assertNull(entity.getCity());
        assertNull(entity.getJointStockCompany());
        assertNull(entity.getName());
    }

    @Test
    @DisplayName("Маппинг в лист dto")
    void toDtoListTest() {
        List<BankDetailsDto> dtoList = mapper.toDtoList(List.of(getEntity(),getEntity2(), getEntity3()));

        assertEquals(dtoList, List.of(getDto(), getDto2(), getDto3()));
    }

    @Test
    @DisplayName("Маппинг в лист dto, на вход подан пустой лист")
    void toDtoListEmptyListTest() {
        List<BankDetailsDto> dtoList = mapper.toDtoList(Collections.emptyList());

        assertEquals(dtoList, Collections.emptyList());
    }

    BankDetailsDto getDto() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(1L);
        bankDetailsDto.setBik(2L);
        bankDetailsDto.setInn(3L);
        bankDetailsDto.setKpp(4L);
        bankDetailsDto.setCorAccount(BigDecimal.ONE);
        bankDetailsDto.setCity("Тест Улица");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания");
        bankDetailsDto.setName("Тест Название");
        return bankDetailsDto;
    }

    BankDetailsDto getDto2() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(11L);
        bankDetailsDto.setBik(22L);
        bankDetailsDto.setInn(33L);
        bankDetailsDto.setKpp(44L);
        bankDetailsDto.setCorAccount(BigDecimal.ZERO);
        bankDetailsDto.setCity("Тест Улица 2");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания 2");
        bankDetailsDto.setName("Тест Название 2");
        return bankDetailsDto;
    }

    BankDetailsDto getDto3() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(111L);
        bankDetailsDto.setBik(222L);
        bankDetailsDto.setInn(333L);
        bankDetailsDto.setKpp(444L);
        bankDetailsDto.setCorAccount(BigDecimal.TEN);
        bankDetailsDto.setCity("Тест Улица 3");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания 3");
        bankDetailsDto.setName("Тест Название 3");
        return bankDetailsDto;
    }

    BankDetailsEntity getEntity() {
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity();
        bankDetailsEntity.setId(1L);
        bankDetailsEntity.setBik(2L);
        bankDetailsEntity.setInn(3L);
        bankDetailsEntity.setKpp(4L);
        bankDetailsEntity.setCorAccount(BigDecimal.ONE);
        bankDetailsEntity.setCity("Тест Улица");
        bankDetailsEntity.setJointStockCompany("Тест Акционерная Компания");
        bankDetailsEntity.setName("Тест Название");
        return bankDetailsEntity;
    }

    BankDetailsEntity getEntity2() {
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity();
        bankDetailsEntity.setId(11L);
        bankDetailsEntity.setBik(22L);
        bankDetailsEntity.setInn(33L);
        bankDetailsEntity.setKpp(44L);
        bankDetailsEntity.setCorAccount(BigDecimal.ZERO);
        bankDetailsEntity.setCity("Тест Улица 2");
        bankDetailsEntity.setJointStockCompany("Тест Акционерная Компания 2");
        bankDetailsEntity.setName("Тест Название 2");
        return bankDetailsEntity;
    }

    BankDetailsEntity getEntity3() {
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity();
        bankDetailsEntity.setId(111L);
        bankDetailsEntity.setBik(222L);
        bankDetailsEntity.setInn(333L);
        bankDetailsEntity.setKpp(444L);
        bankDetailsEntity.setCorAccount(BigDecimal.TEN);
        bankDetailsEntity.setCity("Тест Улица 3");
        bankDetailsEntity.setJointStockCompany("Тест Акционерная Компания 3");
        bankDetailsEntity.setName("Тест Название 3");
        return bankDetailsEntity;
    }
}