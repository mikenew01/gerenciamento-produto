package com.github.maikoncanuto.producer.controller.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.maikoncanuto.producer.domain.dto.Mensagem;
import com.github.maikoncanuto.producer.domain.dto.Response;
import com.github.maikoncanuto.producer.domain.exception.ProdutoNaoEncontratoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleProdutoNaoEncontradoException() {
        String errorMessage = "Produto não encontrado";
        ProdutoNaoEncontratoException exception = new ProdutoNaoEncontratoException(errorMessage);

        ResponseEntity<Response> responseEntity = globalExceptionHandler.handleProdutoNaoEncontradoException(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().errors().size());
        Mensagem mensagem = responseEntity.getBody().errors().get(0);
        assertEquals(String.valueOf(HttpStatus.NOT_FOUND), mensagem.code());
        assertEquals(errorMessage, mensagem.mensagem());
    }

    @Test
    void testHandleJsonProcessingException() {
        String errorMessage = "Erro ao processar JSON";
        JsonProcessingException exception = new JsonProcessingException(errorMessage) {};

        ResponseEntity<Response> responseEntity = globalExceptionHandler.handleJsonProcessingException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().errors().size());
        Mensagem mensagem = responseEntity.getBody().errors().get(0);
        assertEquals(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), mensagem.code());
        assertEquals(errorMessage, mensagem.mensagem());
    }
}
