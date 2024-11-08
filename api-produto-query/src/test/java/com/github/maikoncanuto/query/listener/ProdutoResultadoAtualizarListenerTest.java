package com.github.maikoncanuto.query.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maikoncanuto.query.domain.entity.Produto;
import com.github.maikoncanuto.query.domain.entity.ProdutoErro;
import com.github.maikoncanuto.query.domain.entity.ProdutoResposta;
import com.github.maikoncanuto.query.repository.ProdutoErroRepository;
import com.github.maikoncanuto.query.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoResultadoAtualizarListenerTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoErroRepository produtoErroRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProdutoResultadoAtualizarListener produtoResultadoAtualizarListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @RabbitListener(queues = {"tp.produto.resultado.atualizar"})
    void testRun_MessageProcessedSuccessfully() throws JsonProcessingException {
        // Configuração do mock para a mensagem JSON
        String mensagem = "{\"data\":{\"traceId\":\"sample-trace-id\"}}";
        ProdutoResposta produtoResposta = new ProdutoResposta();
        Produto produto = new Produto();
        produto.setTraceId("sample-trace-id");
        produtoResposta.setData(produto);

        when(objectMapper.readValue(mensagem, ProdutoResposta.class)).thenReturn(produtoResposta);
        when(produtoRepository.findByData_TraceId("sample-trace-id")).thenReturn(Optional.of(produtoResposta));
        when(produtoRepository.save(any(ProdutoResposta.class))).thenReturn(produtoResposta);

        // Execução
        produtoResultadoAtualizarListener.run(mensagem);

        // Verificações
        verify(produtoRepository).findByData_TraceId("sample-trace-id");
        verify(produtoRepository).save(any(ProdutoResposta.class));
        verify(produtoErroRepository, never()).save(any(ProdutoErro.class));
    }

    @Test
    void testRun_ProdutoNotFound_CreatesProdutoErro() throws JsonProcessingException {
        // Configuração do mock para o caso onde o produto não é encontrado
        String mensagem = "{\"data\":{\"traceId\":\"not-found-trace-id\"}}";
        ProdutoResposta produtoResposta = new ProdutoResposta();
        Produto produto = new Produto();
        produto.setTraceId("not-found-trace-id");
        produtoResposta.setData(produto);

        when(objectMapper.readValue(mensagem, ProdutoResposta.class)).thenReturn(produtoResposta);
        when(produtoRepository.findByData_TraceId("not-found-trace-id")).thenReturn(Optional.empty());

        ProdutoErro produtoErro = new ProdutoErro();
        produtoErro.setErro("produto nao encontrado na base de dados");
        produtoErro.setTraceId("not-found-trace-id");
        when(produtoErroRepository.save(any(ProdutoErro.class))).thenReturn(produtoErro);

        // Execução
        produtoResultadoAtualizarListener.run(mensagem);

        // Verificações
        verify(produtoRepository).findByData_TraceId("not-found-trace-id");
        verify(produtoErroRepository).save(any(ProdutoErro.class));
    }

    @Test
    void testRun_ExceptionThrown_CreatesProdutoErro() throws JsonProcessingException {
        // Configuração do mock para uma exceção durante o processamento
        String mensagem = "{\"data\":{\"traceId\":\"sample-trace-id\"}}";

        when(objectMapper.readValue(mensagem, ProdutoResposta.class)).thenThrow(new JsonProcessingException("Invalid JSON") {
        });

        ProdutoErro produtoErro = new ProdutoErro();
        produtoErro.setErro("Invalid JSON");
        when(produtoErroRepository.save(any(ProdutoErro.class))).thenReturn(produtoErro);

        // Execução
        produtoResultadoAtualizarListener.run(mensagem);

        // Verificações
        verify(produtoErroRepository).save(any(ProdutoErro.class));
        verify(produtoRepository, never()).findByData_TraceId(anyString());
        verify(produtoRepository, never()).save(any(ProdutoResposta.class));
    }
}
