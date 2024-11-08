package com.github.maikoncanuto.query.domain.exception;

public class ProdutoNaoEncontratoException extends RuntimeException {

    public ProdutoNaoEncontratoException(final String mensagem) {
        super(mensagem);
    }

}
