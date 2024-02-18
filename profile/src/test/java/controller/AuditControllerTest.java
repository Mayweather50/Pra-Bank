package controller;

import com.bank.profile.ProfileApplication;
import com.bank.profile.controller.AuditController;
import com.bank.profile.dto.AuditDto;
import com.bank.profile.service.AuditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(AuditController.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ProfileApplication.class)
class AuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditService auditService;


    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        Long id = 1L;
        AuditDto expectedAuditDto = new AuditDto();
        expectedAuditDto.setId(id);

        when(auditService.findById(id)).thenReturn(expectedAuditDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest () throws Exception  {
        Long id = null;

        when(auditService.findById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

