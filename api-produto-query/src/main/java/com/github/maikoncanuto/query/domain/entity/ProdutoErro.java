package com.github.maikoncanuto.query.domain.entity;

import com.github.maikoncanuto.query.domain.enuns.NomeEvento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "produto_erro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoErro implements Serializable {

    @Id
    private String id;

    private NomeEvento nomeEvento;

    private String traceId;

    private String mensagem;

    private String erro;

}
