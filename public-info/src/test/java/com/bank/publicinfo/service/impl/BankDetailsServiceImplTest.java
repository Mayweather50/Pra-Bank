package com.bank.publicinfo.service.impl;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.mapper.BankDetailsMapper;
import com.bank.publicinfo.repository.BankDetailsRepository;
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
class BankDetailsServiceImplTest {
    @Mock
    private BankDetailsRepository repository;
    @Mock
    private BankDetailsMapper mapper;
    @Mock
    private EntityNotFoundSupplier supplierNotFound;
    @InjectMocks
    private BankDetailsServiceImpl service;

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;

        BankDetailsEntity entity = new BankDetailsEntity();
        BankDetailsDto dto = new BankDetailsDto();
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        BankDetailsDto actualAtmDto = service.findById(id);
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
    @DisplayName("Создание информации о банке, позитивный сценарий")
    void createPositiveTest() {
        BankDetailsEntity entity = new BankDetailsEntity();
        BankDetailsDto dto = new BankDetailsDto();

        entity.setId(1L);
        dto.setId(1L);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);


        BankDetailsDto actualDto = service.create(dto);

        assertEquals(actualDto, dto);

        verify(repository, times(1)).save(entity);
    }

    @Test
    @DisplayName("Создание информацию о банке, ошибка при маппинге, негативный сценарий")
    void createMappingErrorNegativeTest() {
        BankDetailsDto dto = new BankDetailsDto();

        when(mapper.toEntity(dto)).thenThrow(new RuntimeException(
                "Error happened while trying map atmDto to atmEntity")
        );

        assertThrows(RuntimeException.class, () -> service.create(dto));
    }

    @Test
    @DisplayName("Обновление информацию о банке, позитивный сценарий")
    void updatePositiveTest() {
        BankDetailsEntity entity = new BankDetailsEntity();
        BankDetailsEntity updatedEntity = new BankDetailsEntity();
        BankDetailsDto dto = new BankDetailsDto();
        BankDetailsDto updatedDto = new BankDetailsDto();

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
    @DisplayName("Обновление информацию о банке по несуществующему, негативный сценарий")
    void updateNonExistIdNegativeTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(supplierNotFound.getException(any(), any()))
                .thenReturn(new EntityNotFoundException("entity not found"));

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, new BankDetailsDto()));
    }

    @Test
    @DisplayName("Чтение по листу ids, позитивный сценарий")
    void findAllByIdPositiveTest() {
        BankDetailsDto dto = new BankDetailsDto();
        BankDetailsDto dto1 = new BankDetailsDto();
        dto.setId(1L);
        dto1.setId(2L);

        BankDetailsEntity entity = new BankDetailsEntity();
        BankDetailsEntity entity1 = new BankDetailsEntity();
        entity.setId(1L);
        entity1.setId(2L);

        List<BankDetailsDto> dtoList = List.of(dto1, dto);
        List<BankDetailsEntity> entityList = List.of(entity1, entity);
        List<Long> ids = List.of(1L, 2L);

        when(repository.findAllById(ids)).thenReturn(entityList);
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List<BankDetailsDto> actualDtoList = service.findAllById(ids);

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