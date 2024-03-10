package controller;

import com.bank.account.AccountApplication;
import com.bank.account.controller.AccountAuditController;
import com.bank.account.dto.AuditDto;
import com.bank.account.service.AccountAuditService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountAuditController.class)
@ContextConfiguration(classes = AccountApplication.class)
public class AccountAuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountAuditService service;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    public void readTest() throws Exception {
        Long id = 1L;
        AuditDto auditDto = new AuditDto();
        auditDto.setId(id);

        when(service.findById(id)).thenReturn(auditDto);

        mockMvc.perform(get("/audit/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @DisplayName("чтение по id, негативный сценарий")
    public void readTest_NotFound() throws Exception {
        Long id = null;

        when(service.findById(id)).thenReturn(null);

        mockMvc.perform(get("/audit/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
