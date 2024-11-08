package com.github.maikoncanuto.consumer.repository;

import com.github.maikoncanuto.consumer.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByTraceId(String traceId);


}
