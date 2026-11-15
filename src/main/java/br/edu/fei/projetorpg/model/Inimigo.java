/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model;

/**
 *
 * @author vitoriamartins
 */
public class Inimigo extends Personagem {
    protected int idInimigo;
    
    public Inimigo(int idInimigo, String nome, int vida, int ataque, int defesa,
            int nivel){
        super(nome, vida, ataque, defesa, nivel);
        this.idInimigo = idInimigo;
        
    }
    
    public Inimigo(String nome){
        super(nome, 100, 10, 5, 1);
    }
    
    @Override
    public void atacar(Personagem alvo){
        int danoCalc = this.ataque - alvo.getDefesa();
        
        if(danoCalc <= 0){
            danoCalc = 1;
        }
        
        alvo.recebeDano(danoCalc);
    }
   
}
