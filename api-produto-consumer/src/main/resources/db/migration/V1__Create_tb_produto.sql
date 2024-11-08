CREATE TABLE produto
(
    id                 SERIAL PRIMARY KEY,
    nome_produto       VARCHAR(255)   NOT NULL,
    trace_id       VARCHAR(255)   NOT NULL,
    quantidade_produto INT            NOT NULL,
    descricao_produto  VARCHAR(500),
    preco_produto      DECIMAL(10, 2) NOT NULL,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_nome_produto ON produto (nome_produto);
CREATE INDEX idx_trace_id ON produto (trace_id);
CREATE INDEX idx_id_produto ON produto (id);
CREATE INDEX idx_id_nome_produto ON produto (id, nome_produto);