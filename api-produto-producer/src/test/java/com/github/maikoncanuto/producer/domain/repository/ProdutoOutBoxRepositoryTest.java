package com.github.maikoncanuto.producer.domain.repository;

import com.github.maikoncanuto.producer.domain.entity.ProdutoOutBox;
import com.github.maikoncanuto.producer.domain.enuns.NomeEvento;
import com.github.maikoncanuto.producer.domain.enuns.StatusEnvio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class ProdutoOutBoxRepositoryTest {

    @Autowired
    private ProdutoOutBoxRepository produtoOutBoxRepository;

    @BeforeEach
    void setUp() {
        ProdutoOutBox produto1 = new ProdutoOutBox(null, "trace-1", NomeEvento.PRODUTO_CRIACAO, StatusEnvio.PENDENTE, null, "{\"data\": \"produto1\"}");
        ProdutoOutBox produto2 = new ProdutoOutBox(null, "trace-2", NomeEvento.PRODUTO_ATUALIZACAO, StatusEnvio.ENVIADO, null, "{\"data\": \"produto2\"}");
        ProdutoOutBox produto3 = new ProdutoOutBox(null, "trace-3", NomeEvento.PRODUTO_CRIACAO, StatusEnvio.PENDENTE, null, "{\"data\": \"produto3\"}");
        produtoOutBoxRepository.saveAll(List.of(produto1, produto2, produto3));
    }

    @Test
    void testFindByStatusEnvio_Pendente() {
        List<ProdutoOutBox> pendentes = produtoOutBoxRepository.findByStatusEnvio(StatusEnvio.PENDENTE);

        assertNotNull(pendentes);
        assertEquals(2, pendentes.size());
        assertEquals("trace-1", pendentes.get(0).getTraceId());
        assertEquals("trace-3", pendentes.get(1).getTraceId());
    }

    @Test
    void testFindByStatusEnvio_Enviado() {
        List<ProdutoOutBox> enviados = produtoOutBoxRepository.findByStatusEnvio(StatusEnvio.ENVIADO);

        assertNotNull(enviados);
        assertEquals(1, enviados.size());
        assertEquals("trace-2", enviados.get(0).getTraceId());
    }
}
