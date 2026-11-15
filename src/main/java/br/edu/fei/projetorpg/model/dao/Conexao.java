/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

/**
 *
 * @author vitoriamartins
 */
public class Conexao {
    
    public static Connection conexao;
    
    public Conexao(){
        
    }
    
    public static Connection getInstance() {
        try{
            if(conexao == null || conexao.isClosed()){
                Dotenv dotenv = Dotenv.load();
                conexao = DriverManager.getConnection(
                dotenv.get("DB_URL"),
                dotenv.get("DB_USER"),
                dotenv.get("DB_PASSWORD"));
 
            }
            return conexao;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conexao;
    }
}
