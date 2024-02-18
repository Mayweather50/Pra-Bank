package mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.RegistrationMapperImpl;
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
class RegistrationMapperImplTest {

    @InjectMocks
    private RegistrationMapperImpl registrationMapperImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Маппинг в dto")
    public void toDtoTest() {
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setId(1L);
        RegistrationDto expectedRegistrationDto = new RegistrationDto();
        expectedRegistrationDto.setId(1L);

        RegistrationDto registrationDto = registrationMapperImpl.toDto(registrationEntity);

        assertEquals(expectedRegistrationDto, registrationDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    public void toDtoNullTest() {
        RegistrationEntity registrationEntity = null;

        RegistrationDto registrationDto = registrationMapperImpl.toDto(registrationEntity);

        assertNull(registrationDto);
    }

    @Test
    @DisplayName("Маппинг в entity")
    public void toEntityTest() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setId(1L);
        RegistrationEntity expectedRegistrationEntity = new RegistrationEntity();
        expectedRegistrationEntity.setId(null);

        RegistrationEntity registrationEntity = registrationMapperImpl
                .toEntity(registrationDto);

        assertEquals(expectedRegistrationEntity, registrationEntity);
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    public void toEntityNullTest() {
        RegistrationDto registrationDto = null;

        RegistrationEntity registrationEntity = registrationMapperImpl
                .toEntity(registrationDto);

        assertNull(registrationEntity);
    }

    @Test
    @DisplayName("Объединение dto и entity")
    public void mergeToEntityTest() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setId(1L);
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setId(2L);
        RegistrationEntity expectedRegistrationEntity = new RegistrationEntity();
        expectedRegistrationEntity.setId(2L);

        RegistrationEntity mergedRegistrationEntity =
                registrationMapperImpl.mergeToEntity(registrationDto, registrationEntity);

        assertEquals(expectedRegistrationEntity, mergedRegistrationEntity);
    }

    @Test
    @DisplayName("Объединение dto и entity, на вход подан null")
    public void mergeToEntityNullTest() {
        RegistrationDto registrationDto = null;
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setId(2L);

        RegistrationEntity mergedRegistrationEntity =
                registrationMapperImpl.mergeToEntity(registrationDto, registrationEntity);

        assertEquals(registrationEntity, mergedRegistrationEntity);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto")
    public void toDtoListTest() {
        List<RegistrationEntity> registrationEntities = new ArrayList<>();
        RegistrationEntity registrationEntity1 = new RegistrationEntity();
        registrationEntity1.setId(1L);
        registrationEntities.add(registrationEntity1);
        RegistrationEntity registrationEntity2 = new RegistrationEntity();
        registrationEntity2.setId(2L);
        registrationEntities.add(registrationEntity2);

        List<RegistrationDto> expectedRegistrationDtos = new ArrayList<>();
        RegistrationDto registrationDto1 = new RegistrationDto();
        registrationDto1.setId(1L);
        expectedRegistrationDtos.add(registrationDto1);
        RegistrationDto registrationDto2 = new RegistrationDto();
        registrationDto2.setId(2L);
        expectedRegistrationDtos.add(registrationDto2);

        List<RegistrationDto> registrationDtos =
                registrationMapperImpl.toDtoList(registrationEntities);

        assertEquals(expectedRegistrationDtos, registrationDtos);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto, на вход подан null")
    public void toDtoListNullTest() {
        List<RegistrationEntity> registrationEntities = null;

        List<RegistrationDto> registrationDtos =
                registrationMapperImpl.toDtoList(registrationEntities);

        assertNull(registrationDtos);
    }
}
