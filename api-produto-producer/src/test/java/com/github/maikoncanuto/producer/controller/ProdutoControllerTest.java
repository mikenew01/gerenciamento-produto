package com.github.maikoncanuto.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.maikoncanuto.producer.domain.dto.RequestProdutoOutBox;
import com.github.maikoncanuto.producer.domain.dto.Response;
import com.github.maikoncanuto.producer.domain.entity.ProdutoOutBox;
import com.github.maikoncanuto.producer.service.ProdutoOutBoxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    @Mock
    private ProdutoOutBoxService produtoOutBoxService;

    @InjectMocks
    private ProdutoController produtoController;

    private RequestProdutoOutBox requestProdutoOutBox;
    private ProdutoOutBox produtoOutBox;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestProdutoOutBox = new RequestProdutoOutBox(
                1L, "sample-trace-id", "Produto Teste", 10, "Descrição Teste", 100.0);

        produtoOutBox = new ProdutoOutBox(
                1L, "sample-trace-id", null, null, null, "Produto Teste");
    }

    @Test
    void testCriarProduto() throws JsonProcessingException {
        when(produtoOutBoxService.criarEventoProdutoCriacao(requestProdutoOutBox)).thenReturn(produtoOutBox);

        ResponseEntity<Response> response = produtoController.criarProduto(requestProdutoOutBox);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(produtoOutBox, response.getBody().data());
        verify(produtoOutBoxService, times(1)).criarEventoProdutoCriacao(requestProdutoOutBox);
    }

    @Test
    void testAtualizarProduto() throws JsonProcessingException {
        when(produtoOutBoxService.criarEventoProdutoAtualizacao(requestProdutoOutBox)).thenReturn(produtoOutBox);

        ResponseEntity<Response> response = produtoController.atualizarProduto("sample-trace-id", requestProdutoOutBox);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(produtoOutBox, response.getBody().data());
        verify(produtoOutBoxService, times(1)).criarEventoProdutoAtualizacao(requestProdutoOutBox);
    }

    @Test
    void testDeletarProduto() throws JsonProcessingException {
        when(produtoOutBoxService.criarEventoProdutoDeletar("sample-trace-id")).thenReturn(produtoOutBox);

        ResponseEntity<Response> response = produtoController.deletarProduto("sample-trace-id");

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(produtoOutBox, response.getBody().data());
        verify(produtoOutBoxService, times(1)).criarEventoProdutoDeletar("sample-trace-id");
    }
}
