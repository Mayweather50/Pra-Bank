package mapper;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapperImpl;
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
class ProfileMapperImplTest {

    @InjectMocks
    private ProfileMapperImpl profileMapperImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Маппинг в dto")
    public void toDtoTest() {
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(1L);
        ProfileDto expectedProfileDto = new ProfileDto();
        expectedProfileDto.setId(1L);

        ProfileDto profileDto = profileMapperImpl.toDto(profileEntity);

        assertEquals(expectedProfileDto, profileDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    public void toDtoNullTest() {
        ProfileEntity profileEntity = null;

        ProfileDto profileDto = profileMapperImpl.toDto(profileEntity);

        assertNull(profileDto);
    }

    @Test
    @DisplayName("Маппинг в entity")
    public void toEntityTest() {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(1L);
        ProfileEntity expectedProfileEntity = new ProfileEntity();
        expectedProfileEntity.setId(null);

        ProfileEntity profileEntity = profileMapperImpl.toEntity(profileDto);

        assertEquals(expectedProfileEntity, profileEntity);
    }

    @Test
    @DisplayName("Маппинг в entity, на вход подан null")
    public void toEntityNullTest() {
        ProfileDto profileDto = null;

        ProfileEntity profileEntity = profileMapperImpl.toEntity(profileDto);

        assertNull(profileEntity);
    }

    @Test
    @DisplayName("Объединение dto и entity")
    public void mergeToEntityTest() {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(1L);
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(2L);
        ProfileEntity expectedProfileEntity = new ProfileEntity();
        expectedProfileEntity.setId(2L);

        ProfileEntity mergedProfileEntity =
                profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertEquals(expectedProfileEntity, mergedProfileEntity);
    }

    @Test
    @DisplayName("Объединение dto и entity, на вход подан null")
    public void mergeToEntityNullTest() {
        ProfileDto profileDto = null;
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setId(2L);

        ProfileEntity mergedProfileEntity =
                profileMapperImpl.mergeToEntity(profileDto, profileEntity);

        assertEquals(profileEntity, mergedProfileEntity);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto")
    public void toDtoListTest() {
        List<ProfileEntity> profileEntities = new ArrayList<>();
        ProfileEntity profileEntity1 = new ProfileEntity();
        profileEntity1.setId(1L);
        profileEntities.add(profileEntity1);
        ProfileEntity profileEntity2 = new ProfileEntity();
        profileEntity2.setId(2L);
        profileEntities.add(profileEntity2);

        List<ProfileDto> expectedProfileDtos = new ArrayList<>();
        ProfileDto profileDto1 = new ProfileDto();
        profileDto1.setId(1L);
        expectedProfileDtos.add(profileDto1);
        ProfileDto profileDto2 = new ProfileDto();
        profileDto2.setId(2L);
        expectedProfileDtos.add(profileDto2);

        List<ProfileDto> profileDtos =
                profileMapperImpl.toDtoList(profileEntities);

        assertEquals(expectedProfileDtos, profileDtos);
    }

    @Test
    @DisplayName("Маппинг списка entity в список dto, на вход подан null")
    public void toDtoListNullTest() {
        List<ProfileEntity> profileEntities = null;

        List<ProfileDto> profileDtos =
                profileMapperImpl.toDtoList(profileEntities);

        assertNull(profileDtos);
    }
}

