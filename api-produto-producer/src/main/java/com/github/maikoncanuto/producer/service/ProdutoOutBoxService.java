package com.github.maikoncanuto.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maikoncanuto.producer.domain.dto.RequestProdutoOutBox;
import com.github.maikoncanuto.producer.domain.entity.ProdutoOutBox;
import com.github.maikoncanuto.producer.domain.repository.ProdutoOutBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.github.maikoncanuto.producer.domain.enuns.NomeEvento.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProdutoOutBoxService {

    private final ProdutoOutBoxRepository produtoOutBoxRepository;
    private final ObjectMapper objectMapper;

    public ProdutoOutBox criarEventoProdutoCriacao(final RequestProdutoOutBox requestProdutoOutBox) throws JsonProcessingException {
        log.info("inicio para criar o produto out box {}", requestProdutoOutBox);
        final var produtoOutBox = new ProdutoOutBox();
        produtoOutBox.setTraceId(requestProdutoOutBox.traceId());
        produtoOutBox.setData(objectMapper.writeValueAsString(requestProdutoOutBox));
        produtoOutBox.setNomeEvento(PRODUTO_CRIACAO);
        final var produtoResponse = produtoOutBoxRepository.saveAndFlush(produtoOutBox);
        log.info("fim para criar o produto out box {}", produtoOutBox);
        return produtoResponse;
    }

    public ProdutoOutBox criarEventoProdutoAtualizacao(final RequestProdutoOutBox requestProdutoOutBox) throws JsonProcessingException {
        log.info("inicio para atualizar o produto out box {}", requestProdutoOutBox);
        final var produtoOutBox = new ProdutoOutBox();
        produtoOutBox.setTraceId(requestProdutoOutBox.traceId());
        produtoOutBox.setData(objectMapper.writeValueAsString(requestProdutoOutBox));
        produtoOutBox.setNomeEvento(PRODUTO_ATUALIZACAO);
        final var produtoResponse = produtoOutBoxRepository.saveAndFlush(produtoOutBox);
        log.info("fim para atualizar o produto out box {}", produtoOutBox);
        return produtoResponse;
    }

    public ProdutoOutBox criarEventoProdutoDeletar(final String traceId) throws JsonProcessingException {
        log.info("inicio para deletar o produto out box {}", traceId);
        final var request = new RequestProdutoOutBox(null, traceId, null, null, null, null);
        final var produtoOutBox = new ProdutoOutBox();
        produtoOutBox.setTraceId(traceId);
        produtoOutBox.setData(objectMapper.writeValueAsString(request));
        produtoOutBox.setNomeEvento(PRODUTO_REMOCAO);
        final var produtoResponse = produtoOutBoxRepository.saveAndFlush(produtoOutBox);
        log.info("fim para deletar o produto out box {}", produtoOutBox);
        return produtoResponse;
    }

}
