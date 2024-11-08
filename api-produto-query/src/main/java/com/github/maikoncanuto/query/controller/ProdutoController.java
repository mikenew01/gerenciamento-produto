package com.github.maikoncanuto.query.controller;

import com.github.maikoncanuto.query.domain.entity.ProdutoResposta;
import com.github.maikoncanuto.query.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/produtos")
@RequiredArgsConstructor
@Slf4j
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResposta>> buscarTodos() {
        log.info("inicio da busca por todos");
        final var produtoResposta = produtoService.buscarTodosProdutos();
        log.info("fim da busca por todos {}", produtoResposta);
        return ResponseEntity.ok(produtoResposta);
    }

    @GetMapping("/{traceId}")
    public ResponseEntity<ProdutoResposta> buscarProdutoPorTraceId(@PathVariable("traceId") final String traceId) {
        log.info("inicio da busca por id {}", traceId);
        final var produtoResposta = produtoService.buscarProdutoPorTraceId(traceId);
        log.info("fim da busca por id {}", produtoResposta);
        return ResponseEntity.ok(produtoResposta);
    }


}
