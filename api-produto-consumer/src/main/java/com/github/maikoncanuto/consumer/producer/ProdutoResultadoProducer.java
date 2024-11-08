package com.github.maikoncanuto.consumer.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoResultadoProducer {

    @Value("${spring.application.name}")
    private String applicationName;

    private final RabbitTemplate rabbitTemplate;

    public void enviarResultado(final String topico, final String traceId, final String mensagem) {
        log.info("inicio do envio da mensagem de resultado {}", mensagem);
        rabbitTemplate.convertAndSend(topico, createMessage(traceId, mensagem));
        log.info("fim do envio da mensagem de resultado {}", mensagem);
    }

    public Message createMessage(final String traceId, final String mensagem) {
        return MessageBuilder
                .withBody(mensagem.getBytes(UTF_8))
                .setContentType("application/json")
                .setMessageId(traceId)
                .setAppId(applicationName)
                .setTimestamp(new Date())
                .setContentEncoding(UTF_8.name())
                .setCorrelationId(traceId)
                .build();
    }

}
