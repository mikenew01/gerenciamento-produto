package com.github.maikoncanuto.query.controller;

import com.github.maikoncanuto.query.domain.entity.ProdutoResposta;
import com.github.maikoncanuto.query.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

class ProdutoControllerTest {

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private ProdutoController produtoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarTodos() {
        // Configuração do mock para retornar uma lista de produtos
        ProdutoResposta produtoResposta = new ProdutoResposta();
        produtoResposta.setId("1");
        List<ProdutoResposta> produtos = Collections.singletonList(produtoResposta);
        when(produtoService.buscarTodosProdutos()).thenReturn(produtos);

        // Execução
        ResponseEntity<List<ProdutoResposta>> response = produtoController.buscarTodos();

        // Verificações
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(produtoService).buscarTodosProdutos();
    }

    @Test
    void testBuscarProdutoPorTraceId() {
        // Configuração do mock para retornar um produto com traceId específico
        ProdutoResposta produtoResposta = new ProdutoResposta();
        produtoResposta.setId("1");
        when(produtoService.buscarProdutoPorTraceId(anyString())).thenReturn(produtoResposta);

        // Execução
        ResponseEntity<ProdutoResposta> response = produtoController.buscarProdutoPorTraceId("sample-trace-id");

        // Verificações
        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals("1", response.getBody().getId());
        verify(produtoService).buscarProdutoPorTraceId("sample-trace-id");
    }
}
