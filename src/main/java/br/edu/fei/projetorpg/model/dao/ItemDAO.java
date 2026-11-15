/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model.dao;

import br.edu.fei.projetorpg.model.Itens;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author vitoriamartins
 */
public class ItemDAO {
    private Connection conn;
    
    public ItemDAO(Connection conn){
        this.conn = conn;
    }
    
    public void salvarItem(ArrayList<Itens> inventario, int idPersonagem, 
            String nomeSave) throws SQLException{
        String sql = "INSERT INTO itens_personagens(nome, tipo, efeito,"
                + " id_pers_fk, nome_save)"
                + "VALUES (?, ?, ?, ?,?)";
        for(Itens item: inventario){
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, item.getNome());
            st.setString(2, item.getTipo());
            st.setInt(3, item.getPontoEfeito());
            st.setInt(4, idPersonagem);
            st.setString(5, nomeSave);
            
            st.execute();
        }
    }
    
    public ArrayList<Itens> consultaItens(int idPersonagem, String nomeSave) 
            throws SQLException{
        ArrayList<Itens> inventario = new ArrayList<>();
        String sql = "SELECT * FROM itens_personagens WHERE id_pers_fk = ? "
                + "AND nome_save = ?";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idPersonagem);
        ps.setString(2, nomeSave);
        try(ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                int idItem = rs.getInt("id_item");
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                int efeito = rs.getInt("efeito");
                
                String desc = "efeito de " + tipo.toLowerCase();
                
                Itens item = new Itens(nome, desc, tipo, efeito, idItem);
                inventario.add(item);
            }
        }
        return inventario;
    }
}
