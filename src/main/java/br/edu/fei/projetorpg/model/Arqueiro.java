/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.edu.fei.projetorpg.model;

import java.util.ArrayList;

/**
 * representa o protagonista da classe Arqueiro, que possui
 * destreza como atributo específico e utiliza esse valor
 * para calcular o dano de seu poder especial
 *
 * @author vitoriamartins
 */
public class Arqueiro extends Protagonista{

    protected int destreza;

    /**
     * inicializa o arqueiro com os atributos recebidos por
     * parâmetro incluindo seus dados, inventário, inimigos
     * derrotados e valor de destreza
     * @param idPersonagem 
     * @param idJogadorFk 
     * @param nome 
     * @param vida 
     * @param ataque 
     * @param defesa 
     * @param nivel 
     * @param inventario 
     * @param advDerrotados 
     * @param destreza 
     */
    public Arqueiro(int idPersonagem, int idJogadorFk, String nome, int vida,
            int ataque, int defesa, int nivel, ArrayList<Itens> inventario,
            ArrayList<Inimigo> advDerrotados, int destreza){

        super(idPersonagem, idJogadorFk, nome, vida, ataque, defesa, nivel,
                inventario, advDerrotados);
        this.destreza = destreza;
    }

    /**
     * inicializa um novo arqueiro com o nome informado e
     * os valores iniciais de vida, ataque, defesa, nível
     * e destreza, além das listas vazias de itens e inimigos
     * derrotados
     * @param nome 
     */
    public Arqueiro(String nome){
        super(nome, 100, 18, 5, 0, new ArrayList<>(), new ArrayList<>());
        this.destreza = 15;
    }

    /**
     * retorna o valor de destreza do arqueiro.
     * @return 
     */
    public int getDestreza(){
        return this.destreza;
    }

    /**
     * define o valor de destreza do arqueiro
     *
     * @param destreza 
     */
    public void setDestreza(int destreza){
        this.destreza = destreza;
    }

    /**
     * retorna a quantidade de vida inicial do arqueiro
     *
     * @return 
     */
    @Override
    public int getVidaInicial(){
        return 100;
    }

    /**
     * retorna a destreza como atributo de poder do arqueiro
     * @return 
     */
    @Override
    public int getPoder(){
        return this.destreza;
    }

    /**
     * executa o poder especial do arqueiro, calculando o dano
     * a partir do ataque e do dobro da destreza, considerando
     * metade da defesa do alvo no cálculo do dano
     * @param alvo 
     */
    @Override
    public void poderEspecial(Personagem alvo){
        int danoEspecial = (this.ataque + (this.destreza * 2));
        alvo.recebeDano(calcDano(danoEspecial, alvo.getDefesa()/2));
    }
}
