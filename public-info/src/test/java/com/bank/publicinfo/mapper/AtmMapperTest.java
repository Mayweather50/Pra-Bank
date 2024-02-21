package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.AtmEntity;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AtmMapperTest {
    @Mock
    AtmEntity entity;
    @Mock
    AtmDto dto;
    @InjectMocks
    AtmMapperImpl mapper;

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        when(dto.getAddress()).thenReturn("адрес");
        when(dto.getStartOfWork()).thenReturn(LocalTime.of(2, 3));
        when(dto.getEndOfWork()).thenReturn(LocalTime.of(4, 5));
        when(dto.getAllHours()).thenReturn(true);
        when(dto.getBranch()).thenReturn(getBranchDto());

        entity = mapper.toEntity(dto);

        assertNull(entity.getId());
        assertEquals("адрес", entity.getAddress());
        assertEquals(LocalTime.of(2, 3), entity.getStartOfWork());
        assertEquals(LocalTime.of(4, 5), entity.getEndOfWork());
        assertEquals(true, entity.getAllHours());
        assertEquals(1L, entity.getBranch().getId());
        assertEquals("тест улица", entity.getBranch().getAddress());
        assertEquals(123456L, entity.getBranch().getPhoneNumber());
        assertEquals("тест город", entity.getBranch().getCity());
        assertEquals(LocalTime.of(2, 3), entity.getBranch().getStartOfWork());
        assertEquals(LocalTime.of(4, 5), entity.getBranch().getEndOfWork());
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    void toEntityNullTest() {
        entity = mapper.toEntity(null);
        assertNull(entity);
    }

    @Test
    @DisplayName("Маппинг в Dto")
    void toDtoTest() {
        when(entity.getId()).thenReturn(1L);
        when(entity.getAddress()).thenReturn("адрес");
        when(entity.getStartOfWork()).thenReturn(LocalTime.of(2, 3));
        when(entity.getEndOfWork()).thenReturn(LocalTime.of(4, 5));
        when(entity.getAllHours()).thenReturn(true);
        when(entity.getBranch()).thenReturn(getBranchEntity());

        dto = mapper.toDto(entity);

        assertEquals(1L, entity.getId());
        assertEquals("адрес", entity.getAddress());
        assertEquals(LocalTime.of(2, 3), entity.getStartOfWork());
        assertEquals(LocalTime.of(4, 5), entity.getEndOfWork());
        assertEquals(true, entity.getAllHours());
        assertEquals(1L, entity.getBranch().getId());
        assertEquals("тест улица", entity.getBranch().getAddress());
        assertEquals(123456L, entity.getBranch().getPhoneNumber());
        assertEquals("тест город", entity.getBranch().getCity());
        assertEquals(LocalTime.of(2, 3), entity.getBranch().getStartOfWork());
        assertEquals(LocalTime.of(4, 5), entity.getBranch().getEndOfWork());
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
        dto = getAtmDto();

        entity = getAtmEntity();
        dto.setStartOfWork(null);
        entity.setAllHours(null);

        entity = mapper.mergeToEntity(dto, entity);

        assertEquals(1L, entity.getId());
        assertEquals("адрес", entity.getAddress());
        assertNull(entity.getStartOfWork());
        assertEquals(LocalTime.of(4, 5), entity.getEndOfWork());
        assertEquals(true, entity.getAllHours());
        assertEquals(1L, entity.getBranch().getId());
        assertEquals("тест улица", entity.getBranch().getAddress());
        assertEquals(123456L, entity.getBranch().getPhoneNumber());
        assertEquals("тест город", entity.getBranch().getCity());
        assertEquals(LocalTime.of(2, 3), entity.getBranch().getStartOfWork());
        assertEquals(LocalTime.of(4, 5), entity.getBranch().getEndOfWork());
    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        dto = new AtmDto();
        entity = getAtmEntity();

        entity = mapper.mergeToEntity(dto, entity);

        assertNull(entity.getStartOfWork());
        assertNull(entity.getEndOfWork());
        assertNull(entity.getAllHours());
        assertNull(entity.getBranch());
    }

    @Test
    @DisplayName("Маппинг в лист dto")
    void toDtoListTest() {
        List<AtmDto> atmDtoList = mapper.toDtoList(List.of(getAtmEntity(),getAtmEntity2(), getAtmEntity3()));

        assertEquals(atmDtoList, List.of(getAtmDto(), getAtmDto2(), getAtmDto3()));
    }

    @Test
    @DisplayName("Маппинг в лист dto, на вход подан пустой лист")
    void toDtoListEmptyListTest() {
        List<AtmDto> atmDtoList = mapper.toDtoList(Collections.emptyList());

        assertEquals(atmDtoList, Collections.emptyList());
    }

    AtmDto getAtmDto() {
        AtmDto atmDto = new AtmDto();
        atmDto.setId(1L);
        atmDto.setAddress("адрес");
        atmDto.setAllHours(true);
        atmDto.setBranch(getBranchDto());
        atmDto.setStartOfWork(LocalTime.of(2, 3));
        atmDto.setEndOfWork(LocalTime.of(4, 5));
        return atmDto;
    }

    private AtmDto getAtmDto2() {
        AtmDto atmDto = new AtmDto();
        atmDto.setId(2L);
        atmDto.setAddress("адрес 2");
        atmDto.setAllHours(true);
        atmDto.setBranch(getBranchDto());
        atmDto.setStartOfWork(LocalTime.of(2, 3));
        atmDto.setEndOfWork(LocalTime.of(4, 5));
        return atmDto;
    }

    private AtmDto getAtmDto3() {
        AtmDto atmDto = new AtmDto();
        atmDto.setId(3L);
        atmDto.setAddress("адрес 3");
        atmDto.setAllHours(true);
        atmDto.setBranch(getBranchDto());
        atmDto.setStartOfWork(LocalTime.of(2, 3));
        atmDto.setEndOfWork(LocalTime.of(4, 5));
        return atmDto;
    }

    AtmEntity getAtmEntity() {
        AtmEntity atmEntity = new AtmEntity();
        atmEntity.setId(1L);
        atmEntity.setAddress("адрес");
        atmEntity.setAllHours(true);
        atmEntity.setBranch(getBranchEntity());
        atmEntity.setStartOfWork(LocalTime.of(2, 3));
        atmEntity.setEndOfWork(LocalTime.of(4, 5));
        return atmEntity;
    }

    AtmEntity getAtmEntity2() {
        AtmEntity atmEntity = new AtmEntity();
        atmEntity.setId(2L);
        atmEntity.setAddress("адрес 2");
        atmEntity.setAllHours(true);
        atmEntity.setBranch(getBranchEntity());
        atmEntity.setStartOfWork(LocalTime.of(2, 3));
        atmEntity.setEndOfWork(LocalTime.of(4, 5));
        return atmEntity;
    }

    AtmEntity getAtmEntity3() {
        AtmEntity atmEntity = new AtmEntity();
        atmEntity.setId(3L);
        atmEntity.setAddress("адрес 3");
        atmEntity.setAllHours(true);
        atmEntity.setBranch(getBranchEntity());
        atmEntity.setStartOfWork(LocalTime.of(2, 3));
        atmEntity.setEndOfWork(LocalTime.of(4, 5));
        return atmEntity;
    }

    BranchDto getBranchDto() {
        BranchDto branchDto = new BranchDto();
        branchDto.setId(1L);
        branchDto.setAddress("тест улица");
        branchDto.setPhoneNumber(123456L);
        branchDto.setCity("тест город");
        branchDto.setStartOfWork(LocalTime.of(2, 3));
        branchDto.setEndOfWork(LocalTime.of(4, 5));
        return branchDto;
    }

    BranchEntity getBranchEntity() {
        BranchEntity branchEntity = new BranchEntity();
        branchEntity.setId(1L);
        branchEntity.setAddress("тест улица");
        branchEntity.setPhoneNumber(123456L);
        branchEntity.setCity("тест город");
        branchEntity.setStartOfWork(LocalTime.of(2, 3));
        branchEntity.setEndOfWork(LocalTime.of(4, 5));
        return branchEntity;
    }
}