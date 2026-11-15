/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.edu.fei.projetorpg.model;

import java.util.ArrayList;

/**
 * Representa o protagonista da classe Guerreiro, que possui
 * força como atributo específico e utiliza esse valor
 * para calcular o dano de seu poder especial.
 *
 * @author vitoriamartins
 */
public class Guerreiro extends Protagonista {

    protected int forca;

    /**
     * inicializa o guerreiro com os atributos recebidos por
     * parâmetro incluindo seus dados, inventário, inimigos
     * derrotados e valor de força
     * @param idPersonagem 
     * @param idJogadorFk 
     * @param nome 
     * @param vida 
     * @param ataque 
     * @param defesa 
     * @param nivel 
     * @param inventario 
     * @param advDerrotado 
     * @param forca 
     */
    public Guerreiro(int idPersonagem, int idJogadorFk, String nome, int vida,
            int ataque, int defesa, int nivel, ArrayList<Itens>
            inventario, ArrayList<Inimigo> advDerrotado, int forca){

        super(idPersonagem, idJogadorFk, nome, vida, ataque, defesa, nivel,
                inventario, advDerrotado);
        this.forca = forca;
    }

    /**
     * inicializa um novo guerreiro com o nome informado e
     * os valores iniciais de vida, ataque, defesa, nível
     * e força, além das listas vazias de itens e inimigos
     * derrotados
     * @param nome 
     */
    public Guerreiro(String nome){
        super(nome, 100, 18, 10, 0, new ArrayList<>(), new ArrayList<>());
        this.forca = 15;
    }

    /**
     * retorna o valor de força do guerreiro
     * @return 
     */
    public int getForca(){
        return this.forca;
    }

    /**
     * define o valor de força do guerreiro
     * @param forca 
     */
    public void setForca(int forca){
        this.forca = forca;
    }

    /**
     * retorna a quantidade de vida inicial do guerreiro
     * @return 
     */
    @Override
    public int getVidaInicial(){
        return 100;
    }

    /**
     * retorna a força como atributo de poder do guerreiro
     * @return 
     */
    @Override
    public int getPoder(){
        return this.forca;
    }

    /**
     * executa o poder especial do guerreiro, calculando o dano
     * a partir do ataque e do dobro da força, considerando
     * a defesa do alvo no cálculo do dano
     * @param alvo 
     */
    @Override
    public void poderEspecial(Personagem alvo){
        int danoEspecial = this.ataque + (this.forca * 2);
        alvo.recebeDano(calcDano(danoEspecial, alvo.getDefesa()));
    }
}
