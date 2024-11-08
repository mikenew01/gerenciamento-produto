package com.github.maikoncanuto.query.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable {

    private Long id;

    private String traceId;

    private String nome;

    private String descricao;

    private Integer quantidade;

    private Double preco;

}
