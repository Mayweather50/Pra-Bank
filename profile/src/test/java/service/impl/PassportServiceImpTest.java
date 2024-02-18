package service.impl;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.mapper.PassportMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.service.impl.PassportServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PassportServiceImpTest {

    @InjectMocks
    PassportServiceImp passportServiceImp;

    @Mock
    private PassportRepository repository;

    @Mock
    private PassportMapper mapper;

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        PassportEntity entity = new PassportEntity();
        PassportDto dto = new PassportDto();
        entity.setId(id);
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        PassportDto actualDto = passportServiceImp.findById(id);

        assertNotNull(actualDto);
        assertEquals(id, actualDto.getId());
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            passportServiceImp.findById(id);
        });

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("сохранение пользователя, позитивный сценарий")
    void saveUserByUserDataPositiveTest() {
        PassportDto dto = new PassportDto();
        PassportEntity entity = new PassportEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        PassportDto actualDto = passportServiceImp.save(dto);

        assertNotNull(actualDto);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("сохранение пользователя c пустыми данными, негативный сценарий")
    void saveUserByEmptyUserDataNegativeTest() {
        PassportDto dto = null;
        passportServiceImp.save(dto);
        verify(mapper, times(1)).toEntity(dto);
    }

    @Test
    @DisplayName("обновление пользователя по id, позитивный сценарий")
    void updateUserByUserDataPositiveTest() {
        Long id = 1L;
        PassportDto dto = new PassportDto();
        PassportEntity entity = new PassportEntity();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.mergeToEntity(dto, entity)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        PassportDto actualDto = passportServiceImp.update(id, dto);

        assertNotNull(actualDto);
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).mergeToEntity(dto, entity);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("обновление пользователя по несуществующему id, негативный сценарий")
    void updateUserByEmptyUserDataNegativeTest() {
        Long id = 1L;
        PassportDto dto = new PassportDto();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            passportServiceImp.update(id, dto);
        });

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("поиск всех пользователей по ids, позитивный сценарий")
    void findAllByIdsPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<PassportEntity> entities = Arrays.asList(new PassportEntity(), new PassportEntity());
        List<PassportDto> dtos = Arrays.asList(new PassportDto(), new PassportDto());

        when(repository.findAllById(ids)).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(dtos);

        List<PassportDto> actualDtos = passportServiceImp.findAllById(ids);

        assertNotNull(actualDtos);
        assertEquals(dtos.size(), actualDtos.size());
        verify(repository, times(1)).findAllById(ids);
        verify(mapper, times(1)).toDtoList(entities);
    }

    @Test
    @DisplayName("поиск всех пользователей по несуществующим ids, негативный сценарий")
    void findAllByNonExistIdsNegativeTest() {
        List<Long> ids = Arrays.asList(1L, 2L);

        when(repository.findAllById(ids)).thenReturn(Arrays.asList());

        List<PassportDto> actualDtos = passportServiceImp.findAllById(ids);

        assertTrue(actualDtos.isEmpty());
        verify(repository, times(1)).findAllById(ids);
    }
}


