package com.github.maikoncanuto.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maikoncanuto.producer.domain.dto.RequestProdutoOutBox;
import com.github.maikoncanuto.producer.domain.entity.ProdutoOutBox;
import com.github.maikoncanuto.producer.domain.enuns.NomeEvento;
import com.github.maikoncanuto.producer.domain.repository.ProdutoOutBoxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoOutBoxServiceTest {

    @Mock
    private ProdutoOutBoxRepository produtoOutBoxRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProdutoOutBoxService produtoOutBoxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarEventoProdutoCriacao() throws JsonProcessingException {
        RequestProdutoOutBox request = new RequestProdutoOutBox(1L, "trace-123", "Produto Teste", 10, "Descricao Teste", 99.99);
        ProdutoOutBox produtoOutBox = new ProdutoOutBox();
        produtoOutBox.setTraceId(request.traceId());
        produtoOutBox.setNomeEvento(NomeEvento.PRODUTO_CRIACAO);

        when(objectMapper.writeValueAsString(request)).thenReturn("{\"data\":\"produto\"}");
        when(produtoOutBoxRepository.saveAndFlush(any(ProdutoOutBox.class))).thenReturn(produtoOutBox);

        ProdutoOutBox result = produtoOutBoxService.criarEventoProdutoCriacao(request);

        assertNotNull(result);
        assertEquals("trace-123", result.getTraceId());
        assertEquals(NomeEvento.PRODUTO_CRIACAO, result.getNomeEvento());
        verify(produtoOutBoxRepository, times(1)).saveAndFlush(any(ProdutoOutBox.class));
    }

    @Test
    void testCriarEventoProdutoAtualizacao() throws JsonProcessingException {
        RequestProdutoOutBox request = new RequestProdutoOutBox(1L, "trace-123", "Produto Teste", 10, "Descricao Teste", 99.99);
        ProdutoOutBox produtoOutBox = new ProdutoOutBox();
        produtoOutBox.setTraceId(request.traceId());
        produtoOutBox.setNomeEvento(NomeEvento.PRODUTO_ATUALIZACAO);

        when(objectMapper.writeValueAsString(request)).thenReturn("{\"data\":\"produto\"}");
        when(produtoOutBoxRepository.saveAndFlush(any(ProdutoOutBox.class))).thenReturn(produtoOutBox);

        ProdutoOutBox result = produtoOutBoxService.criarEventoProdutoAtualizacao(request);

        assertNotNull(result);
        assertEquals("trace-123", result.getTraceId());
        assertEquals(NomeEvento.PRODUTO_ATUALIZACAO, result.getNomeEvento());
        verify(produtoOutBoxRepository, times(1)).saveAndFlush(any(ProdutoOutBox.class));
    }

    @Test
    void testCriarEventoProdutoDeletar() throws JsonProcessingException {
        String traceId = "trace-123";
        ProdutoOutBox produtoOutBox = new ProdutoOutBox();
        produtoOutBox.setTraceId(traceId);
        produtoOutBox.setNomeEvento(NomeEvento.PRODUTO_REMOCAO);

        when(objectMapper.writeValueAsString(any(RequestProdutoOutBox.class))).thenReturn("{\"data\":\"produto\"}");
        when(produtoOutBoxRepository.saveAndFlush(any(ProdutoOutBox.class))).thenReturn(produtoOutBox);

        ProdutoOutBox result = produtoOutBoxService.criarEventoProdutoDeletar(traceId);

        assertNotNull(result);
        assertEquals("trace-123", result.getTraceId());
        assertEquals(NomeEvento.PRODUTO_REMOCAO, result.getNomeEvento());
        verify(produtoOutBoxRepository, times(1)).saveAndFlush(any(ProdutoOutBox.class));
    }
}
