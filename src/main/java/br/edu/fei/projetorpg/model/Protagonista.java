/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model;
import java.util.ArrayList;

/**
 * classe abstrata que representa os protagonistas do jogo,
 * armazenando suas informações, inventário e inimigos
 * derrotados, além de definir comportamentos de combate
 * e utilização de itens
 *
 * @author vitoriamartins
 */
public abstract class Protagonista extends Personagem{
    protected int idPersonagem;
    protected int idJogadorFk;
    protected ArrayList<Itens> inventario;
    protected ArrayList<Inimigo> advDerrotados;
    
    /**
     * inicializa o protagonista com os atributos recebidos
     * por parâmetro, incluindo seus identificadores,
     * inventário e lista de inimigos derrotados
     * @param idPersonagem 
     * @param idJogadorFk 
     * @param nome 
     * @param vida 
     * @param ataque 
     * @param defesa 
     * @param nivel 
     * @param inventario 
     * @param advDerrotados 
     */
    public Protagonista(int idPersonagem, int idJogadorFk, String nome, int vida,
            int ataque, int defesa, int nivel, ArrayList<Itens> 
                    inventario, ArrayList<Inimigo> advDerrotados){
        super(nome, vida, ataque, defesa, nivel);
        this.idPersonagem = idPersonagem;
        this.idJogadorFk = idJogadorFk;
        this.inventario = (inventario != null) ? inventario: new ArrayList<>();
        this.advDerrotados = (advDerrotados != null) ? advDerrotados : new ArrayList<>();
        
    }
    
    /**
     * inicializa um novo protagonista com os atributos
     * básicos e as listas de itens e inimigos derrotados,
     * sem informar seus identificadores
     * @param nome 
     * @param vida 
     * @param ataque 
     * @param defesa 
     * @param nivel 
     * @param inventario 
     * @param advDerrotados 
     */
    public Protagonista(String nome, int vida, int ataque, int defesa, int nivel,
            ArrayList<Itens> inventario, ArrayList<Inimigo> advDerrotados){
        super(nome, vida, ataque, defesa, nivel);
        this.idPersonagem = 0;
        this.inventario = (inventario != null) ? inventario : new ArrayList<>();
        this.advDerrotados = (advDerrotados != null) ? advDerrotados : new ArrayList<>();
    }
    
    /**
     * retorna o identificador do protagonista
     * @return 
     */
    public int getIdPersonagem() {
        return idPersonagem;
    }
    
     /**
     * define o identificador do protagonista
     * @param idPersonagem 
     */
    public void setIdPersonagem(int idPersonagem) {
        this.idPersonagem = idPersonagem;
    }
    
    /**
     * retorna o identificador do jogador associado
     * @return 
     */
    public int getIdJogadorFk() {
        return idJogadorFk;
    }

    /**
     * define o identificador do jogador associado
     * @param idJogadorFk 
     */
    public void setIdJogadorFk(int idJogadorFk) {
        this.idJogadorFk = idJogadorFk;
    }
    
    /**
     * retorna a lista de itens do inventário
     * @return 
     */
    public ArrayList<Itens> getInventario(){
        return this.inventario;
    }
    
     /**
     * retorna a lista de inimigos derrotados
     * @return 
     */
    public ArrayList<Inimigo> getAdvDerrotados(){
        return this.advDerrotados;
    }
    
    /**
     * define a lista de inimigos derrotados
     * @param advDerrotados 
     */
    public void setAdvDerrotados(ArrayList<Inimigo> advDerrotados){
        this.advDerrotados = advDerrotados;
    }
    
     /**
     * adiciona um item ao inventário do protagonista,
     * respeitando o limite máximo de três itens
     * @param novoItem 
     * @return 
     */
    public boolean addItens (Itens novoItem){
        if(this.inventario.size() < 3){
            this.inventario.add(novoItem);
            return true;
        }
        
        return false;
    }
    
    /**
     * registra a vitória sobre um inimigo e tenta
     * adicionar o item recebido ao inventário
     * @param inimigoDerrotado 
     * @param nvItem 
     */
    public void ganharItemInimigo(Inimigo inimigoDerrotado, Itens nvItem){
        regVitoria(inimigoDerrotado);
        addItens(nvItem);
    }
    
     /**
     * registra um inimigo derrotado, evitando adicionar
     * novamente a mesma referência à lista
     * @param inimigo 
     */
    public void regVitoria(Inimigo inimigo){
        if(inimigo != null && !this.advDerrotados.contains(inimigo)){
            this.advDerrotados.add(inimigo);
        }
        
    }
    
    /**
     * utiliza um item do inventário, aplicando seu efeito
     * ao protagonista e removendo o item utilizado
     * @param indItem 
     * @return 
     */
    public String usarItem(int indItem){
        if(indItem >= 0 && indItem < this.inventario.size()){
            Itens item = this.inventario.get(indItem);
            item.concederEfeito(this);
            this.inventario.remove(indItem);
            return "usou " + item.getNome();
        }
        return "item invalido";
    }
    
    /**
     * retorna o atributo de poder específico do protagonista,
     * conforme a implementação de cada classe
     * @return 
     */
    public abstract int getPoder();
    
    /**
     * executa um ataque contra o personagem informado,
     * calculando o dano a partir do ataque do protagonista
     * e da defesa do alvo
     * @param alvo 
     */
    @Override
    public void atacar(Personagem alvo){
        int danoFinal = calcDano(this.ataque, alvo.getDefesa());
        alvo.recebeDano(danoFinal);
    }
    
    /**
     * reduz a vida do protagonista considerando sua defesa,
     * impedindo que o dano calculado seja negativo
     * @param danoInimigo 
     */
    public void danoDefesa(int danoInimigo){
        int danoFinal = danoInimigo - this.defesa;
        
        if(danoFinal < 0){
            danoFinal = 0;
        }
        
        this.vida -= danoFinal;
    }
    
    /**
     * define o poder especial que deverá ser implementado
     * pelas classes específicas de protagonistas
     * @param alvo 
     */
    public abstract void poderEspecial(Personagem alvo);
    public abstract int getVidaInicial();
}
