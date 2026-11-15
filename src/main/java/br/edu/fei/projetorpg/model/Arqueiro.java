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
public class Arqueiro extends Protagonista{
    protected int destreza;
    
    
    public Arqueiro(int idPersonagem, int idJogadorFk, String nome, int vida,
            int ataque, int defesa, int nivel, ArrayList<Itens> inventario,
                ArrayList<Inimigo> advDerrotados, int destreza){
        super(idPersonagem, idJogadorFk, nome, vida, ataque, defesa, nivel,
                inventario, advDerrotados);
        this.destreza = destreza;
    }
    
    public Arqueiro(String nome){
        super(nome, 100, 18, 5, 0, new ArrayList<>(), new ArrayList<>());
        this.destreza = 15;
    }
    
    public int getDestreza(){
        return this.destreza;
    }
    
    public void setDestreza(int destreza){
        this.destreza = destreza;
    }
    
    @Override
    public int getVidaInicial(){
        return 100;
    }
    
    @Override
    public int getPoder(){
        return this.destreza;
    }
    
    @Override
    public void poderEspecial(Personagem alvo){
        int danoEspecial=(this.ataque + (this.destreza * 2));
        alvo.recebeDano(calcDano(danoEspecial, alvo.getDefesa()/2));
    }
}
