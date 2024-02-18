package controller;

import com.bank.profile.ProfileApplication;
import com.bank.profile.controller.ProfileController;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.service.ProfileService;
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

@WebMvcTest(ProfileController.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ProfileApplication.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        Long id = 1L;
        ProfileDto expectedProfileDto = new ProfileDto();
        expectedProfileDto.setId(id);

        when(profileService.findById(id)).thenReturn(expectedProfileDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest () throws Exception  {
        Long id = null;

        when(profileService.findById(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Создание записи, позитивный сценарий")
    void createPositiveTest() throws Exception {
        ProfileDto inputDto = new ProfileDto();
        ProfileDto outputDto = new ProfileDto();
        outputDto.setId(1L);

        when(profileService.save(inputDto)).thenReturn(outputDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Создание записи с невалидными данными, негативный сценарий")
    void createNegativeTest() throws Exception {
        ProfileDto inputDto = null;

        mockMvc.perform(MockMvcRequestBuilders.post("/profile/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Обновление записи, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        Long id = 1L;
        ProfileDto inputDto = new ProfileDto();
        ProfileDto outputDto = new ProfileDto();
        outputDto.setId(id);

        when(profileService.update(id, inputDto)).thenReturn(outputDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/profile/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("Обновление записи с невалидными данными, негативный сценарий")
    void updateNegativeTest() throws Exception {
        Long id = 1L;
        ProfileDto inputDto = null;

        mockMvc.perform(MockMvcRequestBuilders.put("/profile/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Чтение всех записей по списку id, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<ProfileDto> outputDtoList = new ArrayList<>();
        outputDtoList.add(new ProfileDto());
        outputDtoList.get(0).setId(1L);
        outputDtoList.add(new ProfileDto());
        outputDtoList.get(1).setId(2L);

        when(profileService.findAllById(ids)).thenReturn(outputDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/read/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("ids", "1", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L));
    }

    @Test
    @DisplayName("Чтение всех записей по пустому списку id, негативный сценарий")
    void readAllByIdNegativeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/profile/read/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
