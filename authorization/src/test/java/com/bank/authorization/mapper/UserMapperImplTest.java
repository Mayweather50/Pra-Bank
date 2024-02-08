package com.bank.authorization.mapper;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class UserMapperImplTest {

    @InjectMocks
    private UserMapperImpl userMapperImpl;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("маппинг в dto")
    public void toDtoTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setRole("admin");
        userEntity.setPassword("password");
        userEntity.setProfileId(123L);
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(1L);
        expectedUserDto.setRole("admin");
        expectedUserDto.setPassword("password");
        expectedUserDto.setProfileId(123L);

        when(userMapper.toDTO(userEntity)).thenReturn(expectedUserDto);

        UserDto userDto = userMapperImpl.toDTO(userEntity);

        assertEquals(expectedUserDto, userDto);
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    public void toDtoNullTest() {
        UserEntity userEntity = null;

        UserDto userDto = userMapperImpl.toDTO(userEntity);

        assertNull(userDto);
    }

    @Test
    @DisplayName("маппинг в entity")
    public void toEntityTest() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setRole("admin");
        userDto.setPassword("password");
        userDto.setProfileId(123L);
        UserEntity expectedUserEntity = new UserEntity();
        expectedUserEntity.setRole("admin");
        expectedUserEntity.setPassword("password");
        expectedUserEntity.setProfileId(123L);

        when(userMapper.toEntity(userDto)).thenReturn(expectedUserEntity);

        UserEntity userEntity = userMapperImpl.toEntity(userDto);

        assertEquals(expectedUserEntity, userEntity);
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    public void toEntityNullTest() {
        UserDto userDto = null;

        UserEntity userEntity = userMapperImpl.toEntity(userDto);

        assertNull(userEntity);
    }

    @Test
    @DisplayName("маппинг в list dto")
    public void toDtoListTest() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setId(1L);
        userEntity1.setRole("admin");
        userEntity1.setPassword("password");
        userEntity1.setProfileId(123L);
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        userEntity2.setRole("user");
        userEntity2.setPassword("123456");
        userEntity2.setProfileId(456L);
        List<UserEntity> userEntityList = Arrays.asList(userEntity1, userEntity2);
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setRole("admin");
        userDto1.setPassword("password");
        userDto1.setProfileId(123L);
        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setRole("user");
        userDto2.setPassword("123456");
        userDto2.setProfileId(456L);
        List<UserDto> expectedUserDtoList = Arrays.asList(userDto1, userDto2);

        when(userMapper.toDtoList(userEntityList)).thenReturn(expectedUserDtoList);

        List<UserDto> userDtoList = userMapperImpl.toDtoList(userEntityList);

        assertEquals(expectedUserDtoList, userDtoList);
    }

    @Test
    @DisplayName("маппинг в list dto, на вход подан null")
    public void toDtoListNullTest() {
        List<UserEntity> userEntityList = null;

        List<UserDto> userDtoList = userMapperImpl.toDtoList(userEntityList);

        assertNull(userDtoList);
    }

    @Test
    @DisplayName("слияние в entity")
    public void mergeToEntityTest() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setRole("admin");
        userDto.setPassword("password");
        userDto.setProfileId(123L);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setRole("user");
        userEntity.setPassword("123456");
        userEntity.setProfileId(456L);
        UserEntity expectedMergedUserEntity = new UserEntity();
        expectedMergedUserEntity.setId(1L);
        expectedMergedUserEntity.setRole("admin");
        expectedMergedUserEntity.setPassword("password");
        expectedMergedUserEntity.setProfileId(123L);

        when(userMapper.mergeToEntity(userDto, userEntity)).thenReturn(expectedMergedUserEntity);

        UserEntity mergedUserEntity = userMapperImpl.mergeToEntity(userDto, userEntity);

        assertEquals(expectedMergedUserEntity, mergedUserEntity);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    public void mergeToEntityNullTest() {
        UserDto userDto = null;
        UserEntity userEntity = new UserEntity();

        UserEntity mergedUserEntity = userMapperImpl.mergeToEntity(userDto, userEntity);

        assertEquals(userEntity, mergedUserEntity);
    }
}