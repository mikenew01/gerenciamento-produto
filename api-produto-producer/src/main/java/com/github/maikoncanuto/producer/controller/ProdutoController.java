package com.github.maikoncanuto.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.maikoncanuto.producer.domain.dto.RequestProdutoOutBox;
import com.github.maikoncanuto.producer.domain.dto.Response;
import com.github.maikoncanuto.producer.service.ProdutoOutBoxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/produtos")
@RequiredArgsConstructor
@Slf4j
public class ProdutoController {

    private final ProdutoOutBoxService produtoOutBoxService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response> criarProduto(@RequestBody final RequestProdutoOutBox produto) throws JsonProcessingException {
        log.info("inicio da criacao do evento para criar produto {}", produto);
        final var novoProduto = produtoOutBoxService.criarEventoProdutoCriacao(produto);
        final var resposta = new Response(novoProduto, null);
        log.info("fim da criacao do evento para criar produto {}", resposta);
        return new ResponseEntity<>(resposta, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/{traceId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response> atualizarProduto(@PathVariable final String traceId, @RequestBody final RequestProdutoOutBox produto) throws JsonProcessingException {
        log.info("inicio da criacao do evento para atualizar produto {}", produto);
        final var novoProduto = produtoOutBoxService.criarEventoProdutoAtualizacao(produto);
        final var resposta = new Response(novoProduto, null);
        log.info("fim da criacao do evento para atualizar produto {}", resposta);
        return new ResponseEntity<>(resposta, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/{traceId}", produces = "application/json")
    public ResponseEntity<Response> deletarProduto(@PathVariable final String traceId) throws JsonProcessingException {
        log.info("inicio da criacao do evento para deletar produto {}", traceId);
        final var novoProduto = produtoOutBoxService.criarEventoProdutoDeletar(traceId);
        final var resposta = new Response(novoProduto, null);
        log.info("fim da criacao do evento para deletar produto {}", resposta);
        return new ResponseEntity<>(resposta, HttpStatus.ACCEPTED);
    }

}
