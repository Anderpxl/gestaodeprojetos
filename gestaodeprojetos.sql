CREATE DATABASE gestaodeprojeto;
USE gestaodeprojeto;

CREATE TABLE gestaodeprojeto.colaboradores(
id INT AUTO_INCREMENT PRIMARY KEY,
cpf VARCHAR(11) NOT NULL UNIQUE KEY,
usuario VARCHAR(30) NOT NULL UNIQUE KEY,
senha VARCHAR(255) NOT NULL,
nome VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE KEY,
perfil VARCHAR(50) NOT NULL
);

ALTER TABLE gestaodeprojeto.colaboradores CHANGE cargo perfil VARCHAR(50) NOT NULL;

CREATE TABLE gestaodeprojeto.equipes(
id INT AUTO_INCREMENT PRIMARY KEY,
nome_equipe VARCHAR(100) NOT NULL,
gerente_id INT NOT NULL,
lider_id INT NOT NULL,

FOREIGN KEY (gerente_id) REFERENCES colaboradores(id),
FOREIGN KEY (lider_id) REFERENCES colaboradores(id)
);

CREATE TABLE gestaodeprojeto.projetos(
id INT AUTO_INCREMENT PRIMARY KEY,
nome_projeto VARCHAR(255) NOT NULL,
descricao VARCHAR(5000),
data_inicio DATE,
data_final DATE,
equipe_id INT NOT NULL,

FOREIGN KEY (equipe_id) REFERENCES equipes(id)
);

CREATE TABLE gestaodeprojeto.historico_colaboradores(
id INT AUTO_INCREMENT PRIMARY KEY,
cpf VARCHAR(11) NOT NULL UNIQUE KEY,
usuario VARCHAR(30) NOT NULL UNIQUE KEY,
senha VARCHAR(255) NOT NULL,
nome VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE KEY,
cargo VARCHAR(50) NOT NULL
);

CREATE TABLE gestaodeprojeto.equipe_colaboradores(
	equipe_id INT NOT NULL,
    colaborador_id INT NOT NULL,
    
    PRIMARY KEY (equipe_id, colaborador_id),
    
    FOREIGN KEY (equipe_id) REFERENCES equipes(id),
    FOREIGN KEY (colaborador_id) REFERENCES colaboradores(id)
);

DELIMITER $
CREATE TRIGGER tr_usuarios_contas BEFORE DELETE ON gestaodeprojeto.colaboradores FOR EACH ROW
BEGIN
    INSERT INTO gestaodeprojeto.historico_colaboradores (id, cpf, usuario, senha, nome, email, cargo) VALUES (OLD.id, OLD.cpf, OLD.usuario, OLD.senha, OLD.nome, OLD.email, OLD.cargo);
END$
DELIMITER ;

DELIMITER &
CREATE TRIGGER tr_senhas_emails_usuarios BEFORE UPDATE ON gestaodeprojeto.colaboradores FOR EACH ROW
BEGIN
    INSERT INTO gestaodeprojeto.historico_senhas_emails (id, senha, nome, email, cargo) VALUES (OLD.id, OLD.senha, OLD.nome, OLD.email, OLD.cargo);
END&
DELIMITER ;

SELECT * FROM gestaodeprojeto.colaboradores;

UPDATE gestaodeprojeto.pessoas SET cpf = '13111111111', usuario = 'andersonn', senha = 'ander3211', nome = 'Anderson Elias', email = 'anderson1.elias@hotmail.com', cargo = 'Analista Pl' WHERE id = 4;

UPDATE gestaodeprojeto.pessoas SET senha = 'ander321', nome = 'Anderson Elias de Souza Lima', cargo = 'Analista Pl' WHERE id = 2;

DELETE FROM gestaodeprojeto.pessoas WHERE id = 4;

DROP TABLE gestaodeprojeto.historico_senhas_emails;


