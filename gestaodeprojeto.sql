CREATE DATABASE gestaodeprojeto;
USE gestaodeprojeto;

CREATE TABLE gestaodeprojeto.colaboradores(
id INT AUTO_INCREMENT PRIMARY KEY,
cpf VARCHAR(11) NOT NULL UNIQUE KEY,
usuario VARCHAR(30) NOT NULL UNIQUE KEY,
senha VARCHAR(255) NOT NULL,
nome VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE KEY,
cargo VARCHAR(50) NOT NULL
);

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
data_fim DATE,
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

