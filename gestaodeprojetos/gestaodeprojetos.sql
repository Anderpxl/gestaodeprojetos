CREATE DATABASE gestaodeprojeto;
USE gestaodeprojeto;

INSERT INTO gestaodeprojeto.colaboradores (cpf, usuario, senha, nome, email, perfil) VALUES ('11111111111', 'ander', 'password', 'Anderson Elias', 'ander@gmail.com', 'ADMINISTRADOR');

ALTER TABLE gestaodeprojeto.projetos MODIFY equipes_id INT NULL;

SELECT * FROM gestaodeprojeto.colaboradores;
SELECT * FROM gestaodeprojeto.equipes;
SELECT * FROM gestaodeprojeto.projetos;

CREATE TABLE gestaodeprojeto.colaboradores(
id INT AUTO_INCREMENT PRIMARY KEY,
cpf VARCHAR(11) NOT NULL UNIQUE KEY,
usuario VARCHAR(30) NOT NULL UNIQUE KEY,
senha VARCHAR(255) NOT NULL,
nome VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE KEY,
perfil ENUM('ADMINISTRADOR','GERENTE','COLABORADOR')
);

CREATE TABLE gestaodeprojeto.equipes(
id INT AUTO_INCREMENT PRIMARY KEY,
nome_equipe VARCHAR(100) NOT NULL,
gerente_id INT NULL,

FOREIGN KEY (gerente_id) REFERENCES colaboradores(id)
);

CREATE TABLE gestaodeprojeto.equipe_colaborador(
equipes_id INT,
colaboradores_id INT,

PRIMARY KEY (equipes_id, colaboradores_id),

FOREIGN KEY (equipes_id) REFERENCES equipes(id),
FOREIGN KEY (colaboradores_id) REFERENCES colaboradores(id)
);

CREATE TABLE gestaodeprojeto.projetos(
id INT AUTO_INCREMENT PRIMARY KEY,
nome_projeto VARCHAR(255) NOT NULL,
descricao VARCHAR(5000),
data_inicio DATE,
data_final DATE,
equipes_id INT NULL,

FOREIGN KEY (equipes_id) REFERENCES equipes(id)
);

CREATE TABLE gestaodeprojeto.historico_colaboradores(
id INT AUTO_INCREMENT PRIMARY KEY,
cpf VARCHAR(11) NOT NULL UNIQUE KEY,
usuario VARCHAR(30) NOT NULL UNIQUE KEY,
senha VARCHAR(255) NOT NULL,
nome VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE KEY,
perfil VARCHAR(50) NOT NULL
);

CREATE TABLE gestaodeprojeto.equipe_colaboradores(
	equipes_id INT NOT NULL,
    colaboradores_id INT NOT NULL,
    
    PRIMARY KEY (equipes_id, colaboradores_id),
    
    FOREIGN KEY (equipes_id) REFERENCES equipes(id),
    FOREIGN KEY (colaboradores_id) REFERENCES colaboradores(id)
);

DELIMITER $
CREATE TRIGGER tr_usuarios_contas BEFORE DELETE ON gestaodeprojeto.colaboradores FOR EACH ROW
BEGIN
    INSERT INTO gestaodeprojeto.historico_colaboradores (id, cpf, usuario, senha, nome, email, perfil) VALUES (OLD.id, OLD.cpf, OLD.usuario, OLD.senha, OLD.nome, OLD.email, OLD.perfil);
END$
DELIMITER ;

DELIMITER &
CREATE TRIGGER tr_senhas_emails_usuarios BEFORE UPDATE ON gestaodeprojeto.colaboradores FOR EACH ROW
BEGIN
    INSERT INTO gestaodeprojeto.historico_senhas_emails (id, senha, nome, email, perfil) VALUES (OLD.id, OLD.senha, OLD.nome, OLD.email, OLD.perfil);
END&
DELIMITER ;

UPDATE gestaodeprojeto.pessoas SET cpf = '13111111111', usuario = 'andersonn', senha = 'ander3211', nome = 'Anderson Elias', email = 'anderson1.elias@hotmail.com', cargo = 'Analista Pl' WHERE id = 4;

UPDATE gestaodeprojeto.pessoas SET senha = 'ander321', nome = 'Anderson Elias de Souza Lima', cargo = 'Analista Pl' WHERE id = 2;

DELETE FROM gestaodeprojeto.pessoas WHERE id = 4;

DROP TABLE gestaodeprojeto.historico_colaboradores;


