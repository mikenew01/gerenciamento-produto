package com.github.maikoncanuto.consumer.domain.dto;


public record ProdutoMensagem(
        Long id,
        String traceId,
        String nome,
        int quantidade,
        String descricao,
        double preco
) {
}
