package com.github.maikoncanuto.query.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.tp.produto.resultado.criar}")
    private String topicoProdutoResultadoCriar;

    @Value("${spring.rabbitmq.tp.produto.resultado.atualizar}")
    private String topicoProdutoResultadoAtualizar;

    @Value("${spring.rabbitmq.tp.produto.resultado.remover}")
    private String topicoProdutoResultadoRemover;

    @Bean
    public Queue queueProdutoResultadoCriar() {
        log.info("inicio da queue do produto para evento de resultado criar");
        final var queue = new Queue(topicoProdutoResultadoCriar, true);
        log.info("fim da queue do produto para evento de resultado criar, {}", queue);
        return queue;
    }

    @Bean
    public Queue queueProdutoResultadoAtualizar() {
        log.info("inicio da queue do produto para evento de resultado atualizar");
        final var queue = new Queue(topicoProdutoResultadoAtualizar, true);
        log.info("fim da queue do produto para evento de resultado atualizar, {}", queue);
        return queue;
    }

    @Bean
    public Queue queueProdutoResultadoRemover() {
        log.info("inicio da queue do produto para evento de resultado remover");
        final var queue = new Queue(topicoProdutoResultadoRemover, true);
        log.info("fim da queue do produto para evento de resultado remover, {}", queue);
        return queue;
    }
}
