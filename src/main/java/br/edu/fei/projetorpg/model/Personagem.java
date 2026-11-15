/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model;

/**
 *
 * @author vitoriamartins
 */
public abstract class Personagem {
    protected String nome;
    protected int vida;
    protected int ataque;
    protected int defesa;
    protected int nivel;
    
    
    public Personagem(String nome, int vida, int ataque, int defesa, int nivel){
        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;     
        
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    public int calcDano(int ataque, int defesaAlvo){
        int dano = ataque - defesaAlvo;
        return (dano < 1) ? 1: dano;
    }
    
    public boolean recebeDano(int dano){
        this.vida -= dano;
        
        if(this.vida < 0) this.vida = 0;
        return isDerrotado();
    }  
    
    public boolean isDerrotado(){
        return this.vida <= 0;
    }
    
    public abstract void atacar(Personagem alvo);
    
}
