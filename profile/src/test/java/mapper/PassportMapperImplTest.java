package mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.mapper.PassportMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class PassportMapperImplTest {

    @InjectMocks
    private PassportMapperImpl passportMapperImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Маппинг в dto")
    public void toDtoTest() {
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setId(1L);
        PassportDto expectedPassportDto = new PassportDto();
        expectedPassportDto.setId(1L);

        PassportDto passportDto = passportMapperImpl.toDto(passportEntity);

        assertEquals(expectedPassportDto, passportDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    public void toDtoNullTest() {
        PassportEntity passportEntity = null;

        PassportDto passportDto = passportMapperImpl.toDto(passportEntity);

        assertNull(passportDto);
    }

    @Test
    @DisplayName("Маппинг в entity")
    public void toEntityTest() {
        PassportDto passportDto = new PassportDto();
        passportDto.setId(1L);
        PassportEntity expectedPassportEntity = new PassportEntity();
        expectedPassportEntity.setId(null);

        PassportEntity passportEntity = passportMapperImpl.toEntity(passportDto);

        assertEquals(expectedPassportEntity, passportEntity);
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    public void toEntityNullTest() {
        PassportDto passportDto = null;

        PassportEntity passportEntity = passportMapperImpl.toEntity(passportDto);

        assertNull(passportEntity);
    }

    @Test
    @DisplayName("Объединение dto и entity")
    public void mergeToEntityTest() {
        PassportDto passportDto = new PassportDto();
        passportDto.setId(1L);
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setId(2L);
        PassportEntity expectedPassportEntity = new PassportEntity();
        expectedPassportEntity.setId(2L);

        PassportEntity mergedPassportEntity =
                passportMapperImpl.mergeToEntity(passportDto, passportEntity);

        assertEquals(expectedPassportEntity, mergedPassportEntity);
    }

    @Test
    @DisplayName("Объединение dto и entity, на вход подан null")
    public void mergeToEntityNullTest() {
        PassportDto passportDto = null;
        PassportEntity passportEntity = new PassportEntity();
        passportEntity.setId(2L);

        PassportEntity mergedPassportEntity =
                passportMapperImpl.mergeToEntity(passportDto, passportEntity);

        assertEquals(passportEntity, mergedPassportEntity);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto")
    public void toDtoListTest() {
        List<PassportEntity> passportEntities = new ArrayList<>();
        PassportEntity passportEntity1 = new PassportEntity();
        passportEntity1.setId(1L);
        passportEntities.add(passportEntity1);
        PassportEntity passportEntity2 = new PassportEntity();
        passportEntity2.setId(2L);
        passportEntities.add(passportEntity2);

        List<PassportDto> expectedPassportDtos = new ArrayList<>();
        PassportDto passportDto1 = new PassportDto();
        passportDto1.setId(1L);
        expectedPassportDtos.add(passportDto1);
        PassportDto passportDto2 = new PassportDto();
        passportDto2.setId(2L);
        expectedPassportDtos.add(passportDto2);

        List<PassportDto> passportDtos =
                passportMapperImpl.toDtoList(passportEntities);

        assertEquals(expectedPassportDtos, passportDtos);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto, на вход подан null")
    public void toDtoListNullTest() {
        List<PassportEntity> passportEntities = null;

        List<PassportDto> passportDtos =
                passportMapperImpl.toDtoList(passportEntities);

        assertNull(passportDtos);
    }
}



