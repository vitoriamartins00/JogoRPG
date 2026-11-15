/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model.dao;

import br.edu.fei.projetorpg.model.Jogador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vitoriamartins
 */
public class JogadorDAO {
    private final Connection conn;
    
    public JogadorDAO(Connection conn){
        this.conn = conn;
    }
    
    public void inserir(Jogador jogador) throws SQLException{
        try (conn) {
            String sql = "INSERT INTO jogador(nome_usuario, senha) VALUES('"
                    + jogador.getNomeusuario() + "', '" + jogador.getSenha() + "')";
            
            System.out.println(sql);
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
        }
    }
    
    public ResultSet consultar(String nomeUsuario, String senha) 
            throws SQLException{
        String sql = "SELECT * FROM jogador WHERE nome_usuario = ? AND senha = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, nomeUsuario);
        statement.setString(2, senha);
        
        return statement.executeQuery();
    }
}
