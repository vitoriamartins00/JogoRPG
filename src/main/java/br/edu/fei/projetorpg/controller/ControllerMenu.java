/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.controller;

import br.edu.fei.projetorpg.model.Jogador;
import br.edu.fei.projetorpg.model.dao.Conexao;
import br.edu.fei.projetorpg.view.JFrameMenuJogo;
import br.edu.fei.projetorpg.view.JFrameNovoJogo;
import br.edu.fei.projetorpg.view.JFrameSaves;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 * classe responsável por gerenciar as opções do menu principal, permite que o
 * jogador crie um novo jogo, gerencie seus saves guardados ou encerra a sessão
 * @author vitoriamartins
 */
public class ControllerMenu {
    private final JFrameMenuJogo telaMenu;
    private final Jogador jogador;
    private final Connection conexao;
    
    /**
     * construtor classe ControllerMenu, inicializa o controlador do menu, associa 
     * a tela e faz a conexão com o banco de dados
     * @param telaMenu
     * @param jogador 
     */
    public ControllerMenu(JFrameMenuJogo telaMenu, Jogador jogador){
        this.telaMenu = telaMenu;
        this.jogador = jogador;
        this.conexao = Conexao.getInstance();
    }
    
    /**
     * retorna a pagina do menu principal assosciada a este controlador
     * @return a instancia de JFrameMenuJogo
     */

    public JFrameMenuJogo getTelaMenu() {
        return telaMenu;
    }
    
    /**
     * Retorna o jogador autenticado na sessao do menu
     * @return 
     */
    public Jogador getJogador() {
        return jogador;
    }
    
    /**
     * fecha a pagina do menu principal e abre a tela de criação de um novo jogo,
     * inicializando o controlador do novo jogo
     */
    public void criarNvJogo(){
        this.telaMenu.setVisible(false);
        
        //criando a view
        JFrameNovoJogo telaNvJogo = new JFrameNovoJogo();
        
        ControllerNvJogo controllerNovo = new ControllerNvJogo(telaNvJogo, 
                this.jogador);
        telaNvJogo.setTitle("NOVO JOGO");
        telaNvJogo.setController(controllerNovo);
        telaNvJogo.setVisible(true);   
    }
    
    /**
     * fecha a janela do menu principal e abre a tela de gestao de saves, 
     * carregando e mostrando os jogos guardados do jogador logado
     */
    public void saves(){
        this.telaMenu.setVisible(false);
        JFrameSaves viewSaves = new JFrameSaves();
        
        ControllerSaves ctrSaves = new ControllerSaves(viewSaves, this.jogador);
        viewSaves.setControllerSaves(ctrSaves);
        
        ctrSaves.mostraSaves();
        viewSaves.setVisible(true);
    }
    
    /**
     * mostra uma caixa de diálogo para confirmar o encerramento da sessão, 
     * caso a resposta seja sim, esconde a tela do menu e encerra o acesso
     */
    public void sair(){
        int resposta = JOptionPane.showConfirmDialog(this.telaMenu,
                "deseja encerrar sessao? ", "sair", JOptionPane.YES_NO_OPTION);
        
        if(resposta == JOptionPane.YES_OPTION){
            try{
                if(this.conexao != null && !this.conexao.isClosed()){
                    this.conexao.close();
                }
                this.telaMenu.setVisible(false);
                
            }catch (Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(this.telaMenu, "erro ao encerrar "
                        + "a conexao: " + e.getMessage());
            }
        }
    }
}
