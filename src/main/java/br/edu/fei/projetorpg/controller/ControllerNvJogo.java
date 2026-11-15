/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.controller;

import br.edu.fei.projetorpg.model.Arqueiro;
import br.edu.fei.projetorpg.model.Guerreiro;
import br.edu.fei.projetorpg.model.Jogador;
import br.edu.fei.projetorpg.model.Mago;
import br.edu.fei.projetorpg.model.Protagonista;
import br.edu.fei.projetorpg.model.dao.Conexao;
import br.edu.fei.projetorpg.model.dao.PersonagemDAO;
import br.edu.fei.projetorpg.view.JFrameMenuJogo;
import br.edu.fei.projetorpg.view.JFrameNovoJogo;
import br.edu.fei.projetorpg.view.JFramePartida;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * classe responsavel por admistrar a criacao de um novo personagem, permitindo
 * ao jogador escolher dentre as classes, atribuir um nome, gravar os dados e 
 * iniciar a partida
 * @author vitoriamartins
 */
public class ControllerNvJogo {
    private final JFrameNovoJogo viewNovoJogo;
    private final Jogador jogador;
    private final Connection conexao;
    
    /**
     * construtor da classe ControllerNvJogo, inicializa o controlador associando
     * a pagina de criacao de um novo jogo, o jogador ativo e faz a conexao via
     * Singleton
     * @param viewNovoJogo
     * @param jogador 
     */
    public ControllerNvJogo(JFrameNovoJogo viewNovoJogo, Jogador jogador){
        this.viewNovoJogo = viewNovoJogo;
        this.jogador = jogador;
        this.conexao = Conexao.getInstance();
    }
    
    /**
     * retorna a pagina de novo jogo
     * @return 
     */
    public JFrameNovoJogo getViewNovoJogo() {
        return viewNovoJogo;
    }

    /**
     * retorna o joagdor conectado à criação do personagem 
     * @return 
     */
    public Jogador getJogador() {
        return jogador;
    }
    
    /**
     * cria e guarda um novo personagem com base no nome e na classe escolhida,
     * valida os campos e grava os dados no banco de dados através do PerosnagemDAO
     * e redireciona para a tela da partida
     * @param tipoClasse 
     */
    public void criarPersonagem(String tipoClasse){
        String nomePersonagem = this.viewNovoJogo.getNmPersonagem().getText();
        Protagonista hero = null;
        
        //validando se o campo nome esta vazio
        if(nomePersonagem == null || nomePersonagem.trim().isEmpty()){
            JOptionPane.showMessageDialog(this.viewNovoJogo,"insira um nome para"
                    + " o seu personagem", "Aviso", JOptionPane.WARNING_MESSAGE);
            this.viewNovoJogo.getNmPersonagem().requestFocus();
            return;
        }
        
        if(this.jogador == null){
            JOptionPane.showMessageDialog(this.viewNovoJogo,
                    "erro critico: sessao perdida", "erro de sessao", JOptionPane.ERROR_MESSAGE);
        }
        
        String classeTratada = (tipoClasse != null) ? 
                tipoClasse.toLowerCase().trim() : "";
        
        //instanciando a classe escolhida do heroi
        switch (classeTratada){
            case "arqueiro":
                hero = new Arqueiro(nomePersonagem);
                break;
            case "guerreiro":
                hero = new Guerreiro(nomePersonagem);
                break;
            case "mago":
                hero = new Mago(nomePersonagem);
                break;
        }  
        
        if(hero == null){
            JOptionPane.showMessageDialog(this.viewNovoJogo, "selecione uma "
                    + "classe", "Aviso", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //atribuindo o id retornado
        hero.setIdJogadorFk(this.jogador.getIdJogador());
        
        //associando o personagem ao jogador
        this.jogador.setPersonagemOn(hero);
        
        try{
            //criando o dao
            PersonagemDAO personagemDAO = new PersonagemDAO(this.conexao);
            
            //inserindo no banco de dados
            personagemDAO.inserir(hero, this.jogador.getIdJogador());
            int idPersonagem = personagemDAO.consultarIdJogador(this.jogador.getIdJogador());
            hero.setIdPersonagem(idPersonagem);
            JOptionPane.showMessageDialog(this.viewNovoJogo, "personagem criado "
                    + "com sucesso");
            
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.viewNovoJogo, "erro ao salvar "
                    + "personagem", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //criando a tela da partida
        this.viewNovoJogo.setVisible(false);
        JFramePartida telaPartida = new JFramePartida();
        ControllerPartida ctrlPartida = new ControllerPartida(telaPartida, 
                this.jogador);
        telaPartida.setTitle("PARTIDA");
        telaPartida.setController(ctrlPartida);
        telaPartida.setVisible(true);  
    }
    
    /**
     * fecha a pagina da criacao do jogo e retorna ao menu principal, inicializando
     * o controlador menu
     */
    public void menu(){
        this.viewNovoJogo.setVisible(false);
        JFrameMenuJogo telaMenu = new JFrameMenuJogo();
        ControllerMenu controllerMenu = new ControllerMenu(telaMenu, 
                this.jogador);
        telaMenu.setTitle("MENU");
        telaMenu.setController(controllerMenu);
        telaMenu.setVisible(true);
    }
    
}
