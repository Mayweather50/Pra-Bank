package controller;

import com.bank.profile.ProfileApplication;
import com.bank.profile.controller.ActualRegistrationController;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.service.ActualRegistrationService;
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

@WebMvcTest(ActualRegistrationController.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ProfileApplication.class)
class ActualRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActualRegistrationService actualRegistrationService;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        Long id = 1L;
        ActualRegistrationDto expectedActualRegistrationDto = new ActualRegistrationDto();
        expectedActualRegistrationDto.setId(id);

        when(actualRegistrationService.findById(id)).thenReturn(expectedActualRegistrationDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/actual/registration/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest () throws Exception  {
        Long id = null;

        mockMvc.perform(MockMvcRequestBuilders.get("/actual/registration/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName("Создание записи, позитивный сценарий")
    void createPositiveTest() throws Exception {
        ActualRegistrationDto inputDto = new ActualRegistrationDto();
        ActualRegistrationDto outputDto = new ActualRegistrationDto();
        outputDto.setId(1L);

        when(actualRegistrationService.save(inputDto)).thenReturn(outputDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/actual/registration/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Создание записи с невалидными данными, негативный сценарий")
    void createNegativeTest() throws Exception {
        ActualRegistrationDto inputDto = null;

        mockMvc.perform(MockMvcRequestBuilders.post("/actual/registration/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Обновление записи, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        Long id = 1L;
        ActualRegistrationDto inputDto = new ActualRegistrationDto();
        ActualRegistrationDto outputDto = new ActualRegistrationDto();
        outputDto.setId(id);

        when(actualRegistrationService.update(id, inputDto)).thenReturn(outputDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/actual/registration/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Обновление записи с невалидными данными, негативный сценарий")
    void updateNegativeTest() throws Exception {
        Long id = 1L;
        ActualRegistrationDto inputDto = null;

        mockMvc.perform(MockMvcRequestBuilders.put("/actual/registration/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Чтение всех записей по списку id, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<ActualRegistrationDto> outputDtoList = new ArrayList<>();
        outputDtoList.add(new ActualRegistrationDto());
        outputDtoList.get(0).setId(1L);
        outputDtoList.add(new ActualRegistrationDto());
        outputDtoList.get(1).setId(2L);

        when(actualRegistrationService.findAllById(ids)).thenReturn(outputDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/actual/registration/read/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("ids", "1", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L));
    }

    @Test
    @DisplayName("Чтение всех записей по пустому списку id, негативный сценарий")
    void readAllByIdNegativeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/actual/registration/read/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("ids",""))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
}
