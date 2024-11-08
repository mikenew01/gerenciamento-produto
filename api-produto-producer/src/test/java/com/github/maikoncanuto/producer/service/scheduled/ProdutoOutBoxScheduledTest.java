package com.github.maikoncanuto.producer.service.scheduled;

import com.github.maikoncanuto.producer.domain.enuns.NomeEvento;
import com.github.maikoncanuto.producer.domain.enuns.StatusEnvio;
import com.github.maikoncanuto.producer.domain.entity.ProdutoOutBox;
import com.github.maikoncanuto.producer.domain.repository.ProdutoOutBoxRepository;
import com.github.maikoncanuto.producer.service.producer.ProdutoProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.github.maikoncanuto.producer.domain.enuns.NomeEvento.*;
import static com.github.maikoncanuto.producer.domain.enuns.StatusEnvio.*;
import static org.mockito.Mockito.*;

class ProdutoOutBoxScheduledTest {

    @Mock
    private ProdutoOutBoxRepository produtoOutBoxRepository;

    @Mock
    private ProdutoProducer produtoProducer;

    @InjectMocks
    private ProdutoOutBoxScheduled produtoOutBoxScheduled;

    private ProdutoOutBox produtoCriacao;
    private ProdutoOutBox produtoAtualizacao;
    private ProdutoOutBox produtoRemocao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        produtoCriacao = new ProdutoOutBox(1L, "traceId1", PRODUTO_CRIACAO, PENDENTE, null, "{\"nome\": \"Produto 1\"}");
        produtoAtualizacao = new ProdutoOutBox(2L, "traceId2", PRODUTO_ATUALIZACAO, PENDENTE, null, "{\"nome\": \"Produto 2\"}");
        produtoRemocao = new ProdutoOutBox(3L, "traceId3", PRODUTO_REMOCAO, PENDENTE, null, "{\"nome\": \"Produto 3\"}");
    }

    @Test
    void testProcessarProdutosPendentes() throws Exception {
        when(produtoOutBoxRepository.findByStatusEnvio(PENDENTE))
                .thenReturn(List.of(produtoCriacao, produtoAtualizacao, produtoRemocao));

        produtoOutBoxScheduled.processarProdutosPendentes();

        verify(produtoProducer).enviarCriacaoMensagem("traceId1", "{\"nome\": \"Produto 1\"}");
        verify(produtoProducer).enviarAtualizacaoMensagem("traceId2", "{\"nome\": \"Produto 2\"}");
        verify(produtoProducer).enviarRemoverMensagem("traceId3", "{\"nome\": \"Produto 3\"}");

        verify(produtoOutBoxRepository, times(3)).saveAndFlush(any(ProdutoOutBox.class));
    }

    @Test
    void testProcessarProdutosQueFalharam() throws Exception {
        produtoCriacao.setStatusEnvio(FALHOU);
        when(produtoOutBoxRepository.findByStatusEnvio(FALHOU)).thenReturn(List.of(produtoCriacao));

        produtoOutBoxScheduled.processarProdutosQueFalharam();

        verify(produtoProducer).enviarCriacaoMensagem("traceId1", "{\"nome\": \"Produto 1\"}");
        verify(produtoOutBoxRepository).saveAndFlush(any(ProdutoOutBox.class));
    }

}
