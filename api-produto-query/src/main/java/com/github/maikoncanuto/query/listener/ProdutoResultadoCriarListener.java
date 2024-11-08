package com.github.maikoncanuto.query.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maikoncanuto.query.domain.entity.ProdutoErro;
import com.github.maikoncanuto.query.domain.entity.ProdutoResposta;
import com.github.maikoncanuto.query.repository.ProdutoErroRepository;
import com.github.maikoncanuto.query.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.github.maikoncanuto.query.domain.enuns.NomeEvento.PRODUTO_CRIACAO;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProdutoResultadoCriarListener {

    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;
    private final ProdutoErroRepository produtoErroRepository;


    @RabbitListener(queues = {"tp.produto.resultado.criar"})
    public void run(final String mensagem) throws JsonProcessingException {

        try {
            log.info("Inicio do consumo da mensagem para atualizacao do objeto {}", mensagem);
            final var produtoMensagem = objectMapper.readValue(mensagem, ProdutoResposta.class);
            log.info("Mensagem convertida para ProdutoMensagem {}", produtoMensagem);

            log.info("inicio para salvar ProdutoResposta na collection {}", produtoMensagem);
            final var produtoSalvo = produtoRepository.save(produtoMensagem);
            log.info("fim para salvar ProdutoResposta na collection {}", produtoSalvo);
        } catch (Exception e) {
            final var produtoErroSalvo = criarProdutoErro(e.getMessage(), mensagem, null);
            log.error("erro ao criar produto da base de dados {}", produtoErroSalvo);
        }
    }

    private ProdutoErro criarProdutoErro(final String erro, final String mensagem, final String traceId) {
        final var produtoErro = new ProdutoErro();
        produtoErro.setErro(erro);
        produtoErro.setNomeEvento(PRODUTO_CRIACAO);
        produtoErro.setMensagem(mensagem);
        produtoErro.setTraceId(traceId);
        return produtoErroRepository.save(produtoErro);
    }
}
