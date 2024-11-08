package com.github.maikoncanuto.query.repository;

import com.github.maikoncanuto.query.domain.entity.ProdutoResposta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends MongoRepository<ProdutoResposta, String> {

    Optional<ProdutoResposta> findByData_TraceId(String traceId);

}
