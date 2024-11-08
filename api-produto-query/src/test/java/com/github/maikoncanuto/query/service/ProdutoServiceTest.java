package com.github.maikoncanuto.query.service;

import com.github.maikoncanuto.query.domain.entity.Produto;
import com.github.maikoncanuto.query.domain.entity.ProdutoResposta;
import com.github.maikoncanuto.query.domain.exception.ProdutoNaoEncontratoException;
import com.github.maikoncanuto.query.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private Logger log;

    @InjectMocks
    private ProdutoService produtoService;

    private ProdutoResposta produtoResposta;

    @BeforeEach
    void setUp() {
        Produto produto = new Produto();
        produto.setTraceId("sample-trace-id");
        produtoResposta = new ProdutoResposta("1", produto, null, true);
    }

    @Test
    void testBuscarProdutoPorTraceId_Success() {
        when(produtoRepository.findByData_TraceId("sample-trace-id")).thenReturn(Optional.of(produtoResposta));

        ProdutoResposta result = produtoService.buscarProdutoPorTraceId("sample-trace-id");

        assertNotNull(result);
        assertEquals("sample-trace-id", result.getData().getTraceId());
        verify(produtoRepository, times(1)).findByData_TraceId("sample-trace-id");
    }

    @Test
    void testBuscarProdutoPorTraceId_NotFound() {
        when(produtoRepository.findByData_TraceId("invalid-trace-id")).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontratoException.class, () -> produtoService.buscarProdutoPorTraceId("invalid-trace-id"));
        verify(produtoRepository, times(1)).findByData_TraceId("invalid-trace-id");
    }

    @Test
    void testBuscarTodosProdutos() {
        List<ProdutoResposta> produtos = List.of(produtoResposta);
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<ProdutoResposta> result = produtoService.buscarTodosProdutos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("sample-trace-id", result.get(0).getData().getTraceId());
        verify(produtoRepository, times(1)).findAll();
    }
}
