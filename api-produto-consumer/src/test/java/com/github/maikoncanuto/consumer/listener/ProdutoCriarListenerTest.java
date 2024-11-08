package com.github.maikoncanuto.consumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maikoncanuto.consumer.domain.dto.ProdutoMensagem;
import com.github.maikoncanuto.consumer.domain.entity.Produto;
import com.github.maikoncanuto.consumer.producer.ProdutoResultadoProducer;
import com.github.maikoncanuto.consumer.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoCriarListenerTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ProdutoResultadoProducer produtoResultadoProducer;

    @InjectMocks
    private ProdutoCriarListener produtoCriarListener;

    private final String mensagem = "{\"traceId\": \"sample-trace-id\", \"nome\": \"Produto Teste\", \"preco\": 100.0, \"quantidade\": 5, \"descricao\": \"Produto de Teste\"}";
    private ProdutoMensagem produtoMensagem;
    private Produto produto;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        produtoMensagem = new ProdutoMensagem(null, "sample-trace-id", "Produto Teste", 5, "Produto de Teste", 100.0);
        produto = new Produto(null, "sample-trace-id", "Produto Teste", 100.0, 5, "Produto de Teste");

        when(objectMapper.readValue(mensagem, ProdutoMensagem.class)).thenReturn(produtoMensagem);
    }

    @Test
    void testRun_Sucesso() throws JsonProcessingException {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        produtoCriarListener.run(mensagem);

        verify(produtoRepository, times(1)).save(any(Produto.class));
        verify(produtoResultadoProducer, times(1)).enviarResultado(any(), eq(produtoMensagem.traceId()), any());
    }

    @Test
    void testRun_JsonProcessingException() throws JsonProcessingException {
        when(objectMapper.readValue(mensagem, ProdutoMensagem.class)).thenThrow(new JsonProcessingException("Erro de parsing") {
        });

        produtoCriarListener.run(mensagem);

        verify(produtoResultadoProducer, times(1)).enviarResultado(any(), isNull(), any());
        verify(produtoRepository, never()).save(any());
    }

    @Test
    void testRun_ExceptionDuringSave() throws JsonProcessingException {
        when(produtoRepository.save(any())).thenThrow(new RuntimeException("Erro ao salvar produto"));

        produtoCriarListener.run(mensagem);

        verify(produtoResultadoProducer, times(1)).enviarResultado(any(), isNull(), any());
        verify(produtoRepository, times(1)).save(any());
    }
}
