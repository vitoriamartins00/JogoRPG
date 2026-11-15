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
public class SaveDAO {
   private final Connection conn;
   
   public SaveDAO(Connection conn){
       this.conn = conn;
   }
   
    
    public void salvarJogo(Protagonista p, int faseAtual, String nomeSave, 
            String inimigos, String tipoClasse, int vdInimigoAtual) 
            throws SQLException{
        String sql = "INSERT INTO save_jogo(nome_save,nome_heroi, nivel, "
                + "vida_atual, fase_atual, id_personagem_fk, inim_der, "
                + "classe_heroi, vida_inim_atual) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
        
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, nomeSave);
            st.setString(2, p.getNome());
            st.setInt(3, p.getNivel());
            st.setInt(4, (int)p.getVida());
            st.setInt(5, faseAtual);
            st.setInt(6, p.getIdPersonagem());
            st.setString(7, inimigos);
            st.setString(8, tipoClasse.toLowerCase());
            st.setInt(9, vdInimigoAtual);
            
            System.out.println(st.toString());
            st.execute();
        
    }
    
    public ResultSet consultarJogo(String nomeSave) throws SQLException {
        String sql = "SELECT * FROM save_jogo WHERE nome_save = ?";
        PreparedStatement ps = this.conn.prepareStatement(sql);
        ps.setString(1, nomeSave);
        
        System.out.println(ps.toString());
        ps.execute();
        return ps.getResultSet();
    }
    
    public ResultSet consultarSaves() throws SQLException {
        String sql = "SELECT * FROM save_jogo";
        PreparedStatement stm = this.conn.prepareStatement(sql);
        return stm.executeQuery();  
    }
    
    public boolean existeSaveNome(String nomeSave) throws SQLException{
        String sql = "SELECT COUNT(*) FROM save_jogo WHERE nome_save = ?";
        
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, nomeSave);
        
        try(ResultSet rs = stm.executeQuery()){
            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}
