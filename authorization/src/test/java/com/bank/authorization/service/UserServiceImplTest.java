package com.bank.authorization.service;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        UserDto userDto = new UserDto();
        userDto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(userEntity));
        when(mapper.toDTO(userEntity)).thenReturn(userDto);

        UserDto actualUserDto = userServiceImpl.findById(id);

        assertNotNull(actualUserDto);
        assertEquals(id, actualUserDto.getId());
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDTO(userEntity);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userServiceImpl.findById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("сохранение пользователя, позитивный сценарий")
    void saveUserByUserDataPositiveTest() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setRole("admin");
        userDto.setPassword("password");
        userDto.setProfileId(1L);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setRole("admin");
        userEntity.setPassword("password");
        userEntity.setProfileId(1L);

        when(mapper.toEntity(userDto)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toDTO(userEntity)).thenReturn(userDto);

        UserDto actualUserDto = userServiceImpl.save(userDto);

        assertNotNull(actualUserDto);
        assertEquals(userDto, actualUserDto);
        verify(mapper, times(1)).toEntity(userDto);
        verify(repository, times(1)).save(userEntity);
        verify(mapper, times(1)).toDTO(userEntity);
    }

    @Test
    @DisplayName("сохранение пользователя c пустыми данными, негативный сценарий")
    void saveUserByEmptyUserDataNegativeTest() {
        UserDto userDto = null;

        assertThrows(NullPointerException.class, () -> userServiceImpl.save(userDto));

        verify(mapper, times(1)).toEntity(userDto);
    }

    @Test
    @DisplayName("обновление пользователя по id, позитивный сценарий")
    void updateUserByUserDataPositiveTest() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.setRole("admin");
        userDto.setPassword("password");
        userDto.setProfileId(1L);
        UserEntity oldUserEntity = new UserEntity();
        oldUserEntity.setId(userId);
        oldUserEntity.setRole("user");
        oldUserEntity.setPassword("oldpassword");
        oldUserEntity.setProfileId(1L);
        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setId(userId);
        updatedUserEntity.setRole("admin");
        updatedUserEntity.setPassword("password");
        updatedUserEntity.setProfileId(1L);

        when(repository.findById(userId)).thenReturn(Optional.of(oldUserEntity));
        when(repository.save(updatedUserEntity)).thenReturn(updatedUserEntity);
        when(mapper.mergeToEntity(userDto, oldUserEntity)).thenReturn(updatedUserEntity);
        when(mapper.toDTO(updatedUserEntity)).thenReturn(userDto);

        UserDto actualUserDto = userServiceImpl.update(userId, userDto);

        assertEquals(userDto, actualUserDto);
        verify(repository, times(1)).findById(userId);
        verify(repository, times(1)).save(updatedUserEntity);
        verify(mapper, times(1)).mergeToEntity(userDto, oldUserEntity);
        verify(mapper, times(1)).toDTO(updatedUserEntity);
    }

    @Test
    @DisplayName("обновление пользователя по несуществующему id, негативный сценарий")
    void updateUserByEmptyUserDataNegativeTest () {
        Long id = 1L;
        UserDto userDto = new UserDto();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userServiceImpl.update(id, userDto));
        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("поиск всех пользователей по ids, позитивный сценарий")
    void findAllByIdsPositiveTest() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1L);
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity1);
        userEntities.add(userEntity2);
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto1);
        userDtos.add(userDto2);

        when(repository.findById(1L)).thenReturn(Optional.of(userEntity1));
        when(repository.findById(2L)).thenReturn(Optional.of(userEntity2));
        when(mapper.toDtoList(userEntities)).thenReturn(userDtos);

        List<UserDto> actualUserDtos = userServiceImpl.findAllByIds(ids);

        assertEquals(userDtos.size(), actualUserDtos.size());
        assertEquals(userDtos.get(0).getId(), actualUserDtos.get(0).getId());
        assertEquals(userDtos.get(1).getId(), actualUserDtos.get(1).getId());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).findById(2L);
        verify(mapper, times(1)).toDtoList(userEntities);
    }

    @Test
    @DisplayName("поиск всех пользователей по несуществующим ids, негативный сценарий")
    void findAllByNonExistIdsNegativeTest() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        assertThrows(EntityNotFoundException.class, () -> userServiceImpl.findAllByIds(ids));
    }
}