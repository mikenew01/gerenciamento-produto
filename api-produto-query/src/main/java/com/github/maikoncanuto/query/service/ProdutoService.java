package com.github.maikoncanuto.query.service;

import com.github.maikoncanuto.query.domain.entity.ProdutoResposta;
import com.github.maikoncanuto.query.domain.exception.ProdutoNaoEncontratoException;
import com.github.maikoncanuto.query.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoResposta buscarProdutoPorTraceId(final String traceId) {
        log.info("inicio da busca do produto resposta por id {}", traceId);
        final var produtoResposta = produtoRepository.findByData_TraceId(traceId)
                .orElseThrow(() -> new ProdutoNaoEncontratoException("Produto não encontrado"));
        log.info("fim da busca do produto resposta {}", produtoResposta);
        return produtoResposta;
    }

    public List<ProdutoResposta> buscarTodosProdutos() {
        log.info("inicio da busca do produto resposta");
        final var produtos = produtoRepository.findAll();
        log.info("fim da busca do produto resposta para todos {}", produtos);
        return produtos;
    }

}
