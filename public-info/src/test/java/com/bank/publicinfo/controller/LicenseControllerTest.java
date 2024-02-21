package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.LicenseDto;
import com.bank.publicinfo.service.LicenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LicenseControllerTest {
    @Mock
    private LicenseService licenseService;
    @InjectMocks
    private LicenseController licenseController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(licenseController).build();
        objectMapper = new ObjectMapper();}

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        LicenseDto licenseDto = getLicenseDto();
        when(licenseService.findById(1L)).thenReturn(licenseDto);

        mockMvc.perform(get("/license/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.photoLicense[0]").value(51))
                .andExpect(jsonPath("$.photoLicense[1]").value(50))

                .andExpect(jsonPath("$.bankDetails.id").value(1L))
                .andExpect(jsonPath("$.bankDetails.bik").value(2L))
                .andExpect(jsonPath("$.bankDetails.inn").value(3L))
                .andExpect(jsonPath("$.bankDetails.kpp").value(4L))
                .andExpect(jsonPath("$.bankDetails.corAccount").value(BigDecimal.ONE))
                .andExpect(jsonPath("$.bankDetails.city").value("Тест Улица"))
                .andExpect(jsonPath("$.bankDetails.jointStockCompany")
                        .value("Тест Акционерная Компания"))
                .andExpect(jsonPath("$.bankDetails.name").value("Тест Название"));

        verify(licenseService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("чтение по несуществующему  id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws Exception {
        Long id = 2L;
        mockMvc.perform(get("/license/{id}", id))
                .andExpect(jsonPath("$").doesNotHaveJsonPath());
    }

    @Test
    @DisplayName("чтение по листу ids, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<LicenseDto> licenseList = List.of(getLicenseDto(), getLicenseDto2(), getLicenseDto3());
        List<Long> ids = List.of(1L, 2L, 3L);

        when(licenseService.findAllById(ids)).thenReturn(licenseList);

        mockMvc.perform(get("/license/read/all", 1L)
                        .param("ids", String.valueOf(1L), String.valueOf(2L), String.valueOf(3L)))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].photoLicense[0]").value(51))
                .andExpect(jsonPath("$[0].photoLicense[1]").value(50))

                .andExpect(jsonPath("$[0].bankDetails.id").value(1L))
                .andExpect(jsonPath("$[0].bankDetails.bik").value(2L))
                .andExpect(jsonPath("$[0].bankDetails.inn").value(3L))
                .andExpect(jsonPath("$[0].bankDetails.kpp").value(4L))
                .andExpect(jsonPath("$[0].bankDetails.corAccount").value(BigDecimal.ONE))
                .andExpect(jsonPath("$[0].bankDetails.city").value("Тест Улица"))
                .andExpect(jsonPath("$[0].bankDetails.jointStockCompany")
                        .value("Тест Акционерная Компания"))
                .andExpect(jsonPath("$[0].bankDetails.name").value("Тест Название"))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].photoLicense[0]").value(51))
                .andExpect(jsonPath("$[1].photoLicense[1]").value(50))

                .andExpect(jsonPath("$[1].bankDetails.id").value(1L))
                .andExpect(jsonPath("$[1].bankDetails.bik").value(2L))
                .andExpect(jsonPath("$[1].bankDetails.inn").value(3L))
                .andExpect(jsonPath("$[1].bankDetails.kpp").value(4L))
                .andExpect(jsonPath("$[1].bankDetails.corAccount").value(BigDecimal.ZERO))
                .andExpect(jsonPath("$[1].bankDetails.city").value("Тест Улица 2"))
                .andExpect(jsonPath("$[1].bankDetails.jointStockCompany")
                        .value("Тест Акционерная Компания 2"))
                .andExpect(jsonPath("$[1].bankDetails.name").value("Тест Название 2"))

                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].photoLicense[0]").value(51))
                .andExpect(jsonPath("$[2].photoLicense[1]").value(50))

                .andExpect(jsonPath("$[2].bankDetails.id").value(1L))
                .andExpect(jsonPath("$[2].bankDetails.bik").value(2L))
                .andExpect(jsonPath("$[2].bankDetails.inn").value(3L))
                .andExpect(jsonPath("$[2].bankDetails.kpp").value(4L))
                .andExpect(jsonPath("$[2].bankDetails.corAccount").value(BigDecimal.TEN))
                .andExpect(jsonPath("$[2].bankDetails.city").value("Тест Улица 3"))
                .andExpect(jsonPath("$[2].bankDetails.jointStockCompany")
                        .value("Тест Акционерная Компания 3"))
                .andExpect(jsonPath("$[2].bankDetails.name").value("Тест Название 3"));

        verify(licenseService, times(1)).findAllById(ids);
    }

    @Test
    @DisplayName("Чтение без передачи ids, Негативный сценарий")
    void readAllNoIdGivenNegativeTest() throws Exception {
        mockMvc.perform(get("/license/read/all"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Создание лицензии, позитивный сценарий")
    void createPositiveTest() throws Exception {
        LicenseDto licenseDto = getLicenseDto();
        String certificateJson = objectMapper.writeValueAsString(licenseDto);

        mockMvc.perform(post("/license/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(certificateJson))
                .andExpect(status().isOk());

        verify(licenseService, times(1)).create(licenseDto);
    }

    @Test
    @DisplayName("Создание лицензии без передачи сущности, негативный сценарий")
    void createNoEntityGivenNegativeTest() throws Exception {
        mockMvc.perform(post("/license/create"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Обновление лицензии, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        LicenseDto certificateDto = getLicenseDto();
        certificateDto.setId(2L);
        String branchJson = objectMapper.writeValueAsString(certificateDto);

        when(licenseService.update(1L, certificateDto)).thenReturn(getLicenseDto());

        mockMvc.perform(put("/license/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(branchJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.photoLicense[0]").value(51))
                .andExpect(jsonPath("$.photoLicense[1]").value(50))

                .andExpect(jsonPath("$.bankDetails.id").value(1L))
                .andExpect(jsonPath("$.bankDetails.bik").value(2L))
                .andExpect(jsonPath("$.bankDetails.inn").value(3L))
                .andExpect(jsonPath("$.bankDetails.kpp").value(4L))
                .andExpect(jsonPath("$.bankDetails.corAccount").value(BigDecimal.ONE))
                .andExpect(jsonPath("$.bankDetails.city").value("Тест Улица"))
                .andExpect(jsonPath("$.bankDetails.jointStockCompany")
                        .value("Тест Акционерная Компания"))
                .andExpect(jsonPath("$.bankDetails.name").value("Тест Название"));

        verify(licenseService, times(1)).update(1L, certificateDto);
    }

    @Test
    @DisplayName("Обновление лицензии без передачи сущности, негативный сценарий")
    void updateNoEntityGivenNegativeTest() throws Exception {
        mockMvc.perform(post("/license/update/{id}", 2L))
                .andExpect(status().is4xxClientError());
    }

    LicenseDto getLicenseDto() {
        LicenseDto licenseDto = new  LicenseDto();
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(1L);
        bankDetailsDto.setBik(2L);
        bankDetailsDto.setInn(3L);
        bankDetailsDto.setKpp(4L);
        bankDetailsDto.setCorAccount(BigDecimal.ONE);
        bankDetailsDto.setCity("Тест Улица");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания");
        bankDetailsDto.setName("Тест Название");

        licenseDto.setId(1L);
        licenseDto.setPhotoLicense(new Byte[]{51, 50});
        licenseDto.setBankDetails(bankDetailsDto);
        return licenseDto;
    }

    LicenseDto getLicenseDto2() {
        LicenseDto licenseDto = new  LicenseDto();
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(1L);
        bankDetailsDto.setBik(2L);
        bankDetailsDto.setInn(3L);
        bankDetailsDto.setKpp(4L);
        bankDetailsDto.setCorAccount(BigDecimal.ZERO);
        bankDetailsDto.setCity("Тест Улица 2");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания 2");
        bankDetailsDto.setName("Тест Название 2");

        licenseDto.setId(2L);
        licenseDto.setPhotoLicense(new Byte[]{51, 50});
        licenseDto.setBankDetails(bankDetailsDto);
        return licenseDto;
    }

    LicenseDto getLicenseDto3() {
        LicenseDto licenseDto = new  LicenseDto();
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(1L);
        bankDetailsDto.setBik(2L);
        bankDetailsDto.setInn(3L);
        bankDetailsDto.setKpp(4L);
        bankDetailsDto.setCorAccount(BigDecimal.TEN);
        bankDetailsDto.setCity("Тест Улица 3");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания 3");
        bankDetailsDto.setName("Тест Название 3");

        licenseDto.setId(3L);
        licenseDto.setPhotoLicense(new Byte[]{51, 50});
        licenseDto.setBankDetails(bankDetailsDto);
        return licenseDto;
    }
}