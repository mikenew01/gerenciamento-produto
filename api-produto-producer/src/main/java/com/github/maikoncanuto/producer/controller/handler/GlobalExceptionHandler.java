package com.github.maikoncanuto.producer.controller.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.maikoncanuto.producer.domain.dto.Mensagem;
import com.github.maikoncanuto.producer.domain.dto.Response;
import com.github.maikoncanuto.producer.domain.exception.ProdutoNaoEncontratoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.util.List.of;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoNaoEncontratoException.class)
    public ResponseEntity<Response> handleProdutoNaoEncontradoException(ProdutoNaoEncontratoException ex) {
        final var mensagem = new Mensagem(String.valueOf(NOT_FOUND), ex.getMessage());
        final var resposta = new Response(null, of(mensagem));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<Response> handleJsonProcessingException(JsonProcessingException ex) {
        final var mensagem = new Mensagem(String.valueOf(INTERNAL_SERVER_ERROR), ex.getMessage());
        final var resposta = new Response(null, of(mensagem));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }

}
