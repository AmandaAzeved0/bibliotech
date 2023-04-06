

CREATE TABLE estoque_livros(
                               id SERIAL PRIMARY KEY,
                               titulo VARCHAR(100) NOT NULL,
                               genero VARCHAR(100) NOT NULL,
                               quantidade SMALLINT NOT NULL,
                               autor VARCHAR(100) NOT NULL);

CREATE TABLE livros (
                        id SERIAL PRIMARY KEY,
                        disponivel BOOLEAN NOT NULL DEFAULT TRUE,
                        estado VARCHAR(10) NOT NULL,
                        estoque_id INTEGER NOT NULL,
                        FOREIGN KEY (estoque_id) REFERENCES estoque_livros(id));

CREATE TABLE perfis(
             id SERIAL PRIMARY KEY,
             sigla VARCHAR(10) NOT NULL,
             nome VARCHAR(100) NOT NULL);

CREATE TABLE usuarios (
             id SERIAL PRIMARY KEY,
             nome VARCHAR(100) NOT NULL,
             cpf VARCHAR(11) NOT NULL,
             email VARCHAR(100) NOT NULL,
             senha VARCHAR(100) NOT NULL,
             status_de_bloqueio BOOLEAN NOT NULL DEFAULT FALSE,
             perfil_id INTEGER NOT NULL,
             FOREIGN KEY (perfil_id) REFERENCES perfis(id));

CREATE TABLE emprestimos (
             id SERIAL PRIMARY KEY,
             data_emprestimo_inicio DATE NOT NULL,
             data_emprestimo_fim DATE NOT NULL,
             data_devolucao DATE NOT NULL,
             usuario_id INTEGER NOT NULL,
             livro_id INTEGER NOT NULL,
             FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
             FOREIGN KEY (livro_id) REFERENCES livros(id));

CREATE TABLE reservas (
             id SERIAL PRIMARY KEY,
             data_reserva DATE NOT NULL,
             usuario_id INTEGER NOT NULL,
             livro_id INTEGER NOT NULL,
             FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
             FOREIGN KEY (livro_id) REFERENCES livros(id));
--
CREATE TABLE tipos_de_penalidade (
             id SERIAL PRIMARY KEY,
             descricao VARCHAR(100) NOT NULL,
             dias_de_penalidade SMALLINT NOT NULL,
             com_bloqueio BOOLEAN NOT NULL );

CREATE TABLE penalidade_usuario (
             id SERIAL PRIMARY KEY,
             data_penalidade DATE NOT NULL,
             usuario_id INTEGER NOT NULL,
             tipo_de_penalidade_id INTEGER NOT NULL,
             FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
             FOREIGN KEY (tipo_de_penalidade_id) REFERENCES tipos_de_penalidade(id));
