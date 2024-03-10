package controller;

import com.bank.account.AccountApplication;
import com.bank.account.controller.AccountDetailsController;
import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.service.AccountDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import  org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountDetailsController.class)
@ContextConfiguration(classes = AccountApplication.class)
class AccountDetailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountDetailsService auditService;

    @Test
    void read() throws Exception {
        Long id = 1L;
        AccountDetailsDto auditDto = new AccountDetailsDto();
        auditDto.setId(id);
        when(auditService.findById(id)).thenReturn(auditDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/details/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(1)));

    }
    @Test
    void readNegativeTest() throws Exception {
        Long id = null;
        when(auditService.findById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/details/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void create() throws Exception {
        Long id = 1L;
        AccountDetailsDto detailsDto = new AccountDetailsDto();
        detailsDto.setId(id);

        when(auditService.save(detailsDto)).thenReturn(detailsDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/details/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(detailsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(id.intValue())));



    }
    @Test
    void createNegativeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/details/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        Long id = 1L;
        AccountDetailsDto detailsDto = new AccountDetailsDto();
        detailsDto.setId(id);

        when(auditService.update(id,detailsDto)).thenReturn(detailsDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/details/update/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(detailsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",is(id.intValue())));
    }
    @Test
    void updateNegativeTest() throws Exception {
        Long id = 1L;
        AccountDetailsDto detailsDto = new AccountDetailsDto();
        detailsDto.setId(id);

        when(auditService.update(id, detailsDto)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/details/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(detailsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void readAll() throws Exception {
        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> id = List.of(id1,id2);
        List<AccountDetailsDto> ids = new ArrayList<>();

        AccountDetailsDto detailsDto = new AccountDetailsDto();
        detailsDto.setId(id1);

        ids.add(detailsDto);

        when(auditService.findAllById(id)).thenReturn(ids);

        mockMvc.perform(MockMvcRequestBuilders.get("/details/read/all")
                        .param("ids", id1.toString(), id2.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(id1.intValue())));



    }
}