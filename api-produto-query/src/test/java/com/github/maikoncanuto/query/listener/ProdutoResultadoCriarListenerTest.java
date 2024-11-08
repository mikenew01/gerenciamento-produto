package com.github.maikoncanuto.query.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoResultadoCriarListenerTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoErroRepository produtoErroRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProdutoResultadoCriarListener produtoResultadoCriarListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @RabbitListener(queues = {"tp.produto.resultado.criar"})
    void testRun_MessageProcessedSuccessfully() throws JsonProcessingException {
        // Configuração do mock para uma mensagem válida
        String mensagem = "{\"data\":{\"traceId\":\"sample-trace-id\"}}";
        ProdutoResposta produtoResposta = new ProdutoResposta();

        when(objectMapper.readValue(mensagem, ProdutoResposta.class)).thenReturn(produtoResposta);
        when(produtoRepository.save(any(ProdutoResposta.class))).thenReturn(produtoResposta);

        // Execução
        produtoResultadoCriarListener.run(mensagem);

        // Verificações
        verify(produtoRepository).save(any(ProdutoResposta.class));
        verify(produtoErroRepository, never()).save(any(ProdutoErro.class));
    }

    @Test
    void testRun_ExceptionThrown_CreatesProdutoErro() throws JsonProcessingException {
        // Configuração do mock para uma exceção durante o processamento
        String mensagem = "{\"data\":{\"traceId\":\"sample-trace-id\"}}";

        when(objectMapper.readValue(mensagem, ProdutoResposta.class)).thenThrow(new JsonProcessingException("Invalid JSON") {});

        ProdutoErro produtoErro = new ProdutoErro();
        produtoErro.setErro("Invalid JSON");
        when(produtoErroRepository.save(any(ProdutoErro.class))).thenReturn(produtoErro);

        // Execução
        produtoResultadoCriarListener.run(mensagem);

        // Verificações
        verify(produtoErroRepository).save(any(ProdutoErro.class));
        verify(produtoRepository, never()).save(any(ProdutoResposta.class));
    }
}
