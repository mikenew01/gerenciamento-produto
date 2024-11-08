package com.github.maikoncanuto.producer.domain.dto;

import java.util.List;

public record Response(
        Object data,
        List<Mensagem> errors
) {
}
