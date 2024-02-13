package com.bank.antifraud.service.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionReturnerTest {
    @Test
    @DisplayName("Проверка генерации исключения EntityNotFoundException, позитивный сценарий")
    public void getEntityNotFoundExceptionPositiveTest() {
        ExceptionReturner exceptionReturner = new ExceptionReturner();
        String message = "Entity not found";
        EntityNotFoundException exception = exceptionReturner.getEntityNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    @DisplayName("Проверка генерации исключения EntityNotFoundException, негативный сценарий")
    public void getEntityNotFoundExceptionNegativeTest() {
        ExceptionReturner exceptionReturner = new ExceptionReturner();
        String message = "Entity found";
        EntityNotFoundException exception = exceptionReturner.getEntityNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }
}
