package com.github.maikoncanuto.consumer.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "trace_id", nullable = false)
    private String traceId;

    @Column(name = "nome_produto", nullable = false)
    private String nome;

    @Column(name = "preco_produto", nullable = false)
    private Double preco;

    @Column(name = "quantidade_produto", nullable = false)
    private Integer quantidade;

    @Column(name = "descricao_produto", nullable = false)
    private String descricao;

}
