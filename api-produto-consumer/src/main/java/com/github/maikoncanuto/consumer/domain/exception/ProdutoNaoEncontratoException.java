package com.github.maikoncanuto.consumer.domain.exception;

public class ProdutoNaoEncontratoException extends RuntimeException {

    public ProdutoNaoEncontratoException(final String mensagem) {
        super(mensagem);
    }

}
