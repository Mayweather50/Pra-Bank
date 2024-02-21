package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.entity.AtmEntity;
import com.bank.publicinfo.mapper.AtmMapper;
import com.bank.publicinfo.repository.AtmRepository;
import com.bank.publicinfo.util.EntityNotFoundSupplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class AtmServiceTest {
    @Mock
    private AtmRepository repository;
    @Mock
    private AtmMapper mapper;
    @Mock
    private EntityNotFoundSupplier supplierNotFound;
    @InjectMocks
    private AtmServiceImpl service;

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;

        AtmEntity entity = new AtmEntity();
        AtmDto dto = new AtmDto();
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        AtmDto actualAtmDto = service.findById(id);
        assertEquals(dto, actualAtmDto);

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(supplierNotFound.getException(any(), any()))
                .thenReturn(new EntityNotFoundException("entity not found"));
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Создание банкомата, позитивный сценарий")
    void createPositiveTest() {
        AtmEntity entity = new AtmEntity();
        AtmDto dto = new AtmDto();

        entity.setId(1L);
        dto.setId(1L);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);


        AtmDto actualDto = service.create(dto);

        assertEquals(actualDto, dto);

        verify(repository, times(1)).save(entity);
    }

    @Test
    @DisplayName("Создание банкомата, ошибка при маппинге, негативный сценарий")
    void createMappingErrorNegativeTest() {
        AtmDto dto = new AtmDto();

        when(mapper.toEntity(dto)).thenThrow(new RuntimeException
                ("Error happened while trying map atmDto to atmEntity")
        );

        assertThrows(RuntimeException.class, () -> service.create(dto));
    }

    @Test
    @DisplayName("Обновление банкомата, позитивный сценарий")
    void updatePositiveTest() {
        AtmEntity entity = new AtmEntity();
        AtmEntity updatedEntity = new AtmEntity();
        AtmDto dto = new AtmDto();
        AtmDto updatedDto = new AtmDto();

        entity.setId(1L);
        updatedEntity.setId(2L);
        dto.setId(1L);
        updatedDto.setId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(entity));
        when(mapper.mergeToEntity(dto, entity)).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(updatedDto);

        dto = service.update(2L, dto);

        assertEquals(dto, updatedDto);

        verify(repository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Обновление банкомата по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(supplierNotFound.getException(any(), any()))
                .thenReturn(new EntityNotFoundException("entity not found"));

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, new AtmDto()));
    }

    @Test
    @DisplayName("Чтение по листу ids, позитивный сценарий")
    void findAllByIdPositiveTest() {
        AtmDto dto = new AtmDto();
        AtmDto dto1 = new AtmDto();
        dto.setId(1L);
        dto1.setId(2L);

        AtmEntity entity = new AtmEntity();
        AtmEntity entity1 = new AtmEntity();
        entity.setId(1L);
        entity1.setId(2L);

        List<AtmDto> dtoList = List.of(dto1, dto);
        List<AtmEntity> entityList = List.of(entity1, entity);
        List<Long> ids = List.of(1L, 2L);

        when(repository.findAllById(ids)).thenReturn(entityList);
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List<AtmDto> actualDtoList = service.findAllById(ids);

        assertEquals(actualDtoList, dtoList);

        verify(repository, times(1)).findAllById(ids);
    }

    @Test
    @DisplayName("Чтение по листу несуществующих ids, негативный сценарий")
    void findAllByNonExistIdNegativeTest() {
        doThrow(EntityNotFoundException.class).when(supplierNotFound).checkForSizeAndLogging(any(),any(),any());

        assertThrows(EntityNotFoundException.class, () -> service.findAllById(Collections.emptyList()));
    }
}