package com.github.maikoncanuto.producer.domain.repository;

import com.github.maikoncanuto.producer.domain.entity.ProdutoOutBox;
import com.github.maikoncanuto.producer.domain.enuns.StatusEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoOutBoxRepository extends JpaRepository<ProdutoOutBox, Long> {

    List<ProdutoOutBox> findByStatusEnvio(StatusEnvio statusEnvio);

}
