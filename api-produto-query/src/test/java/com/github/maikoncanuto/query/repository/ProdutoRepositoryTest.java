package com.github.maikoncanuto.query.repository;

import com.github.maikoncanuto.query.domain.entity.Mensagem;
import com.github.maikoncanuto.query.domain.entity.Produto;
import com.github.maikoncanuto.query.domain.entity.ProdutoResposta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoRepositoryTest {

    @Mock
    private ProdutoRepository produtoRepository;

    private ProdutoResposta produtoResposta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Produto produto = new Produto();
        produto.setTraceId("sample-trace-id");

        Mensagem mensagem = new Mensagem("Error Code", "Error Message");

        produtoResposta = new ProdutoResposta("1", produto, List.of(mensagem), true);
    }

    @Test
    void testSaveProdutoResposta() {
        when(produtoRepository.save(produtoResposta)).thenReturn(produtoResposta);

        ProdutoResposta savedProdutoResposta = produtoRepository.save(produtoResposta);

        assertEquals(produtoResposta.getId(), savedProdutoResposta.getId());
        assertEquals(produtoResposta.getData(), savedProdutoResposta.getData());
        assertEquals(produtoResposta.getErrors(), savedProdutoResposta.getErrors());
        assertTrue(savedProdutoResposta.getAtivo());
        verify(produtoRepository, times(1)).save(produtoResposta);
    }

    @Test
    void testFindById() {
        when(produtoRepository.findById("1")).thenReturn(Optional.of(produtoResposta));

        Optional<ProdutoResposta> foundProdutoResposta = produtoRepository.findById("1");

        assertTrue(foundProdutoResposta.isPresent());
        assertEquals(produtoResposta.getId(), foundProdutoResposta.get().getId());
        verify(produtoRepository, times(1)).findById("1");
    }

    @Test
    void testFindByDataTraceId() {
        when(produtoRepository.findByData_TraceId("sample-trace-id")).thenReturn(Optional.of(produtoResposta));

        Optional<ProdutoResposta> foundProdutoResposta = produtoRepository.findByData_TraceId("sample-trace-id");

        assertTrue(foundProdutoResposta.isPresent());
        assertEquals(produtoResposta.getData().getTraceId(), foundProdutoResposta.get().getData().getTraceId());
        verify(produtoRepository, times(1)).findByData_TraceId("sample-trace-id");
    }

    @Test
    void testDeleteById() {
        doNothing().when(produtoRepository).deleteById("1");

        produtoRepository.deleteById("1");

        verify(produtoRepository, times(1)).deleteById("1");
    }
}
