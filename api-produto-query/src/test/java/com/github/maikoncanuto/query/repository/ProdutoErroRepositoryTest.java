package com.github.maikoncanuto.query.repository;

import com.github.maikoncanuto.query.domain.entity.ProdutoErro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProdutoErroRepositoryTest {

    @Mock
    private ProdutoErroRepository produtoErroRepository;

    private ProdutoErro produtoErro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produtoErro = new ProdutoErro();
        produtoErro.setId("1");
    }

    @Test
    void testSaveProdutoErro() {
        when(produtoErroRepository.save(produtoErro)).thenReturn(produtoErro);

        ProdutoErro savedProdutoErro = produtoErroRepository.save(produtoErro);

        assertEquals(produtoErro.getId(), savedProdutoErro.getId());
        verify(produtoErroRepository, times(1)).save(produtoErro);
    }

    @Test
    void testFindById() {
        when(produtoErroRepository.findById("1")).thenReturn(Optional.of(produtoErro));

        Optional<ProdutoErro> foundProdutoErro = produtoErroRepository.findById("1");

        assertTrue(foundProdutoErro.isPresent());
        assertEquals(produtoErro.getId(), foundProdutoErro.get().getId());
        verify(produtoErroRepository, times(1)).findById("1");
    }

    @Test
    void testDeleteById() {
        doNothing().when(produtoErroRepository).deleteById("1");

        produtoErroRepository.deleteById("1");

        verify(produtoErroRepository, times(1)).deleteById("1");
    }
}
