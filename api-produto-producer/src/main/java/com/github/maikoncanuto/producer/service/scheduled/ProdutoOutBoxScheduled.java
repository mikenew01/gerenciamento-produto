package com.github.maikoncanuto.producer.service.scheduled;

import com.github.maikoncanuto.producer.domain.enuns.StatusEnvio;
import com.github.maikoncanuto.producer.domain.repository.ProdutoOutBoxRepository;
import com.github.maikoncanuto.producer.service.producer.ProdutoProducer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.github.maikoncanuto.producer.domain.enuns.StatusEnvio.*;
import static java.time.LocalDateTime.now;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProdutoOutBoxScheduled {

    private final ProdutoOutBoxRepository produtoOutBoxRepository;
    private final ProdutoProducer produtoProducer;

    @Scheduled(fixedRate = 10, initialDelay = 1)
    @Transactional
    public synchronized void processarProdutosPendentes() {
        processamentoMensagens(PENDENTE);
    }

    @Scheduled(fixedRate = 30000, initialDelay = 100)
    @Transactional
    public synchronized void processarProdutosQueFalharam() {
        processamentoMensagens(FALHOU);
    }

    private void processamentoMensagens(final StatusEnvio status) {
        final var produtos = produtoOutBoxRepository.findByStatusEnvio(status);

        if (produtos.isEmpty()) {
            return;
        }

        produtos.forEach(produto -> {
            try {
                log.info("inicio do envio da mensagem para o broker {}", produto);

                switch (produto.getNomeEvento()) {
                    case PRODUTO_CRIACAO ->
                            produtoProducer.enviarCriacaoMensagem(produto.getTraceId(), produto.getData());
                    case PRODUTO_ATUALIZACAO ->
                            produtoProducer.enviarAtualizacaoMensagem(produto.getTraceId(), produto.getData());
                    case PRODUTO_REMOCAO ->
                            produtoProducer.enviarRemoverMensagem(produto.getTraceId(), produto.getData());
                }

                produto.setStatusEnvio(ENVIADO);
                produto.setTimestamp(now());
                final var produtoAtualizado = produtoOutBoxRepository.saveAndFlush(produto);
                log.info("fim do envio da mensagem para o broker {}", produtoAtualizado);
            } catch (Exception e) {
                log.error("erro ao processar o produto com ID {}", produto.getId(), e);
                produto.setStatusEnvio(FALHOU);
                produto.setTimestamp(now());
                produtoOutBoxRepository.saveAndFlush(produto);
            }
        });
    }
}
