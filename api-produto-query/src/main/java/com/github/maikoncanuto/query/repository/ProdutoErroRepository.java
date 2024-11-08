package com.github.maikoncanuto.query.repository;

import com.github.maikoncanuto.query.domain.entity.ProdutoErro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoErroRepository extends MongoRepository<ProdutoErro, String> {
}
