package mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.mapper.AuditMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class AuditMapperImplTest {

    @InjectMocks
    private AuditMapperImpl auditMapperImpl;

    @Test
    @DisplayName("Маппинг в dto")
    public void toDtoTest() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setId(1L);
        AuditDto expectedAuditDto = new AuditDto();
        expectedAuditDto.setId(1L);

        AuditDto auditDto = auditMapperImpl.toDto(auditEntity);

        assertEquals(expectedAuditDto, auditDto);
    }

    @Test
    @DisplayName("Маппинг в dto, на вход подан null")
    public void toDtoNullTest() {
        AuditEntity auditEntity = null;

        AuditDto auditDto = auditMapperImpl.toDto(auditEntity);

        assertNull(auditDto);
    }
}



