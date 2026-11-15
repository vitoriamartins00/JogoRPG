/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model;
import java.util.ArrayList;
/**
 *
 * @author vitoriamartins
 */
public class Mago extends Protagonista{
    protected int mana;
    
    public Mago(int idPersonagem, int idJogadorFk, String nome, int vida, 
            int ataque, int defesa, int nivel, ArrayList<Itens> 
                    inventario, ArrayList<Inimigo> advDerrotado, int mana){
        super(idPersonagem, idJogadorFk, nome, vida, ataque, defesa, nivel,
                inventario, advDerrotado);
        this.mana = mana;
    }
    
    public Mago(String nome){
        super(nome, 80, 20, 15, 0, new ArrayList<>(), new ArrayList<>());
        this.mana = 15;
    }
    
    public int getMana(){
        return this.mana;
    }
    
    public void setMana(int mana){
        this.mana = mana;
    }
    
    @Override
    public int getVidaInicial(){
        return 80;
    }
    
    @Override
    public int getPoder(){
        return this.mana;
    }
    
    @Override 
    public void poderEspecial(Personagem alvo){
        int danoEspecial = (this.ataque + (this.mana * 2));
        alvo.recebeDano(calcDano(danoEspecial, alvo.getDefesa()/2));
    }
}
