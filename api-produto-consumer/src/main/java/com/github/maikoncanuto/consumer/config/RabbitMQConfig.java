package com.github.maikoncanuto.consumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
@Slf4j
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.tp.produto.criar}")
    private String topicoProdutoCriar;

    @Value("${spring.rabbitmq.tp.produto.atualizar}")
    private String topicoProdutoAtualizar;

    @Value("${spring.rabbitmq.tp.produto.remover}")
    private String topicoProdutoRemover;

    @Value("${spring.rabbitmq.tp.produto.resultado.criar}")
    private String topicoProdutoResultadoCriar;

    @Value("${spring.rabbitmq.tp.produto.resultado.atualizar}")
    private String topicoProdutoResultadoAtualizar;

    @Value("${spring.rabbitmq.tp.produto.resultado.remover}")
    private String topicoProdutoResultadoRemover;

    @Bean
    public Queue queueProdutoCriar() {
        log.info("inicio da criacao da queue do produto para evento de criar");
        final var queue = new Queue(topicoProdutoCriar, true);
        log.info("fim da criacao da queue do produto para evento de criar, {}", queue);
        return queue;
    }

    @Bean
    public Queue queueProdutoAtualizar() {
        log.info("inicio da criacao da queue do produto para evento de atualizar");
        final var queue = new Queue(topicoProdutoAtualizar, true);
        log.info("fim da criacao da queue do produto para evento de atualizar, {}", queue);
        return queue;
    }

    @Bean
    public Queue queueProdutoRemover() {
        log.info("inicio da criacao da queue do produto para evento de remover");
        final var queue = new Queue(topicoProdutoRemover, true);
        log.info("fim da criacao da queue do produto para evento de remover, {}", queue);
        return queue;
    }

    @Bean
    public Queue queueResultadoCriar() {
        log.info("inicio da criacao da queue do resultado");
        final var queue = new Queue(topicoProdutoResultadoCriar, true);
        log.info("fim da criacao da queue do resultado, {}", queue);
        return queue;
    }

    @Bean
    public Queue queueResultadoAtualizar() {
        log.info("inicio da atualizar da queue do resultado");
        final var queue = new Queue(topicoProdutoResultadoAtualizar, true);
        log.info("fim da atualizar da queue do resultado, {}", queue);
        return queue;
    }

    @Bean
    public Queue queueResultadoRemover() {
        log.info("inicio da remover da queue do resultado");
        final var queue = new Queue(topicoProdutoResultadoRemover, true);
        log.info("fim da remover da queue do resultado, {}", queue);
        return queue;
    }

    @Bean
    public ObjectMapper objectMapper() {
        final var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
