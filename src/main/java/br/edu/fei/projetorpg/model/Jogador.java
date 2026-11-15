/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.edu.fei.projetorpg.model;

/**
 * representa o jogador do sistema, armazenando suas
 * informações de acesso e o protagonista associado
 * à partida
 *
 * @author vitoriamartins
 */
public class Jogador {

    private String nomeUsuario;
    private String senha;
    private int idJogador;
    private Protagonista personagemOn;

    /**
     * inicializa o jogador com o nome de usuário e a senha
     * recebidos por parâmetro
     * @param nomeUsuario 
     * @param senha 
     */
    public Jogador(String nomeUsuario, String senha){
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }

    /**
     * inicializa o jogador com suas informações de acesso,
     * identificador e protagonista associado à partida
     * @param nomeUsuario 
     * @param senha 
     * @param idJogador 
     * @param personagemOn 
     */
    public Jogador(String nomeUsuario, String senha, int idJogador,
            Protagonista personagemOn){
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.idJogador = idJogador;
        this.personagemOn = personagemOn;
    }

    /**
     * inicializa um jogador sem definir seus atributos,
     * permitindo que sejam preenchidos posteriormente
     */
    public Jogador(){

    }

    /**
     * verifica se o nome de usuário e a senha informados
     * correspondem aos dados armazenados no jogador
     * @param nomeUsuarioCad 
     * @param senhaCad 
     * @return 
     */
    public boolean autenticar(String nomeUsuarioCad, String senhaCad){
        return this.nomeUsuario.equals(nomeUsuarioCad) &&
                this.senha.equals(senhaCad);
    }

    /**
     * retorna o nome de usuário do jogador
     * @return 
     */
    public String getNomeusuario() {
        return nomeUsuario;
    }

    /**
     * define o nome de usuário do jogador
     * @param nomeUsuario 
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    /**
     * retorna a senha armazenada do jogador
     * @return 
     */
    public String getSenha() {
        return senha;
    }

    /**
     * define a senha do jogador.
     * @param senha 
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * retorna o identificador do jogador
     * @return
     */
    public int getIdJogador() {
        return idJogador;
    }

    /**
     * define o identificador do jogador
     * @param idJogador 
     */
    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    /**
     * retorna o protagonista associado ao jogador
     * @return 
     */
    public Protagonista getPersonagemOn() {
        return personagemOn;
    }

    /**
     * define o protagonista associado ao jogador
     * @param personagemOn 
     */
    public void setPersonagemOn(Protagonista personagemOn) {
        this.personagemOn = personagemOn;
    }
}
