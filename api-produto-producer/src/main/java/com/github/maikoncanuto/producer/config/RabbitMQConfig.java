package com.github.maikoncanuto.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.tp.produto.criar}")
    private String topicoProdutoCriar;

    @Value("${spring.rabbitmq.tp.produto.atualizar}")
    private String topicoProdutoAtualizar;

    @Value("${spring.rabbitmq.tp.produto.remover}")
    private String topicoProdutoRemover;

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
}
