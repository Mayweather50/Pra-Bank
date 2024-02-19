package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HistoryServiceImplTest {

    @Mock
    private HistoryMapper mapper;

    @Mock
    private HistoryRepository repository;

    @InjectMocks
    private HistoryServiceImpl service;

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() {
        Long id = 1L;
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(id);
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(historyEntity));
        when(mapper.toDto(historyEntity)).thenReturn(historyDto);

        HistoryDto actualDto = service.readById(id);

        assertNotNull(actualDto);
        assertEquals(id, actualDto.getId());
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(historyEntity);
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByIdNegativeTest() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.readById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Чтение всех историй по ids, позитивный сценарий")
    void readAllByIdPositiveTest() {
        List<Long> ids = List.of(1L, 2L);
        HistoryEntity historyEntity1 = new HistoryEntity();
        HistoryEntity historyEntity2 = new HistoryEntity();
        historyEntity1.setId(ids.get(0));
        historyEntity2.setId(ids.get(1));
        List<HistoryEntity> historyEntities = List.of(historyEntity1, historyEntity2);

        HistoryDto historyDto1 = new HistoryDto();
        HistoryDto historyDto2 = new HistoryDto();
        historyDto2.setId(ids.get(0));
        historyDto1.setId(ids.get(1));
        List<HistoryDto> historyDtos = List.of(historyDto1, historyDto2);

        when(repository.findAllById(ids)).thenReturn(historyEntities);
        when(mapper.toListDto(historyEntities)).thenReturn(historyDtos);

        List<HistoryDto> actualDtos = service.readAllById(ids);

        assertEquals(historyDtos.size(), actualDtos.size());
        assertEquals(historyDtos.get(0).getId(), actualDtos.get(0).getId());
        assertEquals(historyDtos.get(1).getId(), actualDtos.get(1).getId());
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toListDto(historyEntities);
    }

    @Test
    @DisplayName("Чтение всех историй по несуществующим ids, негативный сценарий")
    void readAllByIdNegativeTest() {
        List<Long> ids = List.of(1L, 2L);

        assertThrows(EntityNotFoundException.class, () -> service.readAllById(ids));
    }

    @Test
    @DisplayName("Создание истории, позитивный сценарий")
    void createPositiveTest() {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setId(1L);
        historyDto.setTransferAuditId(3L);

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(1L);
        historyEntity.setTransferAuditId(3L);

        when(mapper.toEntity(historyDto)).thenReturn(historyEntity);
        when(repository.save(historyEntity)).thenReturn(historyEntity);
        when(mapper.toDto(historyEntity)).thenReturn(historyDto);

        HistoryDto actualDto = service.create(historyDto);

        assertEquals(historyDto, actualDto);
        verify(mapper, times(1)).toEntity(historyDto);
        verify(repository, times(1)).save(historyEntity);
        verify(mapper, times(1)).toDto(historyEntity);
    }

    @Test
    @DisplayName("Создание пустой истории, негативный сценарий")
    void createNegativeTest() {
        HistoryDto historyDto = new HistoryDto();

        when(mapper.toEntity(historyDto)).thenThrow(new RuntimeException("Ошибка маппинга в Entity"));

        assertThrows(RuntimeException.class, () -> service.create(historyDto));
        verify(mapper, times(1)).toEntity(historyDto);
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Обновление истории, позитивный сценарий")
    void updatePositiveTest() {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();
        historyDto.setTransferAuditId(5L);

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setId(id);
        historyEntity.setTransferAuditId(3L);

        HistoryDto updatedHistoryDto = new HistoryDto();
        updatedHistoryDto.setId(id);
        updatedHistoryDto.setTransferAuditId(5L);

        HistoryEntity updatedHistoryEntity = new HistoryEntity();
        updatedHistoryEntity.setId(id);
        updatedHistoryEntity.setTransferAuditId(5L);

        when(repository.findById(id)).thenReturn(Optional.of(historyEntity));
        when(mapper.mergeToEntity(historyDto, historyEntity)).thenReturn(updatedHistoryEntity);
        when(repository.save(updatedHistoryEntity)).thenReturn(updatedHistoryEntity);
        when(mapper.toDto(updatedHistoryEntity)).thenReturn(updatedHistoryDto);

        HistoryDto actualDto = service.update(id, historyDto);

        assertEquals(updatedHistoryDto, actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(historyDto, historyEntity);
        verify(repository, times(1)).save(updatedHistoryEntity);
        verify(mapper, times(1)).toDto(updatedHistoryEntity);
    }

    @Test
    @DisplayName("Обновление истории по несуществующему id, негативный сценарий")
    void updateNegativeTest() {
        Long id = 1L;
        HistoryDto historyDto = new HistoryDto();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(id, historyDto));
        verify(repository, times(1)).findById(id);
    }
}