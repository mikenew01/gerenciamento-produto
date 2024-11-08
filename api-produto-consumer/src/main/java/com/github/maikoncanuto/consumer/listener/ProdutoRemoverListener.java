package com.github.maikoncanuto.consumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maikoncanuto.consumer.domain.dto.Mensagem;
import com.github.maikoncanuto.consumer.domain.dto.ProdutoMensagem;
import com.github.maikoncanuto.consumer.domain.dto.Response;
import com.github.maikoncanuto.consumer.domain.exception.ProdutoNaoEncontratoException;
import com.github.maikoncanuto.consumer.producer.ProdutoResultadoProducer;
import com.github.maikoncanuto.consumer.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProdutoRemoverListener {

    @Value("${spring.rabbitmq.tp.produto.resultado.remover}")
    private String topicoProdutoResultado;

    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;
    private final ProdutoResultadoProducer produtoResultadoProducer;

    @RabbitListener(queues = {"tp.produto.remover"})
    public void run(final String mensagem) throws JsonProcessingException {

        try {
            log.info("inicio do consumo da mensagem para remover o {}", mensagem);
            final var produtoMensagem = objectMapper.readValue(mensagem, ProdutoMensagem.class);
            log.info("fim do consumo da mensagem para remover do objeto {}", produtoMensagem);

            final var produtoConsultado = produtoRepository.findByTraceId(produtoMensagem.traceId()).orElseThrow(() -> new ProdutoNaoEncontratoException(produtoMensagem.traceId()));

            log.info("inicio da remocao do banco de dados pelo {}", produtoMensagem.traceId());
            produtoRepository.delete(produtoConsultado);
            produtoConsultado.setTraceId(produtoMensagem.traceId());
            final var response = new Response(produtoConsultado, null);
            log.info("fim da remocao do banco de dados pelo {}", produtoMensagem.traceId());

            log.info("inicio envio do resultado para o topico de resultado {}", response);
            produtoResultadoProducer.enviarResultado(topicoProdutoResultado, produtoMensagem.traceId(), objectMapper.writeValueAsString(response));
            log.info("fim envio do resultado para o topico de resultado {}", response);

        } catch (Exception ex) {
            log.warn("inicio da configuracao de mensagem de erro como resultado");
            final var mensagemErro = new Mensagem("0003", ex.getMessage());
            final var response = new Response(null, List.of(mensagemErro));
            log.warn("fim da configuracao de mensagem de erro como resultado {}", response);
            log.info("inicio envio do resultado de erro para o topico de resultado {}", response);
            produtoResultadoProducer.enviarResultado(topicoProdutoResultado, null, objectMapper.writeValueAsString(response));
            log.info("fim envio do resultado para de erro o topico de resultado {}", response);
            log.error(ex.getLocalizedMessage());
        }

    }

}
