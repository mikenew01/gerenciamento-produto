package com.github.maikoncanuto.consumer.domain.dto;

import java.util.List;

public record Response(
        Object data,
        List<Mensagem> errors
) {
}
