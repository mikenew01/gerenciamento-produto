package com.github.maikoncanuto.consumer.producer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoResultadoProducerTest {

    @InjectMocks
    private ProdutoResultadoProducer produtoResultadoProducer;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private final String topico = "test.topic";
    private final String traceId = "sample-trace-id";
    private final String mensagem = "Test Message";

    @BeforeEach
    void setUp() {
        produtoResultadoProducer = new ProdutoResultadoProducer(rabbitTemplate);
    }

    @Test
    void testEnviarResultado() {
        produtoResultadoProducer.enviarResultado(topico, traceId, mensagem);

        verify(rabbitTemplate, times(1)).convertAndSend(eq(topico), any(Message.class));
    }

}
