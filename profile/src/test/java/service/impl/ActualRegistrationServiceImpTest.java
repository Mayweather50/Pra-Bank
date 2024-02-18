package service.impl;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.repository.ActualRegistrationRepository;
import com.bank.profile.service.impl.ActualRegistrationServiceImp;

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
class ActualRegistrationServiceImpTest {

    @InjectMocks
    ActualRegistrationServiceImp actualRegistrationServiceImp;

    @Mock
    private ActualRegistrationRepository repository;

    @Mock
    private ActualRegistrationMapper mapper;

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        ActualRegistrationEntity entity = new ActualRegistrationEntity();
        ActualRegistrationDto dto = new ActualRegistrationDto();
        entity.setId(id);
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ActualRegistrationDto actualDto = actualRegistrationServiceImp.findById(id);

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
            actualRegistrationServiceImp.findById(id);
        });

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("сохранение пользователя, позитивный сценарий")
    void saveUserByUserDataPositiveTest() {
        ActualRegistrationDto dto = new ActualRegistrationDto();
        ActualRegistrationEntity entity = new ActualRegistrationEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ActualRegistrationDto actualDto = actualRegistrationServiceImp.save(dto);

        assertNotNull(actualDto);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("сохранение пользователя c пустыми данными, негативный сценарий")
    void saveUserByEmptyUserDataNegativeTest() {
        ActualRegistrationDto dto = null;

        actualRegistrationServiceImp.save(dto);

        verify(mapper, times(1)).toEntity(dto);
    }

    @Test
    @DisplayName("обновление пользователя по id, позитивный сценарий")
    void updateUserByUserDataPositiveTest() {
        Long id = 1L;
        ActualRegistrationDto dto = new ActualRegistrationDto();
        ActualRegistrationEntity entity = new ActualRegistrationEntity();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.mergeToEntity(dto, entity)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ActualRegistrationDto actualDto = actualRegistrationServiceImp.update(id, dto);

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
        ActualRegistrationDto dto = new ActualRegistrationDto();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            actualRegistrationServiceImp.update(id, dto);
        });

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Поиск всех пользователей по ids")
    void findAllByIdsPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<ActualRegistrationEntity> entities = Arrays.asList(
                new ActualRegistrationEntity(),
                new ActualRegistrationEntity()
        );
        List<ActualRegistrationDto> dtos = Arrays.asList(
                new ActualRegistrationDto(),
                new ActualRegistrationDto()
        );

        when(repository.findAllById(ids)).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(dtos);

        List<ActualRegistrationDto> actualDtos = actualRegistrationServiceImp.findAllById(ids);

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

        List<ActualRegistrationDto> actualDtos = actualRegistrationServiceImp.findAllById(ids);

        assertTrue(actualDtos.isEmpty());
        verify(repository, times(1)).findAllById(ids);
    }
}


