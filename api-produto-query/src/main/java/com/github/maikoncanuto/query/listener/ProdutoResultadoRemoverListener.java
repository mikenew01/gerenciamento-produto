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

import static com.github.maikoncanuto.query.domain.enuns.NomeEvento.PRODUTO_REMOCAO;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProdutoResultadoRemoverListener {

    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;
    private final ProdutoErroRepository produtoErroRepository;

    @RabbitListener(queues = {"tp.produto.resultado.remover"})
    public void run(final String mensagem) throws JsonProcessingException {

        try {
            log.info("Inicio do consumo da mensagem para remocao do objeto {}", mensagem);
            final var produtoMensagem = objectMapper.readValue(mensagem, ProdutoResposta.class);
            log.info("Mensagem convertida para ProdutoMensagem {}", produtoMensagem);

            final var produtoConsultado = produtoRepository.findByData_TraceId(produtoMensagem.getData().getTraceId());

            if (produtoConsultado.isEmpty()) {
                final var produtoErroSalvo = criarProdutoErro("produto nao encontrado na base de dados", mensagem, produtoMensagem.getData().getTraceId());
                log.warn("produto enviado nao localizado na base de dados {}", produtoErroSalvo);
                return;
            }

            final var produto = produtoConsultado.get();
            log.info("inicio para remover ProdutoResposta na collection {}", produtoMensagem);
            produtoRepository.delete(produto);
            log.info("fim para remover ProdutoResposta na collection {}", produtoConsultado);
        } catch (Exception e) {
            final var produtoErroSalvo = criarProdutoErro(e.getMessage(), mensagem, null);
            log.error("erro ao remover produto da base de dados {}", produtoErroSalvo);
        }
    }

    private ProdutoErro criarProdutoErro(final String erro, final String mensagem, final String traceId) {
        final var produtoErro = new ProdutoErro();
        produtoErro.setErro(erro);
        produtoErro.setNomeEvento(PRODUTO_REMOCAO);
        produtoErro.setMensagem(mensagem);
        produtoErro.setTraceId(traceId);
        return produtoErroRepository.save(produtoErro);
    }

}
