insert into perfis (sigla, nome) values ('ADM', 'Administrador');
insert into perfis (sigla, nome) values ('USR', 'Usuário');
insert into perfis (sigla, nome) values ('BIB', 'Bibliotecário');

insert into tipos_de_penalidade (descricao, dias_de_penalidade, com_bloqueio) values ('Atraso inferior a 10 dias', 2, false);
insert into tipos_de_penalidade (descricao, dias_de_penalidade, com_bloqueio) values ('Atraso superior a 10 dias e menor que 20', 7, false);
insert into tipos_de_penalidade (descricao, dias_de_penalidade, com_bloqueio) values ('Atraso superior a 20 dias e menor que 30', 14, false);
insert into tipos_de_penalidade (descricao, dias_de_penalidade, com_bloqueio) values ('Atraso superior a 30 dias e inferior a 40', 21, false);
insert into tipos_de_penalidade (descricao, dias_de_penalidade, com_bloqueio) values ('Atraso superior a 40 dias', 28, false);
insert into tipos_de_penalidade (descricao, dias_de_penalidade, com_bloqueio) values ('Perda ou dano do livro', 28, true);

insert into estoque_livros (titulo, genero, quantidade, autor) values ('O Senhor dos Anéis', 'Fantasia', 1, 'J.R.R. Tolkien');
insert into estoque_livros (titulo, genero, quantidade, autor) values ('O Hobbit', 'Fantasia', 1, 'J.R.R. Tolkien');
insert into estoque_livros (titulo, genero, quantidade, autor) values ('O Silmarillion', 'Fantasia', 1, 'J.R.R. Tolkien');
insert into estoque_livros (titulo, genero, quantidade, autor) values ('O Código da Vinci', 'Ficção', 1, 'Dan Brown');
insert into estoque_livros (titulo, genero, quantidade, autor) values ('O Diário de Anne Frank', 'Biografia', 1, 'Anne Frank');
insert into estoque_livros (titulo, genero, quantidade, autor) values ('O Pequeno Príncipe', 'Infantil', 1, 'Antoine de Saint-Exupéry');


insert into livros (disponivel, estado, estoque_id) values (true, 'Bom', 1);
insert into livros (disponivel, estado, estoque_id) values (true, 'Bom', 2);
insert into livros (disponivel, estado, estoque_id) values (true, 'Bom', 3);
insert into livros (disponivel, estado, estoque_id) values (true, 'Bom', 4);
insert into livros (disponivel, estado, estoque_id) values (true, 'Danificado', 5);
insert into livros (disponivel, estado, estoque_id) values (true, 'Perdido', 6);