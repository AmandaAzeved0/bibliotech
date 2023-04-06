ALTER TABLE reservas
    RENAME TO solicitacoes_de_emprestimo;

ALTER TABLE solicitacoes_de_emprestimo ADD COLUMN  status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE';