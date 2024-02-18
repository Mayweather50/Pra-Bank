package mapper;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.AccountDetailsIdMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class AccountDetailsIdMapperImplTest {

    @InjectMocks
    private AccountDetailsIdMapperImpl accountDetailsIdMapperImpl;

    @Test
    @DisplayName("Маппинг в dto")
    public void toDtoTest() {
        AccountDetailsIdEntity accountDetailsIdEntity = new AccountDetailsIdEntity();
        accountDetailsIdEntity.setId(1L);
        AccountDetailsIdDto expectedAccountDetailsIdDto = new AccountDetailsIdDto();
        expectedAccountDetailsIdDto.setId(1L);

        AccountDetailsIdDto accountDetailsIdDto = accountDetailsIdMapperImpl.toDto(accountDetailsIdEntity);

        assertEquals(expectedAccountDetailsIdDto, accountDetailsIdDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    public void toDtoNullTest() {
        AccountDetailsIdEntity accountDetailsIdEntity = null;

        AccountDetailsIdDto accountDetailsIdDto = accountDetailsIdMapperImpl.toDto(accountDetailsIdEntity);

        assertNull(accountDetailsIdDto);
    }

    @Test
    @DisplayName("Маппинг в entity")
    public void toEntityTest() {
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdDto();
        accountDetailsIdDto.setId(1L);
        AccountDetailsIdEntity expectedAccountDetailsIdEntity = new AccountDetailsIdEntity();
        expectedAccountDetailsIdEntity.setId(null);

        AccountDetailsIdEntity accountDetailsIdEntity = accountDetailsIdMapperImpl.toEntity(accountDetailsIdDto);

        assertEquals(expectedAccountDetailsIdEntity, accountDetailsIdEntity);
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    public void toEntityNullTest() {
        AccountDetailsIdDto accountDetailsIdDto = null;

        AccountDetailsIdEntity accountDetailsIdEntity = accountDetailsIdMapperImpl.toEntity(accountDetailsIdDto);

        assertNull(accountDetailsIdEntity);
    }

    @Test
    @DisplayName("Объединение dto и entity")
    public void mergeToEntityTest() {
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdDto();
        accountDetailsIdDto.setId(1L);
        AccountDetailsIdEntity accountDetailsIdEntity = new AccountDetailsIdEntity();
        accountDetailsIdEntity.setId(2L);
        AccountDetailsIdEntity expectedAccountDetailsIdEntity = new AccountDetailsIdEntity();
        expectedAccountDetailsIdEntity.setId(2L);

        AccountDetailsIdEntity mergedAccountDetailsIdEntity =
                accountDetailsIdMapperImpl.mergeToEntity(accountDetailsIdDto, accountDetailsIdEntity);

        assertEquals(expectedAccountDetailsIdEntity, mergedAccountDetailsIdEntity);
    }

    @Test
    @DisplayName("Объединение dto и entity, на вход подан null")
    public void mergeToEntityNullTest() {
        AccountDetailsIdDto accountDetailsIdDto = null;
        AccountDetailsIdEntity accountDetailsIdEntity = new AccountDetailsIdEntity();
        accountDetailsIdEntity.setId(2L);

        AccountDetailsIdEntity mergedAccountDetailsIdEntity =
                accountDetailsIdMapperImpl.mergeToEntity(accountDetailsIdDto, accountDetailsIdEntity);

        assertEquals(accountDetailsIdEntity, mergedAccountDetailsIdEntity);
    }

    @Test
    @DisplayName("маппинг списка entity в список dto")
    public void toDtoListTest() {
        List<AccountDetailsIdEntity> accountDetailsIdEntities = new ArrayList<>();
        AccountDetailsIdEntity accountDetailsIdEntity1 = new AccountDetailsIdEntity();
        accountDetailsIdEntity1.setId(1L);
        accountDetailsIdEntities.add(accountDetailsIdEntity1);
        AccountDetailsIdEntity accountDetailsIdEntity2 = new AccountDetailsIdEntity();
        accountDetailsIdEntity2.setId(2L);
        accountDetailsIdEntities.add(accountDetailsIdEntity2);

        List<AccountDetailsIdDto> expectedAccountDetailsIdDtos = new ArrayList<>();
        AccountDetailsIdDto accountDetailsIdDto1 = new AccountDetailsIdDto();
        accountDetailsIdDto1.setId(1L);
        expectedAccountDetailsIdDtos.add(accountDetailsIdDto1);
        AccountDetailsIdDto accountDetailsIdDto2 = new AccountDetailsIdDto();
        accountDetailsIdDto2.setId(2L);
        expectedAccountDetailsIdDtos.add(accountDetailsIdDto2);

        List<AccountDetailsIdDto> accountDetailsIdDtos = accountDetailsIdMapperImpl.toDtoList(accountDetailsIdEntities);

        assertEquals(expectedAccountDetailsIdDtos, accountDetailsIdDtos);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto, на вход подан null")
    public void toDtoListNullTest() {
        List<AccountDetailsIdEntity> accountDetailsIdEntities = null;

        List<AccountDetailsIdDto> accountDetailsIdDtos = accountDetailsIdMapperImpl.toDtoList(accountDetailsIdEntities);

        assertNull(accountDetailsIdDtos);
    }
}