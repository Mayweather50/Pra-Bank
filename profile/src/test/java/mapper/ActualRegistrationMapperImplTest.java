package mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.ActualRegistrationMapper;
import com.bank.profile.mapper.ActualRegistrationMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ActualRegistrationMapperImplTest {

    @InjectMocks
    private ActualRegistrationMapperImpl actualRegistrationMapperImpl;

    @Mock
    private ActualRegistrationMapper actualRegistrationMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Маппинг в dto")
    public void toDtoTest() {
        ActualRegistrationEntity actualRegistrationEntity = new ActualRegistrationEntity();
        actualRegistrationEntity.setId(1L);
        ActualRegistrationDto expectedActualRegistrationDto = new ActualRegistrationDto();
        expectedActualRegistrationDto.setId(1L);

        lenient().when(actualRegistrationMapper.toDto(actualRegistrationEntity)).thenReturn(expectedActualRegistrationDto);
        ActualRegistrationDto actualRegistrationDto = actualRegistrationMapperImpl.toDto(actualRegistrationEntity);

        assertEquals(expectedActualRegistrationDto, actualRegistrationDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    public void toDtoNullTest() {
        ActualRegistrationEntity actualRegistrationEntity = null;

        ActualRegistrationDto actualRegistrationDto = actualRegistrationMapperImpl.toDto(actualRegistrationEntity);

        assertNull(actualRegistrationDto);
    }

    @Test
    @DisplayName("Маппинг в entity")
    public void toEntityTest() {
        ActualRegistrationDto actualRegistrationDto = new ActualRegistrationDto();
        actualRegistrationDto.setId(1L);
        ActualRegistrationEntity expectedActualRegistrationEntity = new ActualRegistrationEntity();
        expectedActualRegistrationEntity.setId(null);

        ActualRegistrationEntity actualRegistrationEntity = actualRegistrationMapperImpl.toEntity(actualRegistrationDto);

        assertEquals(expectedActualRegistrationEntity, actualRegistrationEntity);
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    public void toEntityNullTest() {
        ActualRegistrationDto actualRegistrationDto = null;

        ActualRegistrationEntity actualRegistrationEntity = actualRegistrationMapperImpl.toEntity(actualRegistrationDto);

        assertNull(actualRegistrationEntity);
    }

    @Test
    @DisplayName("Объединение dto и entity")
    public void mergeToEntityTest() {
        ActualRegistrationDto actualRegistrationDto = new ActualRegistrationDto();
        actualRegistrationDto.setId(1L);
        ActualRegistrationEntity actualRegistrationEntity = new ActualRegistrationEntity();
        actualRegistrationEntity.setId(2L);
        ActualRegistrationEntity expectedActualRegistrationEntity = new ActualRegistrationEntity();
        expectedActualRegistrationEntity.setId(2L);

        ActualRegistrationEntity mergedActualRegistrationEntity =
                actualRegistrationMapperImpl.mergeToEntity(actualRegistrationDto, actualRegistrationEntity);

        assertEquals(expectedActualRegistrationEntity, mergedActualRegistrationEntity);
    }

    @Test
    @DisplayName("Объединение dto и entity, на вход подан null")
    public void mergeToEntityNullTest() {
        ActualRegistrationDto actualRegistrationDto = null;
        ActualRegistrationEntity actualRegistrationEntity = new ActualRegistrationEntity();
        actualRegistrationEntity.setId(2L);

        ActualRegistrationEntity mergedActualRegistrationEntity =
                actualRegistrationMapperImpl.mergeToEntity(actualRegistrationDto, actualRegistrationEntity);

        assertEquals(actualRegistrationEntity, mergedActualRegistrationEntity);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto")
    public void toDtoListTest() {
        List<ActualRegistrationEntity> actualRegistrationEntities = new ArrayList<>();
        ActualRegistrationEntity actualRegistrationEntity1 = new ActualRegistrationEntity();
        actualRegistrationEntity1.setId(1L);
        actualRegistrationEntities.add(actualRegistrationEntity1);
        ActualRegistrationEntity actualRegistrationEntity2 = new ActualRegistrationEntity();
        actualRegistrationEntity2.setId(2L);
        actualRegistrationEntities.add(actualRegistrationEntity2);

        List<ActualRegistrationDto> expectedActualRegistrationDtos = new ArrayList<>();
        ActualRegistrationDto actualRegistrationDto1 = new ActualRegistrationDto();
        actualRegistrationDto1.setId(1L);
        expectedActualRegistrationDtos.add(actualRegistrationDto1);
        ActualRegistrationDto actualRegistrationDto2 = new ActualRegistrationDto();
        actualRegistrationDto2.setId(2L);
        expectedActualRegistrationDtos.add(actualRegistrationDto2);

        List<ActualRegistrationDto> actualRegistrationDtos =
                actualRegistrationMapperImpl.toDtoList(actualRegistrationEntities);

        assertEquals(expectedActualRegistrationDtos, actualRegistrationDtos);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto, на вход подан null")
    public void toDtoListNullTest() {
        List<ActualRegistrationEntity> actualRegistrationEntities = null;

        List<ActualRegistrationDto> actualRegistrationDtos =
                actualRegistrationMapperImpl.toDtoList(actualRegistrationEntities);

        assertNull(actualRegistrationDtos);
    }
}
