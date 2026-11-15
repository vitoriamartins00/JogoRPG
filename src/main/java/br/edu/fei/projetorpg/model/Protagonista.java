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
public abstract class Protagonista extends Personagem{
    protected int idPersonagem;
    protected int idJogadorFk;
    protected ArrayList<Itens> inventario;
    protected ArrayList<Inimigo> advDerrotados;
    
    public Protagonista(int idPersonagem, int idJogadorFk, String nome, int vida,
            int ataque, int defesa, int nivel, ArrayList<Itens> 
                    inventario, ArrayList<Inimigo> advDerrotados){
        super(nome, vida, ataque, defesa, nivel);
        this.idPersonagem = idPersonagem;
        this.idJogadorFk = idJogadorFk;
        this.inventario = (inventario != null) ? inventario: new ArrayList<>();
        this.advDerrotados = (advDerrotados != null) ? advDerrotados : new ArrayList<>();
        
    }
    
    public Protagonista(String nome, int vida, int ataque, int defesa, int nivel,
            ArrayList<Itens> inventario, ArrayList<Inimigo> advDerrotados){
        super(nome, vida, ataque, defesa, nivel);
        this.idPersonagem = 0;
        this.inventario = (inventario != null) ? inventario : new ArrayList<>();
        this.advDerrotados = (advDerrotados != null) ? advDerrotados : new ArrayList<>();
    }

    public int getIdPersonagem() {
        return idPersonagem;
    }

    public void setIdPersonagem(int idPersonagem) {
        this.idPersonagem = idPersonagem;
    }

    public int getIdJogadorFk() {
        return idJogadorFk;
    }

    public void setIdJogadorFk(int idJogadorFk) {
        this.idJogadorFk = idJogadorFk;
    }
    
    public ArrayList<Itens> getInventario(){
        return this.inventario;
    }
    
    public ArrayList<Inimigo> getAdvDerrotados(){
        return this.advDerrotados;
    }
    
    public void setAdvDerrotados(ArrayList<Inimigo> advDerrotados){
        this.advDerrotados = advDerrotados;
    }
    
    public boolean addItens (Itens novoItem){
        if(this.inventario.size() < 3){
            this.inventario.add(novoItem);
            return true;
        }
        
        return false;
    }
    
    public void ganharItemInimigo(Inimigo inimigoDerrotado, Itens nvItem){
        regVitoria(inimigoDerrotado);
        addItens(nvItem);
    }
    
    public void regVitoria(Inimigo inimigo){
        if(inimigo != null && !this.advDerrotados.contains(inimigo)){
            this.advDerrotados.add(inimigo);
        }
        
    }
    
    public String usarItem(int indItem){
        if(indItem >= 0 && indItem < this.inventario.size()){
            Itens item = this.inventario.get(indItem);
            item.concederEfeito(this);
            this.inventario.remove(indItem);
            return "usou " + item.getNome();
        }
        return "item invalido";
    }
    
    public abstract int getPoder();
    
    @Override
    public void atacar(Personagem alvo){
        int danoFinal = calcDano(this.ataque, alvo.getDefesa());
        alvo.recebeDano(danoFinal);
    }
    
    public void danoDefesa(int danoInimigo){
        int danoFinal = danoInimigo - this.defesa;
        
        if(danoFinal < 0){
            danoFinal = 0;
        }
        
        this.vida -= danoFinal;
    }

    public abstract void poderEspecial(Personagem alvo);
    public abstract int getVidaInicial();
}
