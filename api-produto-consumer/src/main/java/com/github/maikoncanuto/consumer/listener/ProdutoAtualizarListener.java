package com.github.maikoncanuto.consumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maikoncanuto.consumer.domain.dto.Mensagem;
import com.github.maikoncanuto.consumer.domain.dto.ProdutoMensagem;
import com.github.maikoncanuto.consumer.domain.dto.Response;
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
public class ProdutoAtualizarListener {

    @Value("${spring.rabbitmq.tp.produto.resultado.atualizar}")
    private String topicoProdutoResultado;

    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;
    private final ProdutoResultadoProducer produtoResultadoProducer;

    @RabbitListener(queues = {"tp.produto.atualizar"})
    public void run(final String mensagem) throws JsonProcessingException {

        try {
            log.info("Inicio do consumo da mensagem para atualizacao do objeto {}", mensagem);
            final var produtoMensagem = objectMapper.readValue(mensagem, ProdutoMensagem.class);
            log.info("Mensagem convertida para ProdutoMensagem {}", produtoMensagem);

            final var produtoExistente = produtoRepository.findById(produtoMensagem.id());

            if (produtoExistente.isPresent()) {
                log.info("Produto encontrado com ID: {}. Atualizando...", produtoMensagem.id());
                final var produtoAtualizado = produtoExistente.get();
                produtoAtualizado.setTraceId(produtoMensagem.traceId());
                produtoAtualizado.setNome(produtoMensagem.nome());
                produtoAtualizado.setPreco(produtoMensagem.preco());
                produtoAtualizado.setQuantidade(produtoMensagem.quantidade());
                produtoAtualizado.setDescricao(produtoMensagem.descricao());

                final var produtoSalvo = produtoRepository.save(produtoAtualizado);
                final var response = new Response(produtoSalvo, null);
                log.info("produto atualizado com sucesso: {}", response);

                log.info("inicio envio do resultado de atualizacao para o topico de resultado {}", response);
                produtoResultadoProducer.enviarResultado(topicoProdutoResultado, produtoMensagem.traceId(), objectMapper.writeValueAsString(response));
                log.info("fim envio do resultado para o topico de resultado {}", response);

            } else {
                log.warn("Produto com ID: {} nao encontrado", produtoMensagem.traceId());
                Mensagem mensagemErro = new Mensagem("0002", "Produto não encontrado para atualização");
                Response response = new Response(null, List.of(mensagemErro));

                log.info("Inicio envio do resultado de erro para o topico de resultado {}", response);
                produtoResultadoProducer.enviarResultado(topicoProdutoResultado, produtoMensagem.traceId(), objectMapper.writeValueAsString(response));
                log.info("Fim envio do resultado de erro para o topico de resultado {}", response);
            }

        } catch (Exception e) {
            log.warn("Inicio da configuracao de mensagem de erro como resultado");
            final var mensagemErro = new Mensagem("0002", e.getMessage());
            final var response = new Response(null, List.of(mensagemErro));
            log.warn("Fim da configuracao de mensagem de erro como resultado {}", response);

            log.info("Inicio envio do resultado de erro para o topico de resultado {}", response);
            produtoResultadoProducer.enviarResultado(topicoProdutoResultado, null, objectMapper.writeValueAsString(response));
            log.info("Fim envio do resultado de erro para o topico de resultado {}", response);

            log.error("Erro durante o processamento: {}", e.getLocalizedMessage());
        }
    }
}
