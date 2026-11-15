/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model;

/**
 *
 * @author vitoriamartins
 */
public class Jogador {
    private String nomeUsuario;
    private String senha;
    private int idJogador;
    private Protagonista personagemOn;
    
    public Jogador(String nomeUsuario, String senha){
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }
    public Jogador(String nomeUsuario, String senha, int idJogador, 
            Protagonista personagemOn){
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.idJogador = idJogador;
        this.personagemOn = personagemOn;
    }
    
    public Jogador(){
        
    }
      
    public boolean autenticar(String nomeUsuarioCad, String senhaCad){
        return this.nomeUsuario.equals(nomeUsuarioCad) &&
                this.senha.equals(senhaCad);
    }

    public String getNomeusuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public Protagonista getPersonagemOn() {
        return personagemOn;
    }

    public void setPersonagemOn(Protagonista personagemOn) {
        this.personagemOn = personagemOn;
    }
}
