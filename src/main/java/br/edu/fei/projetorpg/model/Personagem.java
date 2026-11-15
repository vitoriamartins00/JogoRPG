/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.edu.fei.projetorpg.model;

/**
 * Classe abstrata que representa os personagens do jogo,
 * armazenando seus atributos básicos e definindo os métodos
 * comuns de combate, dano e verificação de derrota.
 *
 * @author vitoriamartins
 */
public abstract class Personagem {

    protected String nome;
    protected int vida;
    protected int ataque;
    protected int defesa;
    protected int nivel;

    /**
     * inicializa o personagem com os atributos recebidos
     * por parâmetro
     * @param nome 
     * @param vida 
     * @param ataque
     * @param defesa 
     * @param nivel
     */
    public Personagem(String nome, int vida, int ataque, int defesa, int nivel){
        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
    }

    /**
     * retorna o nome do personagem
     * @return 
     */
    public String getNome() {
        return nome;
    }

    /**
     * define o nome do personagem
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * retorna a quantidade de vida do personagem
     * @return 
     */
    public int getVida() {
        return vida;
    }

    /**
     * define a quantidade de vida do personagem
     * @param vida 
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * retorna o valor de ataque do personagem
     * @return 
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * define o valor de ataque do personagem
     * @param ataque 
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * retorna o valor de defesa do personagem
     * @return 
     */
    public int getDefesa() {
        return defesa;
    }

    /**
     * define o valor de defesa do personagem
     * @param defesa 
     */
    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    /**
     * retorna o nível do personagem.
     * @return 
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * define o nível do personagem
     * @param nivel 
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * calcula o dano de um ataque considerando a defesa
     * do alvo, garantindo um dano mínimo de 1 ponto
     * @param ataque 
     * @param defesaAlvo 
     * @return 
     */
    public int calcDano(int ataque, int defesaAlvo){
        int dano = ataque - defesaAlvo;
        return (dano < 1) ? 1: dano;
    }

    /**
     * reduz a vida do personagem de acordo com o dano
     * recebido, impedindo que fique abaixo de zero
     * e verificando se foi derrotado
     * @param dano 
     * @return 
     */
    public boolean recebeDano(int dano){
        this.vida -= dano;

        if(this.vida < 0) this.vida = 0;
        return isDerrotado();
    }

    /**
     * verifica se o personagem foi derrotado com base
     * na quantidade de vida restante
     * @return 
     */
    public boolean isDerrotado(){
        return this.vida <= 0;
    }

    /**
     * define o comportamento de ataque que deverá ser
     * implementado pelas subclasses concretas
     * @param alvo 
     */
    public abstract void atacar(Personagem alvo);
}
