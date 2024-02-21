package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchMapperTest {

    @InjectMocks
    BranchMapperImpl mapper;
    @Mock
    BranchDto dto;
    @Mock
    BranchEntity entity;

    @Test
    @DisplayName("Маппинг в Entity")
    void toEntityTest() {
        when(dto.getAddress()).thenReturn("тест улица");
        when(dto.getPhoneNumber()).thenReturn(123456L);
        when(dto.getCity()).thenReturn("тест город");
        when(dto.getStartOfWork()).thenReturn(LocalTime.of(2, 3));
        when(dto.getEndOfWork()).thenReturn(LocalTime.of(4, 5));

        entity = mapper.toEntity(dto);

        assertNull(entity.getId());
        assertEquals("тест улица", entity.getAddress());
        assertEquals(123456L, entity.getPhoneNumber());
        assertEquals("тест город", entity.getCity());
        assertEquals(LocalTime.of(2, 3), entity.getStartOfWork());
        assertEquals(LocalTime.of(4, 5), entity.getEndOfWork());
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
        when(entity.getAddress()).thenReturn("тест улица");
        when(entity.getPhoneNumber()).thenReturn(123456L);
        when(entity.getCity()).thenReturn("тест город");
        when(entity.getStartOfWork()).thenReturn(LocalTime.of(2, 3));
        when(entity.getEndOfWork()).thenReturn(LocalTime.of(4, 5));

        dto = mapper.toDto(entity);

        assertEquals(1L, dto.getId());
        assertEquals("тест улица", dto.getAddress());
        assertEquals(123456L, dto.getPhoneNumber());
        assertEquals("тест город", dto.getCity());
        assertEquals(LocalTime.of(2, 3), dto.getStartOfWork());
        assertEquals(LocalTime.of(4, 5), dto.getEndOfWork());
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
        dto.setAddress(null);
        dto.setPhoneNumber(null);

        entity = mapper.mergeToEntity(dto, entity);

        assertEquals(1L, entity.getId());
        assertNull(entity.getAddress());
        assertNull(entity.getPhoneNumber());
        assertEquals("тест город", entity.getCity());
        assertEquals(LocalTime.of(2, 3), entity.getStartOfWork());
        assertEquals(LocalTime.of(4, 5), entity.getEndOfWork());

    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        dto = new BranchDto();
        entity = getEntity();

        entity = mapper.mergeToEntity(dto, entity);

        assertNotNull(entity.getId());
        assertNull(entity.getAddress());
        assertNull(entity.getPhoneNumber());
        assertNull(entity.getCity());
        assertNull(entity.getStartOfWork());
        assertNull(entity.getEndOfWork());
    }

    @Test
    @DisplayName("Маппинг в лист dto")
    void toDtoListTest() {
        List<BranchDto> dtoList = mapper.toDtoList(List.of(getEntity(),getEntity2(), getEntity3()));

        assertEquals(dtoList, List.of(getDto(), getDto2(), getDto3()));
    }

    @Test
    @DisplayName("Маппинг в лист dto, на вход подан пустой лист")
    void toDtoListEmptyListTest() {
        List<BranchDto> dtoList = mapper.toDtoList(Collections.emptyList());

        assertEquals(dtoList, Collections.emptyList());
    }

    BranchDto getDto() {
        BranchDto branchDto = new BranchDto();
        branchDto.setId(1L);
        branchDto.setAddress("тест улица");
        branchDto.setPhoneNumber(123456L);
        branchDto.setCity("тест город");
        branchDto.setStartOfWork(LocalTime.of(2, 3));
        branchDto.setEndOfWork(LocalTime.of(4, 5));
        return branchDto;
    }

    BranchDto getDto2() {
        BranchDto branchDto = new BranchDto();
        branchDto.setId(2L);
        branchDto.setAddress("тест улица 2");
        branchDto.setPhoneNumber(123456L);
        branchDto.setCity("тест город 2");
        branchDto.setStartOfWork(LocalTime.of(2, 3));
        branchDto.setEndOfWork(LocalTime.of(4, 5));
        return branchDto;
    }

    BranchDto getDto3() {
        BranchDto branchDto = new BranchDto();
        branchDto.setId(3L);
        branchDto.setAddress("тест улица 3");
        branchDto.setPhoneNumber(123456L);
        branchDto.setCity("тест город 3");
        branchDto.setStartOfWork(LocalTime.of(2, 3));
        branchDto.setEndOfWork(LocalTime.of(4, 5));
        return branchDto;
    }

    BranchEntity getEntity() {
        BranchEntity branchEntity = new BranchEntity();
        branchEntity.setId(1L);
        branchEntity.setAddress("тест улица");
        branchEntity.setPhoneNumber(123456L);
        branchEntity.setCity("тест город");
        branchEntity.setStartOfWork(LocalTime.of(2, 3));
        branchEntity.setEndOfWork(LocalTime.of(4, 5));
        return branchEntity;
    }

    BranchEntity getEntity2() {
        BranchEntity branchEntity = new BranchEntity();
        branchEntity.setId(2L);
        branchEntity.setAddress("тест улица 2");
        branchEntity.setPhoneNumber(123456L);
        branchEntity.setCity("тест город 2");
        branchEntity.setStartOfWork(LocalTime.of(2, 3));
        branchEntity.setEndOfWork(LocalTime.of(4, 5));
        return branchEntity;
    }

    BranchEntity getEntity3() {
        BranchEntity branchEntity = new BranchEntity();
        branchEntity.setId(3L);
        branchEntity.setAddress("тест улица 3");
        branchEntity.setPhoneNumber(123456L);
        branchEntity.setCity("тест город 3");
        branchEntity.setStartOfWork(LocalTime.of(2, 3));
        branchEntity.setEndOfWork(LocalTime.of(4, 5));
        return branchEntity;
    }
}