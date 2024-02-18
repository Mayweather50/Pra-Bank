package service.impl;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.impl.ProfileServiceImp;

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
class ProfileServiceImpTest {

    @InjectMocks
    ProfileServiceImp profileServiceImp;

    @Mock
    private ProfileRepository repository;

    @Mock
    private ProfileMapper mapper;

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        ProfileEntity entity = new ProfileEntity();
        ProfileDto dto = new ProfileDto();
        entity.setId(id);
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        ProfileDto actualDto = profileServiceImp.findById(id);

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
            profileServiceImp.findById(id);
        });

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("сохранение пользователя, позитивный сценарий")
    void saveUserByUserDataPositiveTest() {
        ProfileDto dto = new ProfileDto();
        ProfileEntity entity = new ProfileEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ProfileDto actualDto = profileServiceImp.save(dto);

        assertNotNull(actualDto);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("сохранение пользователя c пустыми данными, негативный сценарий")
    void saveUserByEmptyUserDataNegativeTest() {
        ProfileDto dto = null;
        profileServiceImp.save(dto);
        verify(mapper, times(1)).toEntity(dto);
    }

    @Test
    @DisplayName("обновление пользователя по id, позитивный сценарий")
    void updateUserByUserDataPositiveTest() {
        Long id = 1L;
        ProfileDto dto = new ProfileDto();
        ProfileEntity entity = new ProfileEntity();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.mergeToEntity(dto, entity)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        ProfileDto actualDto = profileServiceImp.update(id, dto);

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
        ProfileDto dto = new ProfileDto();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            profileServiceImp.update(id, dto);
        });

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("поиск всех пользователей по ids, позитивный сценарий")
    void findAllByIdsPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<ProfileEntity> entities = Arrays.asList(new ProfileEntity(), new ProfileEntity());
        List<ProfileDto> dtos = Arrays.asList(new ProfileDto(), new ProfileDto());

        when(repository.findAllById(ids)).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(dtos);

        List<ProfileDto> actualDtos = profileServiceImp.findAllById(ids);

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

        List<ProfileDto> actualDtos = profileServiceImp.findAllById(ids);

        assertTrue(actualDtos.isEmpty());
        verify(repository, times(1)).findAllById(ids);
    }
}


