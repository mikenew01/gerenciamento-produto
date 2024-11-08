package com.github.maikoncanuto.producer.domain.exception;

public class ProdutoNaoEncontratoException extends RuntimeException {

    public ProdutoNaoEncontratoException(final String mensagem) {
        super(mensagem);
    }

}
