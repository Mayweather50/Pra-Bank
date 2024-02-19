package controller;

import com.bank.profile.ProfileApplication;
import com.bank.profile.controller.AccountDetailsIdController;
import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.service.AccountDetailsIdService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@WebMvcTest(AccountDetailsIdController.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ProfileApplication.class)
class AccountDetailsIdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountDetailsIdService service;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readPositiveTest() throws Exception {
        Long id = 1L;
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        dto.setId(id);

        when(service.findById(id)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/account/details/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws Exception {
        Long id = null;

        mockMvc.perform(MockMvcRequestBuilders.get("/account/details/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName("создание пользователя, позитивный сценарий")
    void createPositiveTest() throws Exception {
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        dto.setId(1L);

        when(service.save(dto)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.post("/account/details/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dto.getId()));
    }

    @Test
    @DisplayName("создание пользователя, негативный сценарий")
    void createNegativeTest() throws Exception {
        AccountDetailsIdDto dto = null;

        String exceptionMessage = "Неверные данные для создания AccountDetailsId!";
        when(service.save(dto)).thenThrow(new IllegalArgumentException(exceptionMessage));

        String content = new ObjectMapper().writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/account/details/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("обновление пользователя, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        Long id = 1L;
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        dto.setId(id);

        when(service.update(id, dto)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.put("/account/details/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(dto.getId()));
    }

    @Test
    @DisplayName("обновление пользователя, негативный сценарий")
    void updateNegativeTest() throws Exception {
        Long id = null;
        AccountDetailsIdDto dto = new AccountDetailsIdDto();
        dto.setId(id);

        String exceptionMessage = "Обновление невозможно, AccountDetailsId не найден!";
        when(service.update(id, dto)).thenThrow(new EntityNotFoundException(exceptionMessage));

        String content = new ObjectMapper().writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.put("/account/details/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("чтение всех пользователей по ids, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<AccountDetailsIdDto> dtos = Arrays.asList
                (new AccountDetailsIdDto(), new AccountDetailsIdDto());

        when(service.findAllById(ids)).thenReturn(dtos);

        String idsParam = ids.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        mockMvc.perform(MockMvcRequestBuilders.get("/account/details/read/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("ids", idsParam))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("чтение всех пользователей по несуществующим ids, негативный сценарий")
    void readAllByNonExistIdsNegativeTest() throws Exception {
        String id1 = "1";
        String id2 = "2";
        mockMvc.perform(MockMvcRequestBuilders.get("/account/details/read/all")
                        .param("ids", id1, id2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


}
