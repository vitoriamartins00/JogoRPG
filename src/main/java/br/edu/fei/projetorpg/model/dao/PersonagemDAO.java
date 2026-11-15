/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model.dao;

import br.edu.fei.projetorpg.model.Protagonista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vitoriamartins
 */
public class PersonagemDAO {
    private Connection conn;
    
    public PersonagemDAO(Connection conn){
        this.conn = conn;
    }
    
    public void inserir(Protagonista p, int idJogador) throws SQLException{
        String sql = "INSERT INTO personagem(nome, classe, vida, ataque, defesa,"
                + "nivel, id_jogador) VALUES (?, ? ,? ,?, ?, ?, ?)";
        
        PreparedStatement statement = this.conn.prepareStatement(sql);
        statement.setString(1, p.getNome());
        statement.setString(2, p.getClass().getSimpleName());
        statement.setInt(3, p.getVida());
        statement.setInt(4, p.getAtaque());
        statement.setInt(5, p.getDefesa());
        statement.setInt(6, p.getNivel());
        statement.setInt(7, p.getIdJogadorFk());
            
        statement.execute();
    }
    
    public int consultarIdJogador(int idJogador) throws SQLException {
        String sql = "SELECT id_personagem FROM personagem WHERE id_jogador = ?";
        int idEncontrado = 0;
        
        PreparedStatement st = this.conn.prepareStatement(sql);
        st.setInt(1, idJogador);
        
        System.out.println(st.toString());
        try (ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                idEncontrado = rs.getInt("id_personagem");
            }
        }
        return idEncontrado;
    }
}