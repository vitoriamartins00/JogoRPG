/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.edu.fei.projetorpg;
import br.edu.fei.projetorpg.controller.ControllerLogin;
import br.edu.fei.projetorpg.model.Jogador;
import br.edu.fei.projetorpg.model.dao.Conexao;
import br.edu.fei.projetorpg.view.JFrameLogin;
import java.sql.Connection;
import java.sql.SQLException;



/**
 *
 * @author vitoriamartins
 */
public class ProjetoRPG {

    public static void main(String[] args) {
        
        JFrameLogin viewLogin = new JFrameLogin();
        viewLogin.setTitle("LOGIN");
        Jogador jogador = new Jogador();
        ControllerLogin controllerLogin = new ControllerLogin(viewLogin,
                jogador);
        viewLogin.setController(controllerLogin);
        viewLogin.setVisible(true);

    }
}
