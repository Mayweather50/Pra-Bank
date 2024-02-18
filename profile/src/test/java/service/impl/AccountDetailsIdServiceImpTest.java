package service.impl;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.impl.AccountDetailsIdServiceImp;

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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class AccountDetailsIdServiceImpTest {

    @InjectMocks
    private AccountDetailsIdServiceImp accountDetailsIdServiceImp;

    @Mock
    private AccountDetailsIdRepository repository;

    @Mock
    private AccountDetailsIdMapper mapper;

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        AccountDetailsIdEntity entity = new AccountDetailsIdEntity();
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        entity.setId(id);
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        AccountDetailsIdDto actualDto = accountDetailsIdServiceImp.findById(id);

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
            accountDetailsIdServiceImp.findById(id);
        });

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("сохранение пользователя, позитивный сценарий")
    void saveUserByUserDataPositiveTest() {
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        AccountDetailsIdEntity entity = new AccountDetailsIdEntity();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        AccountDetailsIdDto actualDto = accountDetailsIdServiceImp.save(dto);

        assertNotNull(actualDto);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("сохранение пользователя c пустыми данными, негативный сценарий")
    void saveUserByEmptyUserDataNegativeTest() {
        AccountDetailsIdDto dto = new AccountDetailsIdDto();

        accountDetailsIdServiceImp.save(dto);

        verify(repository, never()).save(any(AccountDetailsIdEntity.class));
    }

    @Test
    @DisplayName("обновление пользователя по id, позитивный сценарий")
    void updateUserByUserDataPositiveTest() {
        Long id = 1L;
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        AccountDetailsIdEntity entity = new AccountDetailsIdEntity();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.mergeToEntity(dto, entity)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        AccountDetailsIdDto actualDto = accountDetailsIdServiceImp.update(id, dto);

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
        AccountDetailsIdDto dto = new AccountDetailsIdDto();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            accountDetailsIdServiceImp.update(id, dto);
        });

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Поиск всех пользователей по ids")
    void findAllByIdsTest() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<AccountDetailsIdEntity> entities = Arrays.asList(
                new AccountDetailsIdEntity(),
                new AccountDetailsIdEntity()
        );
        List<AccountDetailsIdDto> dtos = Arrays.asList(
                new AccountDetailsIdDto(),
                new AccountDetailsIdDto()
        );

        when(repository.findAllById(ids)).thenReturn(entities);
        when(mapper.toDtoList(entities)).thenReturn(dtos);

        List<AccountDetailsIdDto> actualDtos = accountDetailsIdServiceImp.findAllById(ids);

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

        List<AccountDetailsIdDto> actualDtos = accountDetailsIdServiceImp.findAllById(ids);

        assertTrue(actualDtos.isEmpty());
        verify(repository, times(1)).findAllById(ids);
    }
}
