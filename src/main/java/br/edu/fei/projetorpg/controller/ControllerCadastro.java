    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.controller;

import br.edu.fei.projetorpg.model.Jogador;
import br.edu.fei.projetorpg.model.dao.JogadorDAO;
import br.edu.fei.projetorpg.model.dao.Conexao;
import br.edu.fei.projetorpg.view.JFrameCadastro;
import br.edu.fei.projetorpg.view.JFrameLogin;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Classe responsavel pela conexao entre a interface (JFrameCadastro) e a 
 * persistência (JogadorDAO)
 * @author vitoriamartins
 */
public class ControllerCadastro {
    private final JFrameCadastro viewCadastro;
    private Jogador jogador;
    
    /**
     * Construtor da classe ControllerCadastro
     * responsável por inicializar o controlador à tela de cadastro
     * @param viewCadastro 
     */
    public ControllerCadastro(JFrameCadastro viewCadastro){
        this.viewCadastro = viewCadastro;
    }
    
    /**
     * Responsável pela validação e cadastro de um novo jogador/usuário. Captura 
     * os dados inseridos, e valida se estão preenchidos, cria a ligacao com o 
     * banco de dados através do padrão Singleton e insere o cadastro, caso o 
     * cadastro seja efetivo, redireciona o jogador a tela de login
     */
    public void cadastrar(){
        
        String usuario = this.viewCadastro.getNomeusuario().getText();
        String senha = new String(this.viewCadastro.getSenha().getPassword());
        
        //validacao dos campos vazios
        if(usuario.trim().isEmpty() || senha.trim().isEmpty()){
            JOptionPane.showMessageDialog(this.viewCadastro, 
                        "todos os campos devem ser preenchidos",
                        "AVISO", JOptionPane.INFORMATION_MESSAGE);
            return; 
        }
        
        try{
            Connection conexao = Conexao.getInstance();
            
            if(conexao ==  null){
                JOptionPane.showMessageDialog(this.viewCadastro, "erro ao "
                        + "conectar no banco de dados", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JogadorDAO daoj = new JogadorDAO(conexao);
            Jogador jog = new Jogador(usuario, senha);
            
            //add o jogador no bd
            daoj.inserir(jog);
            
            JOptionPane.showMessageDialog(this.viewCadastro, "cadastro realizado"
                    + " realizado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            //redirecionando para tela de login
            voltarLogin();
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.viewCadastro, "usuario ja "
                    + "cadastrado", "ALERTA", JOptionPane.ERROR_MESSAGE);
        }  
    }
    
    /**
     * Fecha a pagina de cadastro e abre a pagina de login, 
     * instanciando o controlador de login 
     */
    
    public void voltarLogin(){
        this.viewCadastro.setVisible(false);
        JFrameLogin telaLogin = new JFrameLogin();
        ControllerLogin controllerLog = new ControllerLogin(telaLogin,
                this.jogador);
        telaLogin.setController(controllerLog);
        telaLogin.setVisible(true);
    }
}
