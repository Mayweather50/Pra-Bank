package com.bank.antifraud.service;

import com.bank.antifraud.service.impl.SuspiciousPhoneTransferServiceImpl;
import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import com.bank.antifraud.mappers.SuspiciousPhoneTransferMapper;
import com.bank.antifraud.repository.SuspiciousPhoneTransferRepository;
import com.bank.antifraud.service.common.ExceptionReturner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class SuspiciousPhoneTransferServiceImplTest {
    @Mock
    private SuspiciousPhoneTransferRepository repository;
    @Mock
    private SuspiciousPhoneTransferMapper mapper;
    @Mock
    private ExceptionReturner returner;
    @InjectMocks
    private SuspiciousPhoneTransferServiceImpl service;

    @Test
    @DisplayName("Сохранение данных аккаунта, позитивный сценарий")
    void savePositiveTest() {
        SuspiciousPhoneTransferDto inputDto = new SuspiciousPhoneTransferDto();
        SuspiciousPhoneTransferEntity savedEntity = new SuspiciousPhoneTransferEntity();
        SuspiciousPhoneTransferDto expectedDto = new SuspiciousPhoneTransferDto();

        when(mapper.toEntity(inputDto)).thenReturn(savedEntity);
        when(repository.save(savedEntity)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(expectedDto);

        SuspiciousPhoneTransferDto actualDto = service.save(inputDto);

        assertEquals(expectedDto, actualDto);
        verify(mapper).toEntity(inputDto);
        verify(repository).save(savedEntity);
        verify(mapper).toDto(savedEntity);
    }

    @Test
    @DisplayName("Сохранение данных аккаунта, негативный сценарий")
    void saveNegativeTest() {
        SuspiciousPhoneTransferDto inputDto = new SuspiciousPhoneTransferDto();

        when(mapper.toEntity(inputDto)).thenThrow(new RuntimeException("Error mapping to entity"));

        assertThrows(RuntimeException.class, () -> service.save(inputDto));

        verify(mapper).toEntity(inputDto);
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        SuspiciousPhoneTransferEntity entity = new SuspiciousPhoneTransferEntity();
        SuspiciousPhoneTransferDto expectedDto = new SuspiciousPhoneTransferDto();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(expectedDto);

        SuspiciousPhoneTransferDto actualDto = service.findById(id);

        assertEquals(expectedDto, actualDto);
        verify(repository).findById(id);
        verify(mapper).toDto(entity);
    }

    @Test
    @DisplayName("Поиск по id, негативный сценарий")
    void findByIdNegativeTest() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException(any(String.class)))
                .thenReturn(new EntityNotFoundException("Entity not found"));

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Обновление данных аккаунта, позитивный сценарий")
    public void updatePositiveTest() {
        Long id = 1L;
        SuspiciousPhoneTransferDto inputDto = new SuspiciousPhoneTransferDto();
        SuspiciousPhoneTransferEntity existingEntity = new SuspiciousPhoneTransferEntity();
        SuspiciousPhoneTransferEntity updatedEntity = new SuspiciousPhoneTransferEntity();
        SuspiciousPhoneTransferDto expectedDto = new SuspiciousPhoneTransferDto();

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(mapper.mergeToEntity(inputDto, existingEntity)).thenReturn(updatedEntity);
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(expectedDto);

        SuspiciousPhoneTransferDto actualDto = service.update(id, inputDto);

        assertEquals(expectedDto, actualDto);
        verify(repository).findById(id);
        verify(mapper).mergeToEntity(inputDto, existingEntity);
        verify(repository).save(updatedEntity);
        verify(mapper).toDto(updatedEntity);
    }

    @Test
    @DisplayName("Обновление данных аккаунта, негативный сценарий")
    public void updateNegativeTest() {
        Long id = 1L;
        SuspiciousPhoneTransferDto inputDto = new SuspiciousPhoneTransferDto();

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException(anyString()))
                .thenReturn(new EntityNotFoundException("Entity not found"));

        assertThrows(EntityNotFoundException.class, () -> service.update(id, inputDto));
        verify(repository).findById(id);
        verify(returner).getEntityNotFoundException(anyString());
    }

    @Test
    @DisplayName("Поиск всех аккаунтов по id, позитивный сценарий")
    public void findAllByIdPositiveTest() {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<SuspiciousPhoneTransferEntity> entities = new ArrayList<>();
        entities.add(new SuspiciousPhoneTransferEntity(1L, 1L, true
                , true, "Blocked", "Suspicious"));
        entities.add(new SuspiciousPhoneTransferEntity(2L, 2L, false
                , true, null, "Suspicious"));
        entities.add(new SuspiciousPhoneTransferEntity(3L, 3L, true
                , false, "Blocked", null));

        when(repository.findById(1L)).thenReturn(Optional.of(entities.get(0)));
        when(repository.findById(2L)).thenReturn(Optional.of(entities.get(1)));
        when(repository.findById(3L)).thenReturn(Optional.of(entities.get(2)));

        List<SuspiciousPhoneTransferDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new SuspiciousPhoneTransferDto(1L, 1L, true
                ,true, "Blocked", "Suspicious"));
        expectedDtos.add(new SuspiciousPhoneTransferDto(2L, 2L, false
                , true, null, "Suspicious"));
        expectedDtos.add(new SuspiciousPhoneTransferDto(3L, 3L, true
                , false, "Blocked", null));
        when(mapper.toListDto(entities)).thenReturn(expectedDtos);

        List<SuspiciousPhoneTransferDto> actualDtos = service.findAllById(ids);

        assertEquals(expectedDtos, actualDtos);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).findById(2L);
        verify(repository, times(1)).findById(3L);
        verifyNoMoreInteractions(repository);
        verify(mapper, times(1)).toListDto(entities);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    @DisplayName("Поиск всех аккаунтов по id,, негативный сценарий")
    public void findAllByIdNegativeTest() {
        List<Long> ids = List.of(1L, 2L);
        when(returner.getEntityNotFoundException(anyString()))
                .thenReturn(new EntityNotFoundException("Entity not found"));

        assertThrows(EntityNotFoundException.class, () -> service.findAllById(ids));
        verify(returner).getEntityNotFoundException(anyString());
    }
}