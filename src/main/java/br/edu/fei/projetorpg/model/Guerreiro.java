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
public class Guerreiro extends Protagonista {
    protected int forca;
    
    public Guerreiro(int idPersonagem, int idJogadorFk, String nome, int vida, 
            int ataque, int defesa, int nivel, ArrayList<Itens> 
                    inventario, ArrayList<Inimigo> advDerrotado, int forca){
        super(idPersonagem, idJogadorFk, nome, vida, ataque, defesa, nivel,
                inventario, advDerrotado);
        this.forca = forca;
    }
    
    public Guerreiro(String nome){
        super(nome, 100, 18, 10, 0, new ArrayList<>(), new ArrayList<>());
        this.forca = 15;
    }
    
    public int getForca(){
        return this.forca;
    }
    
    public void setForca(int forca){
        this.forca = forca;
    }
    
    @Override
    public int getVidaInicial(){
        return 100;
    }
    
    @Override
    public int getPoder(){
        return this.forca;
    }
    
    @Override
    public void poderEspecial(Personagem alvo){
        int danoEspecial = this.ataque + (this.forca * 2);
        alvo.recebeDano(calcDano(danoEspecial, alvo.getDefesa()));
    }
}
