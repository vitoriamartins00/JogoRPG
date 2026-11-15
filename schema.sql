/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  vitoriamartins
 * Created: 26 de out. de 2026
 */

-- tabela de jogadores/usuarios
CREATE TABLE IF NOT EXISTS jogador (
    id_jogador SERIAL PRIMARY KEY,
    nome_usuario TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL
);

-- tabela de personagens
CREATE TABLE IF NOT EXISTS personagem(
    id_personagem SERIAL PRIMARY KEY,
    nome TEXT NOT NULL UNIQUE,
    classe TEXT NOT NULL,
    vida INT NOT NULL,
    ataque INT NOT NULL,
    defesa INT NOT NULL,
    nivel INT NOT NULL,
    id_jogador INT NOT NULL,
    CONSTRAINT fk_jogador FOREIGN KEY (id_jogador) REFERENCES jogador(id_jogador)
        ON DELETE CASCADE
);

-- tabela inventario
CREATE TABLE IF NOT EXISTS item(
    id_item SERIAL PRIMARY KEY,
    id_personagem INT NOT NULL,
    nome TEXT NOT NULL,
    tipo TEXT,
    efeito INT NOT NULL,
    nome_save TEXT NOT NULL,
    CONSTRAINT fk_personagem_item FOREIGN KEY (id_personagem)
        REFERENCES personagem(id_personagem) ON DELETE CASCADE
);

-- tabela saves
CREATE TABLE save_jogo (
    id SERIAL PRIMARY KEY,
    nome_save TEXT NOT NULL,
    nivel INT NOT NULL,
    vida_atual INT NOT NULL,
    fase_atual INT NOT NULL,
    id_personagem_fk INT NOT NULL,
    inim_der TEXT,
    nome_heroi TEXT,
    classe_heroi TEXT,
    vida_inim_atual INT NOT NULL,
    CONSTRAINT fk_personagem FOREIGN KEY (id_personagem_fk) REFERENCES personagem(id_personagem) ON DELETE CASCADE
);