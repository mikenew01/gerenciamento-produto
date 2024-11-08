package com.github.maikoncanuto.query.controller.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.maikoncanuto.query.domain.dto.Response;
import com.github.maikoncanuto.query.domain.entity.Mensagem;
import com.github.maikoncanuto.query.domain.exception.ProdutoNaoEncontratoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleProdutoNaoEncontradoException() {
        // Configuração
        String errorMessage = "Produto não encontrado";
        ProdutoNaoEncontratoException exception = new ProdutoNaoEncontratoException(errorMessage);

        // Execução
        ResponseEntity<Response> response = globalExceptionHandler.handleProdutoNaoEncontradoException(exception);

        // Verificações
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().errors().size());
        Mensagem mensagem = response.getBody().errors().get(0);
        assertEquals(String.valueOf(HttpStatus.NOT_FOUND), mensagem.code());
        assertEquals(errorMessage, mensagem.mensagem());
    }

    @Test
    void testHandleJsonProcessingException() {
        // Configuração
        String errorMessage = "Erro ao processar JSON";
        JsonProcessingException exception = new JsonProcessingException(errorMessage) {
        };

        // Execução
        ResponseEntity<Response> response = globalExceptionHandler.handleJsonProcessingException(exception);

        // Verificações
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().errors().size());
        Mensagem mensagem = response.getBody().errors().get(0);
        assertEquals(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), mensagem.code());
        assertEquals(errorMessage, mensagem.mensagem());
    }
}
