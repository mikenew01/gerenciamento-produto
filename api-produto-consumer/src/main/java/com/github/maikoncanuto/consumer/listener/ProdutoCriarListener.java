package com.github.maikoncanuto.consumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maikoncanuto.consumer.domain.dto.Mensagem;
import com.github.maikoncanuto.consumer.domain.dto.ProdutoMensagem;
import com.github.maikoncanuto.consumer.domain.dto.Response;
import com.github.maikoncanuto.consumer.domain.entity.Produto;
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
public class ProdutoCriarListener {

    @Value("${spring.rabbitmq.tp.produto.resultado.criar}")
    private String topicoProdutoResultado;

    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;
    private final ProdutoResultadoProducer produtoResultadoProducer;

    @RabbitListener(queues = {"tp.produto.criar"})
    public void run(final String mensagem) throws JsonProcessingException {

        try {
            log.info("inicio do consumo da mensagem para criacao do objeto {}", mensagem);
            final var produtoMensagem = objectMapper.readValue(mensagem, ProdutoMensagem.class);
            log.info("fim do consumo da mensagem para criacao do objeto {}", produtoMensagem);

            final var produtoEntity = new Produto(null,
                    produtoMensagem.traceId(),
                    produtoMensagem.nome(),
                    produtoMensagem.preco(),
                    produtoMensagem.quantidade(),
                    produtoMensagem.descricao());

            log.info("inicio para salvar registro de criacao do produto no banco de dados {}", produtoEntity);
            final var produtoSalvo = produtoRepository.save(produtoEntity);
            final var response = new Response(produtoSalvo, null);
            log.info("fim para salvar registro de criacao do produto no banco de dados {}", response);

            log.info("inicio envio do resultado para o topico de resultado {}", response);
            produtoResultadoProducer.enviarResultado(topicoProdutoResultado, produtoMensagem.traceId(), objectMapper.writeValueAsString(response));
            log.info("fim envio do resultado para o topico de resultado {}", response);

        } catch (Exception e) {

            log.warn("inicio da configuracao de mensagem de erro como resultado");
            final var mensagemErro = new Mensagem("0001", e.getMessage());
            final var response = new Response(null, List.of(mensagemErro));
            log.warn("fim da configuracao de mensagem de erro como resultado {}", response);

            log.info("inicio envio do resultado de erro para o topico de resultado {}", response);
            produtoResultadoProducer.enviarResultado(topicoProdutoResultado, null, objectMapper.writeValueAsString(response));
            log.info("fim envio do resultado para de erro o topico de resultado {}", response);

            log.error(e.getLocalizedMessage());
        }

    }

}
