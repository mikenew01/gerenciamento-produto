package com.github.maikoncanuto.query.domain.dto;

import com.github.maikoncanuto.query.domain.entity.Mensagem;

import java.util.List;

public record Response(
        Object data,
        List<Mensagem> errors
) {
}
