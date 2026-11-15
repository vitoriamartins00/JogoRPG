/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model;

import java.util.Random;

/**
 *
 * @author vitoriamartins
 */
public class Itens {
    private String nome;
    private String desc;
    private String tipo;
    private int pontoEfeito;
    private int idItem;
    
    public Itens(String nome, String desc, String tipo, int pontoEfeito, int idItem){
        this.nome = nome;
        this.desc = desc;
        this.tipo = tipo;
        this.pontoEfeito = pontoEfeito;
    }
    
    public Itens(String nome, String desc, String tipo, int pontoEfeito){
        this.nome = nome;
        this.desc = desc;
        this.tipo = tipo;
        this.pontoEfeito = pontoEfeito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public int getPontoEfeito() {
        return pontoEfeito;
    }

    public void setPontoEfeito(int pontoEfeito) {
        this.pontoEfeito = pontoEfeito;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }
    
    
    
    public void concederEfeito(Personagem alvo){
        if(alvo.getVida() == 0){
            return;
        }
        switch (this.tipo) {
            case "CURA":
                int nvVida = alvo.getVida() + this.pontoEfeito;
                alvo.setVida(nvVida);
                break;
            case "ATAQUE":
                int nvAtaque = alvo.getAtaque() + this.pontoEfeito;
                alvo.setAtaque(nvAtaque);
                break;
            case "DEFESA":
                int nvDefesa = alvo.getDefesa() + this.pontoEfeito;
                alvo.setDefesa(nvDefesa);
                break;
            default:
                break;
        }
    }
    
    public static Itens gerarItem(){
        Random random = new Random();
        
        int sortItem = random.nextInt(4 );
        
        switch(sortItem){
            case 0: 
                return new Itens("pocao", "cura ferimentos", "CURA", 25, 0);
            case 1:
                return new Itens("espada", "aumenta ataque", "ATAQUE", 5, 0);
            case 2:
                return new Itens("escudo", "reforca defesa", "DEFESA", 4, 0);
            default:
                return new Itens("pocao cura", "ferimentos leves", "CURA", 12, 0);
        }
    }  
}
