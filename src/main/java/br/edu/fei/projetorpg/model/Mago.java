/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.edu.fei.projetorpg.model;

import java.util.ArrayList;

/**
 * representa o protagonista da classe Mago, que possui
 * mana como atributo específico e utiliza esse valor
 * para calcular o dano de seu poder especial
 * @author vitoriamartins
 */
public class Mago extends Protagonista {

    protected int mana;

    /**
     * inicializa o mago com os atributos recebidos por
     * parâmetro, incluindo seus dados, inventário, inimigos
     * derrotados e valor de mana
     * @param idPersonagem 
     * @param idJogadorFk 
     * @param nome 
     * @param vida 
     * @param ataque 
     * @param defesa 
     * @param nivel 
     * @param inventario 
     * @param advDerrotado 
     * @param mana 
     */
    public Mago(int idPersonagem, int idJogadorFk, String nome, int vida,
            int ataque, int defesa, int nivel, ArrayList<Itens>
            inventario, ArrayList<Inimigo> advDerrotado, int mana){

        super(idPersonagem, idJogadorFk, nome, vida, ataque, defesa, nivel,
                inventario, advDerrotado);
        this.mana = mana;
    }

    /**
     * inicializa um novo mago com o nome informado e
     * os valores iniciais de vida, ataque, defesa, nível
     * e mana, além das listas vazias de itens e inimigos
     * derrotados
     * @param nome 
     */
    public Mago(String nome){
        super(nome, 80, 20, 15, 0, new ArrayList<>(), new ArrayList<>());
        this.mana = 15;
    }

    /**
     * retorna o valor de mana do mago
     * @return 
     */
    public int getMana(){
        return this.mana;
    }

    /**
     * define o valor de mana do mago
     * @param mana 
     */
    public void setMana(int mana){
        this.mana = mana;
    }

    /**
     * retorna a quantidade de vida inicial do mago
     * @return 
     */
    @Override
    public int getVidaInicial(){
        return 80;
    }

    /**
     * retorna a mana como atributo de poder do mago
     * @return 
     */
    @Override
    public int getPoder(){
        return this.mana;
    }

    /**
     * executa o poder especial do mago calculando o dano
     * a partir do ataque e do dobro da mana, considerando
     * metade da defesa do alvo no cálculo do dano
     * @param alvo 
     */
    @Override
    public void poderEspecial(Personagem alvo){
        int danoEspecial = (this.ataque + (this.mana * 2));
        alvo.recebeDano(calcDano(danoEspecial, alvo.getDefesa()/2));
    }
}
