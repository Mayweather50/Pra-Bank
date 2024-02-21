package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.service.CertificateService;
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
class CertificateControllerTest {
    @Mock
    private CertificateService certificateService;
    @InjectMocks
    private CertificateController certificateController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(certificateController).build();
        objectMapper = new ObjectMapper();}

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        CertificateDto certificateDto = getCertificateDto();
        when(certificateService.findById(1L)).thenReturn(certificateDto);

        mockMvc.perform(get("/certificate/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.photoCertificate[0]").value(51))
                .andExpect(jsonPath("$.photoCertificate[1]").value(50))

                .andExpect(jsonPath("$.bankDetails.id").value(1L))
                .andExpect(jsonPath("$.bankDetails.bik").value(2L))
                .andExpect(jsonPath("$.bankDetails.inn").value(3L))
                .andExpect(jsonPath("$.bankDetails.kpp").value(4L))
                .andExpect(jsonPath("$.bankDetails.corAccount").value(BigDecimal.ONE))
                .andExpect(jsonPath("$.bankDetails.city").value("Тест Улица"))
                .andExpect(jsonPath("$.bankDetails.jointStockCompany").
                        value("Тест Акционерная Компания"))
                .andExpect(jsonPath("$.bankDetails.name").value("Тест Название"));

        verify(certificateService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("чтение по несуществующему  id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws Exception {
        Long id = 2L;
        mockMvc.perform(get("/certificate/{id}", id))
                .andExpect(jsonPath("$").doesNotHaveJsonPath());
    }

    @Test
    @DisplayName("чтение по листу ids, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<CertificateDto> branchDtoList = List.of(getCertificateDto(), getCertificateDto2(), getCertificateDto3());
        List<Long> ids = List.of(1L, 2L, 3L);

        when(certificateService.findAllById(ids)).thenReturn(branchDtoList);

        mockMvc.perform(get("/certificate/read/all", 1L)
                        .param("ids", String.valueOf(1L), String.valueOf(2L), String.valueOf(3L)))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].photoCertificate[0]").value(51))
                .andExpect(jsonPath("$[0].photoCertificate[1]").value(50))

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
                .andExpect(jsonPath("$[1].photoCertificate[0]").value(51))
                .andExpect(jsonPath("$[1].photoCertificate[1]").value(50))

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
                .andExpect(jsonPath("$[2].photoCertificate[0]").value(51))
                .andExpect(jsonPath("$[2].photoCertificate[1]").value(50))

                .andExpect(jsonPath("$[2].bankDetails.id").value(1L))
                .andExpect(jsonPath("$[2].bankDetails.bik").value(2L))
                .andExpect(jsonPath("$[2].bankDetails.inn").value(3L))
                .andExpect(jsonPath("$[2].bankDetails.kpp").value(4L))
                .andExpect(jsonPath("$[2].bankDetails.corAccount").value(BigDecimal.TEN))
                .andExpect(jsonPath("$[2].bankDetails.city").value("Тест Улица 3"))
                .andExpect(jsonPath("$[2].bankDetails.jointStockCompany")
                        .value("Тест Акционерная Компания 3"))
                .andExpect(jsonPath("$[2].bankDetails.name").value("Тест Название 3"));

        verify(certificateService, times(1)).findAllById(ids);
    }

    @Test
    @DisplayName("Чтение без передачи ids, Негативный сценарий")
    void readAllNoIdGivenNegativeTest() throws Exception {
        mockMvc.perform(get("/certificate/read/all"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Создание сертификата, позитивный cценарий")
    void createPositiveTest() throws Exception {
        CertificateDto certificateDto = getCertificateDto();
        String certificateJson = objectMapper.writeValueAsString(certificateDto);

        mockMvc.perform(post("/certificate/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(certificateJson))
                .andExpect(status().isOk());

        verify(certificateService, times(1)).create(certificateDto);
    }

    @Test
    @DisplayName("Создание сертификата без передачи сущности, негативный cценарий")
    void createNoEntityGivenNegativeTest() throws Exception {
        mockMvc.perform(post("/certificate/create"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Обновление сертификата, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        CertificateDto certificateDto = getCertificateDto();
        certificateDto.setId(2L);
        String branchJson = objectMapper.writeValueAsString(certificateDto);

        when(certificateService.update(1L, certificateDto)).thenReturn(getCertificateDto());

        mockMvc.perform(put("/certificate/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(branchJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.photoCertificate[0]").value(51))
                .andExpect(jsonPath("$.photoCertificate[1]").value(50))

                .andExpect(jsonPath("$.bankDetails.id").value(1L))
                .andExpect(jsonPath("$.bankDetails.bik").value(2L))
                .andExpect(jsonPath("$.bankDetails.inn").value(3L))
                .andExpect(jsonPath("$.bankDetails.kpp").value(4L))
                .andExpect(jsonPath("$.bankDetails.corAccount").value(BigDecimal.ONE))
                .andExpect(jsonPath("$.bankDetails.city").value("Тест Улица"))
                .andExpect(jsonPath("$.bankDetails.jointStockCompany")
                        .value("Тест Акционерная Компания"))
                .andExpect(jsonPath("$.bankDetails.name").value("Тест Название"));

        verify(certificateService, times(1)).update(1L, certificateDto);
    }

    @Test
    @DisplayName("Обновление сертификата без передачи сущности, негативный сценарий")
    void updateNoEntityGivenNegativeTest() throws Exception {
        mockMvc.perform(post("/certificate/update/{id}", 2L))
                .andExpect(status().is4xxClientError());
    }

    CertificateDto getCertificateDto() {
        CertificateDto certificateDto = new CertificateDto();
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(1L);
        bankDetailsDto.setBik(2L);
        bankDetailsDto.setInn(3L);
        bankDetailsDto.setKpp(4L);
        bankDetailsDto.setCorAccount(BigDecimal.ONE);
        bankDetailsDto.setCity("Тест Улица");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания");
        bankDetailsDto.setName("Тест Название");

        certificateDto.setId(1L);
        certificateDto.setPhotoCertificate(new Byte[]{51, 50});
        certificateDto.setBankDetails(bankDetailsDto);
        return certificateDto;
    }

    CertificateDto getCertificateDto2() {
        CertificateDto certificateDto = new CertificateDto();
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(1L);
        bankDetailsDto.setBik(2L);
        bankDetailsDto.setInn(3L);
        bankDetailsDto.setKpp(4L);
        bankDetailsDto.setCorAccount(BigDecimal.ZERO);
        bankDetailsDto.setCity("Тест Улица 2");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания 2");
        bankDetailsDto.setName("Тест Название 2");

        certificateDto.setId(2L);
        certificateDto.setPhotoCertificate(new Byte[]{51, 50});
        certificateDto.setBankDetails(bankDetailsDto);
        return certificateDto;
    }

    CertificateDto getCertificateDto3() {
        CertificateDto certificateDto = new CertificateDto();
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(1L);
        bankDetailsDto.setBik(2L);
        bankDetailsDto.setInn(3L);
        bankDetailsDto.setKpp(4L);
        bankDetailsDto.setCorAccount(BigDecimal.TEN);
        bankDetailsDto.setCity("Тест Улица 3");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания 3");
        bankDetailsDto.setName("Тест Название 3");

        certificateDto.setId(3L);
        certificateDto.setPhotoCertificate(new Byte[]{51, 50});
        certificateDto.setBankDetails(bankDetailsDto);
        return certificateDto;
    }
}