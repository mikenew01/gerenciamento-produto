package com.github.maikoncanuto.producer.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.maikoncanuto.producer.domain.enuns.NomeEvento;
import com.github.maikoncanuto.producer.domain.enuns.StatusEnvio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static com.github.maikoncanuto.producer.domain.enuns.StatusEnvio.PENDENTE;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tb_produto_out_box")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoOutBox {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "trace_id", length = 1000, nullable = false)
    private String traceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "nome_evento", length = 150, nullable = false)
    private NomeEvento nomeEvento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_envio", length = 100, nullable = false)
    private StatusEnvio statusEnvio = PENDENTE;

    @Column(name = "timestamp", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamp = LocalDateTime.now();

    @JsonIgnore
    @Column(name = "data", length = 2500)
    private String data;
}