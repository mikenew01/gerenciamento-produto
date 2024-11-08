package com.github.maikoncanuto.consumer.repository;

import com.github.maikoncanuto.consumer.domain.entity.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setTraceId("sample-trace-id");
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setQuantidade(10);
        produto.setDescricao("Descrição do Produto Teste");
        produtoRepository.save(produto);
    }

    @Test
    void testFindByTraceId() {
        Optional<Produto> produtoOptional = produtoRepository.findByTraceId("sample-trace-id");

        assertTrue(produtoOptional.isPresent());
        Produto produtoEncontrado = produtoOptional.get();
        assertEquals(produto.getTraceId(), produtoEncontrado.getTraceId());
        assertEquals(produto.getNome(), produtoEncontrado.getNome());
        assertEquals(produto.getPreco(), produtoEncontrado.getPreco());
        assertEquals(produto.getQuantidade(), produtoEncontrado.getQuantidade());
        assertEquals(produto.getDescricao(), produtoEncontrado.getDescricao());
    }

    @Test
    void testFindByTraceId_NotFound() {
        Optional<Produto> produtoOptional = produtoRepository.findByTraceId("invalid-trace-id");

        assertTrue(produtoOptional.isEmpty());
    }
}
