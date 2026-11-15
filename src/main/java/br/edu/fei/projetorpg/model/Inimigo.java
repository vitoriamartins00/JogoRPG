/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.edu.fei.projetorpg.model;

/**
 * representa os inimigos enfrentados durante a partida
 * armazenando seu identificador e implementando a lógica
 * de ataque contra outros personagens
 *
 * @author vitoriamartins
 */
public class Inimigo extends Personagem {

    protected int idInimigo;

    /**
     * inicializa o inimigo com os atributos recebidos por
     * parâmetro incluindo seu identificador, nome, vida,
     * ataque, defesa e nível
     *
     * @param idInimigo 
     * @param nome 
     * @param vida 
     * @param ataque 
     * @param defesa 
     * @param nivel 
     */
    public Inimigo(int idInimigo, String nome, int vida, int ataque,
            int defesa, int nivel){

        super(nome, vida, ataque, defesa, nivel);
        this.idInimigo = idInimigo;
    }

    /**
     * inicializa um novo inimigo com o nome informado e
     * os valores iniciais de vida, ataque, defesa e nível
     * @param nome 
     */
    public Inimigo(String nome){
        super(nome, 100, 10, 5, 1);
    }

    /**
     * executa o ataque do inimigo, calculando o dano a partir
     * de seu ataque e da defesa do alvo, garantindo um dano
     * mínimo de 1 ponto
     * @param alvo 
     */
    @Override
    public void atacar(Personagem alvo){

        int danoCalc = this.ataque - alvo.getDefesa();

        if(danoCalc <= 0){
            danoCalc = 1;
        }

        alvo.recebeDano(danoCalc);
    }
}
