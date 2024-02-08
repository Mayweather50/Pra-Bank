package com.bank.authorization.controller;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("создание пользователя по заполненным данным, позитивный сценарий")
    void createUserByUserDataPositiveTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setPassword("1");
        userDto.setRole("a");
        userDto.setProfileId(2L);
        ResponseEntity<UserDto> expectedUserDto = new ResponseEntity<>(userDto, HttpStatus.CREATED);

        when(userService.save(userDto)).thenReturn(expectedUserDto.getBody());

        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(userDto.getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(userDto.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profileId").value(userDto.getProfileId()));
    }

    @Test
    @DisplayName("создание пользователя с пустыми данными, негативный сценарий")
    void createUserByEmptyUserDataNegativeTest() throws Exception {
        UserDto userDto = null;

        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        Long id = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setPassword("1");
        userDto.setRole("a");
        userDto.setProfileId(2L);
        ResponseEntity<UserDto> expectedUserDto = new ResponseEntity<>(userDto, HttpStatus.OK);

        when(userService.findById(id)).thenReturn(expectedUserDto.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(userDto.getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(userDto.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profileId").value(userDto.getProfileId()));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest () throws Exception {
        Long id = null;
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setPassword("1");
        userDto.setRole("a");
        userDto.setProfileId(2L);

        mockMvc.perform(MockMvcRequestBuilders.get("/read/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName("обновление пользователя по id, позитивный сценарий")
    void updateUserByUserDataPositiveTest() throws Exception {
        Long id = 1L;
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setPassword("1");
        userDto.setRole("a");
        userDto.setProfileId(2L);
        ResponseEntity<UserDto> expectedUserDto = new ResponseEntity<>(userDto, HttpStatus.OK);

        when(userService.update(id, userDto)).thenReturn(expectedUserDto.getBody());

        mockMvc.perform(MockMvcRequestBuilders.put("/{id}/update", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role").value(userDto.getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(userDto.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profileId").value(userDto.getProfileId()));
    }

    @Test
    @DisplayName("обновление пользователя по несуществующему id, негативный сценарий")
    void updateUserByEmptyUserDataNegativeTest() throws Exception {
        Long id = null;
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setPassword(null);
        userDto.setRole(null);
        userDto.setProfileId(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/{id}/update", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    @DisplayName("чтение всех пользователей по ids, позитивный сценарий")
    void readAllByIdsPositiveTest() throws Exception {
        Long id1 = 1L;
        Long id2 = 2L;
        List<Long> ids = new ArrayList<>();
        ids.add(id1);
        ids.add(id2);
        UserDto userDto1 = new UserDto();
        userDto1.setId(id1);
        UserDto userDto2 = new UserDto();
        userDto2.setId(id2);
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto1);
        userDtos.add(userDto2);
        ResponseEntity<List<UserDto>> expectedUserDto = new ResponseEntity<>(userDtos, HttpStatus.OK);

        when(userService.findAllByIds(ids)).thenReturn(expectedUserDto.getBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/read/all")
                        .param("ids", String.valueOf(id1), String.valueOf(id2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(userDto1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].role").value(userDto1.getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password").value(userDto1.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].profileId").value(userDto1.getProfileId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(userDto2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].role").value(userDto2.getRole()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].password").value(userDto2.getPassword()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].profileId").value(userDto2.getProfileId()));
    }

    @Test
    @DisplayName("чтение всех пользователей по несуществующим ids, негативный сценарий")
    void readAllByNonExistIdsNegativeTest() throws Exception {
        Long id1 = null;
        Long id2 = null;
        List<Long> ids = new ArrayList<>();
        ids.add(id1);
        ids.add(id2);

        mockMvc.perform(MockMvcRequestBuilders.get("/read/all")
                        .param("ids", String.valueOf(id1), String.valueOf(id2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}