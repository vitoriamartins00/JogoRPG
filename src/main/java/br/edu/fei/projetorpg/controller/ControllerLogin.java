/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.controller;

import br.edu.fei.projetorpg.model.Jogador;
import br.edu.fei.projetorpg.model.dao.Conexao;
import br.edu.fei.projetorpg.model.dao.JogadorDAO;
import br.edu.fei.projetorpg.view.JFrameCadastro;
import br.edu.fei.projetorpg.view.JFrameLogin;
import br.edu.fei.projetorpg.view.JFrameMenuJogo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Classe responsável pela lógica de autenticação e validação dos dados inseridos
 * no banco de dados através do cadastro e faz a transição para o menu principal 
 * ou tela de cadastro
 * @author vitoriamartins
 */
public class ControllerLogin {
    private final JFrameLogin viewLogin;
    private final Jogador jogador;
    private JogadorDAO jogadorDAO;
    private Connection conexao;
    
    /**
     * Construtor da classe ControllerLogin
     * Inicializa o controlador, obtém a instancia única da conexao e instancia 
     * o DAO de jogadores
     * @param viewLogin
     * @param jogador 
     */
    public ControllerLogin(JFrameLogin viewLogin, Jogador jogador){
        this.viewLogin = viewLogin;
        this.jogador = jogador;
        this.conexao = Conexao.getInstance();
        this.jogadorDAO = new JogadorDAO(conexao);
    }
    
    /**
     * Retorna o jogador associado ao controlador 
     * @return 
     */
    public Jogador getJogador() {
        return jogador;
    }
    
    /**
     * Retorna a tela de login controlada por esta classe
     * @return 
     */
    public JFrameLogin getViewLogin() {
        return viewLogin;
    }
    
    /**
     * Valida os dados inseridos nos campos usuario e senha, também é responsável
     * por verificar se os campos estão preenchidos e consultar consultar o 
     * banco de dados para validacao do login, se validas abre a pagina do menu jogo
     */
    public void validacaoLogin(){
        String uDigitado = this.viewLogin.getCmpUsuario().getText();
        String sDigitado = String.copyValueOf(this.viewLogin.getCmpSenha().getPassword());
        
        //validando campos vazios
        if(uDigitado.isEmpty() || sDigitado.isEmpty()){
            JOptionPane.showMessageDialog(this.viewLogin, "preencha todos os "
                    + "campos para logar", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
      
        try{
            //consultando o banco de dados 
            ResultSet result = this.jogadorDAO.consultar(uDigitado, sDigitado);
            
            //se result.next () retornar true o usuario e senha foram encontrado no bd
            if(result != null && result.next()){
                int idBanco = result.getInt("id_jogador");
                System.out.println(idBanco);
                String nomeBanco = result.getString("nome_usuario");
                System.out.println("deu certo");
                
                Jogador jogadorLogado = new Jogador();
                jogadorLogado.setIdJogador(idBanco);
                jogadorLogado.setNomeUsuario(nomeBanco);
                
                this.viewLogin.setVisible(false);
                JFrameMenuJogo telaMenu = new JFrameMenuJogo();
                telaMenu.setTitle("MENU JOGO");
                ControllerMenu controllerMenu = new ControllerMenu(telaMenu, 
                        jogadorLogado);
                telaMenu.setController(controllerMenu);
                telaMenu.setVisible(true);          
            }else{
                JOptionPane.showMessageDialog(this.viewLogin, "usuario nao "
                        + "cadastrado", "Erro Login", JOptionPane.ERROR_MESSAGE); 
            }  
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.viewLogin, "erro ao consultar"
                    + " banco de dados");
        } 
    }
    
    /**
     * Fecha a pagina de login e abre a pagina de cadastro de novos jogadores, 
     * inicializando o controlador cadastro
     */
    public void irCadastrar(){
        this.viewLogin.setVisible(false);
        JFrameCadastro telaCadastro = new JFrameCadastro();
        ControllerCadastro controllerCad = new ControllerCadastro(telaCadastro);
        telaCadastro.setTitle("CADASTRO");
        telaCadastro.setController(controllerCad);
        telaCadastro.setVisible(true);
    }
    
}
