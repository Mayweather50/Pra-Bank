package com.bank.publicinfo.mapper;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.entity.CertificateEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CertificateMapperTest {

    @InjectMocks
    CertificateMapperImpl mapper;
    @Mock
    CertificateDto dto;
    @Mock
    CertificateEntity entity;

    @Test
    @DisplayName("Маппинг в Entity")
    void toEntityTest() {
        when(dto.getBankDetails()).thenReturn(getBankDetailsDto());
        when(dto.getPhotoCertificate()).thenReturn(new Byte[]{51, 50});

        entity = mapper.toEntity(dto);

        assertNull(entity.getId());
        assertEquals(2L, entity.getBankDetails().getBik());
        assertEquals(3L, entity.getBankDetails().getInn());
        assertEquals(4L, entity.getBankDetails().getKpp());
        assertEquals(BigDecimal.ONE, entity.getBankDetails().getCorAccount());
        assertEquals("Тест Улица", entity.getBankDetails().getCity());
        assertEquals("Тест Акционерная Компания", entity.getBankDetails().getJointStockCompany());
        assertEquals("Тест Название",entity.getBankDetails().getName());
        assertNull(entity.getId());
        assert(Arrays.equals(new Byte[]{51, 50}, entity.getPhotoCertificate()));
    }

    @Test
    @DisplayName("Маппинг в Entity, на вход подан null")
    void toEntityNullTest() {
        entity = mapper.toEntity(null);
        assertNull(entity);
    }

    @Test
    @DisplayName("Маппинг в Dto")
    void toDtoTest() {
        when(entity.getBankDetails()).thenReturn(getBankDetailsEntity());
        when(entity.getId()).thenReturn(1L);
        when(entity.getPhotoCertificate()).thenReturn(new Byte[]{51, 50});

        dto = mapper.toDto(entity);

        assertEquals(dto.getBankDetails().getId(),1L);
        assertEquals(dto.getBankDetails().getBik(), 2L);
        assertEquals(dto.getBankDetails().getInn(), 3L);
        assertEquals(dto.getBankDetails().getKpp(),4L);
        assertEquals(dto.getBankDetails().getCorAccount(),BigDecimal.ONE);
        assertEquals(dto.getBankDetails().getCity(),"Тест Улица");
        assertEquals(dto.getBankDetails().getJointStockCompany(),"Тест Акционерная Компания");
        assertEquals(dto.getBankDetails().getName(),"Тест Название");
        assertEquals(dto.getId(),1L);
        assert(Arrays.equals(new Byte[]{51, 50}, entity.getPhotoCertificate()));
    }

    @Test
    @DisplayName("Маппинг в Dto, на вход подан null")
    void toDtoNullTest() {
        dto = mapper.toDto(null);
        assertNull(dto);
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        dto = getDto();

        entity = getEntity();
        dto.getBankDetails().setInn(null);
        dto.getBankDetails().setBik(null);

        entity = mapper.mergeToEntity(dto, entity);

        assertEquals(entity.getId(), 1L);
        assertNull(entity.getBankDetails().getInn());
        assertNull(entity.getBankDetails().getBik());
        assertEquals(4L, entity.getBankDetails().getKpp());
        assertEquals(BigDecimal.ONE, entity.getBankDetails().getCorAccount());
        assertEquals("Тест Улица", entity.getBankDetails().getCity());
        assertEquals("Тест Акционерная Компания", entity.getBankDetails().getJointStockCompany());
        assertEquals("Тест Название",entity.getBankDetails().getName());
        assertEquals(1L, entity.getId());
        assert(Arrays.equals(new Byte[]{51, 50}, entity.getPhotoCertificate()));
    }

    @Test
    @DisplayName("Слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        dto = new CertificateDto();
        entity = getEntity();

        entity = mapper.mergeToEntity(dto, entity);

        assertNull(entity.getBankDetails());
        assertNotNull(entity.getId());
        assertNull(entity.getPhotoCertificate());
    }

    @Test
    @DisplayName("Маппинг в лист dto")
    void toDtoListTest() {
        List<CertificateDto> dtoList = mapper.toDtoList(List.of(getEntity(),getEntity2(), getEntity3()));

        assertEquals(dtoList, List.of(getDto(), getDto2(), getDto3()));
    }

    @Test
    @DisplayName("Маппинг в лист dto, на вход подан пустой лист")
    void toDtoListEmptyListTest() {
        List<CertificateDto> dtoList = mapper.toDtoList(Collections.emptyList());

        assertEquals(dtoList, Collections.emptyList());
    }

    CertificateDto getDto() {
        CertificateDto certificateDto = new CertificateDto();
        BankDetailsDto bankDetailsDto = getBankDetailsDto();

        certificateDto.setId(1L);
        certificateDto.setPhotoCertificate(new Byte[]{51, 50});
        certificateDto.setBankDetails(bankDetailsDto);
        return certificateDto;
    }

    CertificateDto getDto2() {
        CertificateDto certificateDto = new CertificateDto();
        BankDetailsDto bankDetailsDto = getBankDetailsDto2();

        certificateDto.setId(2L);
        certificateDto.setPhotoCertificate(new Byte[]{51, 50});
        certificateDto.setBankDetails(bankDetailsDto);
        return certificateDto;
    }

    CertificateDto getDto3() {
        CertificateDto certificateDto = new CertificateDto();
        BankDetailsDto bankDetailsDto = getBankDetailsDto3();

        certificateDto.setId(3L);
        certificateDto.setPhotoCertificate(new Byte[]{51, 50});
        certificateDto.setBankDetails(bankDetailsDto);
        return certificateDto;
    }


    BankDetailsDto getBankDetailsDto() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(1L);
        bankDetailsDto.setBik(2L);
        bankDetailsDto.setInn(3L);
        bankDetailsDto.setKpp(4L);
        bankDetailsDto.setCorAccount(BigDecimal.ONE);
        bankDetailsDto.setCity("Тест Улица");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания");
        bankDetailsDto.setName("Тест Название");
        return bankDetailsDto;
    }

    BankDetailsDto getBankDetailsDto2() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(11L);
        bankDetailsDto.setBik(22L);
        bankDetailsDto.setInn(33L);
        bankDetailsDto.setKpp(44L);
        bankDetailsDto.setCorAccount(BigDecimal.ZERO);
        bankDetailsDto.setCity("Тест Улица 2");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания 2");
        bankDetailsDto.setName("Тест Название 2");
        return bankDetailsDto;
    }

    BankDetailsDto getBankDetailsDto3() {
        BankDetailsDto bankDetailsDto = new BankDetailsDto();
        bankDetailsDto.setId(111L);
        bankDetailsDto.setBik(222L);
        bankDetailsDto.setInn(333L);
        bankDetailsDto.setKpp(444L);
        bankDetailsDto.setCorAccount(BigDecimal.TEN);
        bankDetailsDto.setCity("Тест Улица 3");
        bankDetailsDto.setJointStockCompany("Тест Акционерная Компания 3");
        bankDetailsDto.setName("Тест Название 3");
        return bankDetailsDto;
    }

    BankDetailsEntity getBankDetailsEntity() {
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity();
        bankDetailsEntity.setId(1L);
        bankDetailsEntity.setBik(2L);
        bankDetailsEntity.setInn(3L);
        bankDetailsEntity.setKpp(4L);
        bankDetailsEntity.setCorAccount(BigDecimal.ONE);
        bankDetailsEntity.setCity("Тест Улица");
        bankDetailsEntity.setJointStockCompany("Тест Акционерная Компания");
        bankDetailsEntity.setName("Тест Название");
        return bankDetailsEntity;
    }

    BankDetailsEntity getBankDetailsEntity2() {
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity();
        bankDetailsEntity.setId(11L);
        bankDetailsEntity.setBik(22L);
        bankDetailsEntity.setInn(33L);
        bankDetailsEntity.setKpp(44L);
        bankDetailsEntity.setCorAccount(BigDecimal.ZERO);
        bankDetailsEntity.setCity("Тест Улица 2");
        bankDetailsEntity.setJointStockCompany("Тест Акционерная Компания 2");
        bankDetailsEntity.setName("Тест Название 2");
        return bankDetailsEntity;
    }

    BankDetailsEntity getBankDetailsEntity3() {
        BankDetailsEntity bankDetailsEntity = new BankDetailsEntity();
        bankDetailsEntity.setId(111L);
        bankDetailsEntity.setBik(222L);
        bankDetailsEntity.setInn(333L);
        bankDetailsEntity.setKpp(444L);
        bankDetailsEntity.setCorAccount(BigDecimal.TEN);
        bankDetailsEntity.setCity("Тест Улица 3");
        bankDetailsEntity.setJointStockCompany("Тест Акционерная Компания 3");
        bankDetailsEntity.setName("Тест Название 3");
        return bankDetailsEntity;
    }

    CertificateEntity getEntity() {
        CertificateEntity certificateEntity = new CertificateEntity();
        BankDetailsEntity bankDetailsEntity = getBankDetailsEntity();

        certificateEntity.setId(1L);
        certificateEntity.setPhotoCertificate(new Byte[]{51, 50});
        certificateEntity.setBankDetails(bankDetailsEntity);
        return certificateEntity;
    }

    CertificateEntity getEntity2() {
        CertificateEntity certificateEntity = new CertificateEntity();
        BankDetailsEntity bankDetailsEntity = getBankDetailsEntity2();

        certificateEntity.setId(2L);
        certificateEntity.setPhotoCertificate(new Byte[]{51, 50});
        certificateEntity.setBankDetails(bankDetailsEntity);
        return certificateEntity;
    }

    CertificateEntity getEntity3() {
        CertificateEntity certificateEntity = new CertificateEntity();
        BankDetailsEntity bankDetailsEntity = getBankDetailsEntity3();

        certificateEntity.setId(3L);
        certificateEntity.setPhotoCertificate(new Byte[]{51, 50});
        certificateEntity.setBankDetails(bankDetailsEntity);
        return certificateEntity;
    }
}