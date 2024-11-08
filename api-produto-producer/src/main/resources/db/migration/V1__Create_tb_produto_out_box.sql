CREATE TABLE tb_produto_out_box
(
    id           SERIAL PRIMARY KEY,
    nome_evento  VARCHAR(150)                        NOT NULL,
    status_envio VARCHAR(100)                        NOT NULL,
    trace_id    VARCHAR(1000)                       NOT NULL,
    timestamp    TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    data         VARCHAR(2500)
);

CREATE INDEX idx_produto_outbox_id ON tb_produto_out_box (id);
CREATE INDEX idx_produto_outbox_nome_evento ON tb_produto_out_box (nome_evento);
CREATE INDEX idx_produto_outbox_status_envio ON tb_produto_out_box (status_envio);
CREATE INDEX idx_produto_outbox_trace_id ON tb_produto_out_box (trace_id);
