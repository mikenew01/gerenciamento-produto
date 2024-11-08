package com.github.maikoncanuto.producer.service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
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
@Slf4j
@RequiredArgsConstructor
public class ProdutoProducer {

    @Value("${spring.rabbitmq.tp.produto.criar}")
    private String topicoProdutoCriar;

    @Value("${spring.rabbitmq.tp.produto.atualizar}")
    private String topicoProdutoAtualizar;

    @Value("${spring.rabbitmq.tp.produto.remover}")
    private String topicoProdutoRemover;

    @Value("${spring.application.name}")
    private String applicationName;

    private final RabbitTemplate rabbitTemplate;

    public void enviarCriacaoMensagem(final String traceId, final String mensagem) throws JsonProcessingException {
        log.info("inicio da criacao e envio da mensagem {}", mensagem);
        rabbitTemplate.convertAndSend(topicoProdutoCriar, createMessage(traceId, mensagem));
        log.info("fim da criacao e envio da mensagem {}", mensagem);
    }

    public void enviarAtualizacaoMensagem(final String traceId, final String mensagem) throws JsonProcessingException {
        log.info("inicio da atualizacao e envio da mensagem {}", mensagem);
        rabbitTemplate.convertAndSend(topicoProdutoAtualizar, createMessage(traceId, mensagem));
        log.info("fim da atualizacao e envio da mensagem {}", mensagem);
    }

    public void enviarRemoverMensagem(final String traceId, final String mensagem) throws JsonProcessingException {
        log.info("inicio da remover-item e envio da mensagem {}", mensagem);
        rabbitTemplate.convertAndSend(topicoProdutoRemover, createMessage(traceId, mensagem));
        log.info("fim da remover-item e envio da mensagem {}", mensagem);
    }

    private Message createMessage(final String traceId, final String mensagem) throws JsonProcessingException {
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
