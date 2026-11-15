/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.edu.fei.projetorpg.model;

import java.util.Random;

/**
 * representa os itens utilizados durante a partida
 * armazenando suas informações e permitindo aplicar
 * efeitos aos personagens e gerar itens aleatórios
 * @author vitoriamartins
 */
public class Itens {

    private String nome;
    private String desc;
    private String tipo;
    private int pontoEfeito;
    private int idItem;

    /**
     * inicializa um item com as informações recebidas
     * por parâmetro incluindo seu identificador
     * @param nome 
     * @param desc 
     * @param tipo
     * @param pontoEfeito 
     * @param idItem 
     */
    public Itens(String nome, String desc, String tipo,
            int pontoEfeito, int idItem){
        this.nome = nome;
        this.desc = desc;
        this.tipo = tipo;
        this.pontoEfeito = pontoEfeito;
    }

    /**
     * inicializa um item com seu nome, descrição, tipo
     * e quantidade de pontos de efeito.
     *
     * @param nome 
     * @param desc 
     * @param tipo 
     * @param pontoEfeito 
     */
    public Itens(String nome, String desc, String tipo, int pontoEfeito){
        this.nome = nome;
        this.desc = desc;
        this.tipo = tipo;
        this.pontoEfeito = pontoEfeito;
    }

    /**
     * retorna o nome do item
     * @return 
     */
    public String getNome() {
        return nome;
    }

    /**
     * define o nome do item
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * retorna a descrição do item
     * @return 
     */
    public String getDesc() {
        return desc;
    }

    /**
     * define a descrição do item
     * @param desc 
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * retorna o tipo de efeito do item
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * define o tipo de efeito do item
     * @param tipo 
     */
    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    /**
     * retorna a quantidade de pontos de efeito do item
     * @return 
     */
    public int getPontoEfeito() {
        return pontoEfeito;
    }

    /**
     * define a quantidade de pontos de efeito do item
     * @param pontoEfeito 
     */
    public void setPontoEfeito(int pontoEfeito) {
        this.pontoEfeito = pontoEfeito;
    }

    /**
     * retorna o identificador do item
     * @return 
     */
    public int getIdItem() {
        return idItem;
    }

    /**
     * define o identificador do item
     * @param idItem 
     */
    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    /**
     * aplica o efeito do item ao personagem informado
     * aumentando sua vida, ataque ou defesa de acordo
     * com o tipo e a quantidade de pontos do item
     * @param alvo 
     */
    public void concederEfeito(Personagem alvo){

        // impede a utilização do efeito caso a vida seja zero
        if(alvo.getVida() == 0){
            return;
        }

        // identifica o tipo de efeito que será aplicado
        switch (this.tipo) {

            case "CURA":
                // soma os pontos de cura à vida atual
                int nvVida = alvo.getVida() + this.pontoEfeito;
                alvo.setVida(nvVida);
                break;

            case "ATAQUE":
                // aumenta o ataque do personagem
                int nvAtaque = alvo.getAtaque() + this.pontoEfeito;
                alvo.setAtaque(nvAtaque);
                break;

            case "DEFESA":
                // aumenta a defesa do personagem
                int nvDefesa = alvo.getDefesa() + this.pontoEfeito;
                alvo.setDefesa(nvDefesa);
                break;

            default:
                // não aplica efeitos para tipos não reconhecidos
                break;
        }
    }

    /**
     * gera aleatoriamente um item entre as opções
     * disponíveis, com efeitos de cura, ataque ou defesa
     * @return 
     */
    public static Itens gerarItem(){

        // cria o gerador de números aleatórios
        Random random = new Random();

        // sorteia um número inteiro entre 0 e 3
        int sortItem = random.nextInt(4);

        // retorna o item correspondente ao número sorteado
        switch(sortItem){

            case 0:
                return new Itens(
                    "pocao", "cura ferimentos", "CURA", 25, 0
                );

            case 1:
                return new Itens(
                    "espada", "aumenta ataque", "ATAQUE", 5, 0
                );

            case 2:
                return new Itens(
                    "escudo", "reforca defesa", "DEFESA", 4, 0
                );

            default:
                return new Itens(
                    "pocao cura", "ferimentos leves", "CURA", 12, 0
                );
        }
    }
}
