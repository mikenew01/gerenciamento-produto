package com.github.maikoncanuto.producer.domain.dto;


import java.util.UUID;

public record RequestProdutoOutBox(
        Long id,
        String traceId,
        String nome,
        Integer quantidade,
        String descricao,
        Double preco
) {
    public RequestProdutoOutBox {
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString();
        }
    }
}
