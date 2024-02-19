package controller;

import com.bank.profile.ProfileApplication;
import com.bank.profile.controller.RegistrationController;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.RegistrationService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(RegistrationController.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ProfileApplication.class)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        Long id = 1L;
        RegistrationDto expectedRegistrationDto = new RegistrationDto();
        expectedRegistrationDto.setId(id);

        when(registrationService.findById(id)).thenReturn(expectedRegistrationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest () throws Exception  {
        Long id = null;

        when(registrationService.findById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Создание записи, позитивный сценарий")
    void createPositiveTest() throws Exception {
        RegistrationDto inputDto = new RegistrationDto();
        RegistrationDto outputDto = new RegistrationDto();
        outputDto.setId(1L);

        when(registrationService.save(inputDto)).thenReturn(outputDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/registration/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Создание записи с невалидными данными, негативный сценарий")
    void createNegativeTest() throws Exception {
        RegistrationDto inputDto = null;

        mockMvc.perform(MockMvcRequestBuilders.post("/registration/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Обновление записи, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        Long id = 1L;
        RegistrationDto inputDto = new RegistrationDto();
        RegistrationDto outputDto = new RegistrationDto();
        outputDto.setId(id);

        when(registrationService.update(id, inputDto)).thenReturn(outputDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/registration/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Обновление записи с невалидными данными, негативный сценарий")
    void updateNegativeTest() throws Exception {
        Long id = 1L;
        RegistrationDto inputDto = null;

        mockMvc.perform(MockMvcRequestBuilders.put("/registration/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Чтение всех записей по списку id, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<RegistrationDto> outputDtoList = new ArrayList<>();
        outputDtoList.add(new RegistrationDto());
        outputDtoList.get(0).setId(1L);
        outputDtoList.add(new RegistrationDto());
        outputDtoList.get(1).setId(2L);

        when(registrationService.findAllById(ids)).thenReturn(outputDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/registration/read/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("ids", "1", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L));
    }

    @Test
    @DisplayName("Чтение всех записей по пустому списку id, негативный сценарий")
    void readAllByIdNegativeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/registration/read/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("ids",""))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
}
