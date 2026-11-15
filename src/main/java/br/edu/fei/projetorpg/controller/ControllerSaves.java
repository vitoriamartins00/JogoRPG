/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.controller;

import br.edu.fei.projetorpg.model.Arqueiro;
import br.edu.fei.projetorpg.model.Guerreiro;
import br.edu.fei.projetorpg.model.Inimigo;
import br.edu.fei.projetorpg.model.Itens;
import br.edu.fei.projetorpg.model.Jogador;
import br.edu.fei.projetorpg.model.Mago;
import br.edu.fei.projetorpg.model.Protagonista;
import br.edu.fei.projetorpg.model.dao.Conexao;
import br.edu.fei.projetorpg.model.dao.ItemDAO;
import br.edu.fei.projetorpg.model.dao.SaveDAO;
import br.edu.fei.projetorpg.view.JFrameMenuJogo;
import br.edu.fei.projetorpg.view.JFrameSaves;
import br.edu.fei.projetorpg.view.JFramePartida;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vitoriamartins
 */
public class ControllerSaves {
    JFrameSaves viewSaves;
    Jogador jogador;
    private Connection conexao;
    
    
    public ControllerSaves(JFrameSaves viewSaves, Jogador jogador){
        this.viewSaves = viewSaves;
        this.jogador = jogador;
        this.conexao = Conexao.getInstance();
        
    }
    
    public ControllerSaves(Connection conexao){
        this.conexao = conexao;
    }

    public JFrameSaves getViewSaves() {
        return viewSaves;
    }

    public void setViewSaves(JFrameSaves viewSaves) {
        this.viewSaves = viewSaves;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
    
    public void salvarPartida(Protagonista protagonista, int faseAtual, 
            String nomeSave, String tipoClasse, int vdInimigoAtual){
        try{
            SaveDAO saveDAO = new SaveDAO(this.conexao);
            
            if(saveDAO.existeSaveNome(nomeSave)){
                JOptionPane.showMessageDialog(null, 
                        "existe um jogo salvo com o nome " + nomeSave + 
                                ", escolha outro nome", "save existente", 
                                JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String nomesInimigos = "";
            
            for(Inimigo inimigo: protagonista.getAdvDerrotados()){
                nomesInimigos += inimigo.getNome() + ",";
            }
                                   
            saveDAO.salvarJogo(protagonista, faseAtual,nomeSave,nomesInimigos,
                    tipoClasse, vdInimigoAtual);
                        
            JOptionPane.showMessageDialog(null, "" + nomeSave + 
                    " salvo com sucesso");
        }catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "erro ao salvar: "
                    + e.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    public void mostraSaves(){
        try{
            SaveDAO saveDAO = new SaveDAO(this.conexao);
            ResultSet rs = saveDAO.consultarSaves();
            
            //limpa a tabela antes de add para nao duplicar
            DefaultTableModel model = (DefaultTableModel) 
                    this.viewSaves.getjTable1().getModel();
            model.setRowCount(0);
            
            while(rs.next()){
                String nomeSave = rs.getString("nome_save");
                int faseAtual = rs.getInt("fase_atual");
                int vidaAtual = rs.getInt("vida_atual");
                String inimDer = rs.getString("inim_der");
                int nivelAtual = rs.getInt("nivel");
                
                //populando a tabela
                model.addRow(new Object[]{
                    nomeSave, 
                    String.valueOf(faseAtual),
                    String.valueOf(vidaAtual),
                    inimDer, nivelAtual
                    
                });
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.viewSaves, 
                    "erro ao carregar os saves: " + e.getMessage(), "erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void carregarSave(String nomeSave){
        try{
            SaveDAO saveDAO = new SaveDAO(this.conexao);
            ResultSet rs = saveDAO.consultarJogo(nomeSave);
            
            Protagonista p = null;
            
            if(rs.next()){
                int idPersonagemSalvo = rs.getInt("id_personagem_fk");
                String nomeHeroi = rs.getString("nome_heroi");
                int faseSalva = rs.getInt("fase_atual");
                int vidaSalva = rs.getInt("vida_atual");
                int nivelSalvo = rs.getInt("nivel");
                String tipoClasse = rs.getString("classe_heroi");
                String classeTratada = (tipoClasse != null) ? tipoClasse.
                        toLowerCase().trim():"";
                int vidaInimigoSalva = rs.getInt("vida_inim_atual");
                
                switch(classeTratada){
                    case "arqueiro":
                        p = new Arqueiro(nomeHeroi);
                        break;
                    case "guerreiro":
                        p = new Guerreiro(nomeHeroi);
                        break;
                    case "mago":
                        p = new Mago(nomeHeroi);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this.viewSaves, "erro: "
                                + "classe desconhecida '" + tipoClasse + "'", 
                                "erro", JOptionPane.ERROR_MESSAGE);
                        return; //sai do metodo
                }
                
                if(p != null){
                    p.setIdPersonagem(idPersonagemSalvo);
                    p.setVida(vidaSalva);
                    p.setNivel(nivelSalvo);
                    
                    //recuperando os itens do inventario
                    try{
                        ItemDAO itemDAO = new ItemDAO(this.conexao);
                        ArrayList<Itens> itensSalvo = itemDAO.consultaItens
                            (idPersonagemSalvo, nomeSave);
                        if(itensSalvo != null && !itensSalvo.isEmpty()){
                            for(int i = 0; i < itensSalvo.size() && i < 3; i++){
                                p.getInventario().add(itensSalvo.get(i));
                            }
                        }
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    
                    this.jogador.setPersonagemOn(p);
                    
                    this.viewSaves.setVisible(false);
                    JFramePartida viewPartida = new JFramePartida();
                    ControllerPartida ctrPartida = new ControllerPartida(
                            viewPartida,this.jogador);
                    
                    ctrPartida.setFaseAtual(faseSalva);
                    
                    Inimigo inimigoAtual = ctrPartida.getInimigoAtual();
                    if(inimigoAtual != null && vidaInimigoSalva > 0){
                        inimigoAtual.setVida(vidaInimigoSalva);
                    }
                    
                    ctrPartida.setFaseAtual(faseSalva);
                    
                    viewPartida.setController(ctrPartida);
                    viewPartida.setVisible(true);

                    JOptionPane.showMessageDialog(null, "Jogo " + nomeSave
                    + " carregado com sucesso");
                }
                
                //lista de inimigos derrotados
                String inimigos = rs.getString("inim_der");
                ArrayList<Inimigo> listCarregada = new ArrayList<>();
                
                if(inimigos != null && !inimigos.isEmpty()){
                    String[] partes = inimigos.split(",");
                    
                    for(String nomeInimigo : partes){
                        if(!nomeInimigo.isEmpty()){
                            Inimigo inimigoCarregado = new Inimigo(nomeInimigo);
                            listCarregada.add(inimigoCarregado);
                        }
                    }
                }
                
                p.setAdvDerrotados(listCarregada);
                
            }else{
                JOptionPane.showMessageDialog(this.viewSaves, "o save '" 
                        + nomeSave + "' nao foi encontrado", "aviso", 
                        JOptionPane.WARNING_MESSAGE);
            }
            
        }catch(Exception e){
        JOptionPane.showMessageDialog(this.viewSaves, "erro ao carregar jogo: "
        + e.getMessage(), "erro", JOptionPane.ERROR_MESSAGE);   
        }
    }
    
    public void saveEscolhido(){
        try{
            String nomeSave = JOptionPane.showInputDialog(this.viewSaves,
                    "digite o nome do save que deseja carregar:", 
                    "carregar Jogo", JOptionPane.QUESTION_MESSAGE);
            
            if(nomeSave != null && !nomeSave.trim().isEmpty()){
                carregarSave(nomeSave.trim());
            } else if(nomeSave != null) {
                JOptionPane.showMessageDialog(this.viewSaves, 
                        "por favor, insira um nome válido.", "aviso", 
                        JOptionPane.WARNING_MESSAGE);
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.viewSaves, 
                    "erro ao acessar saves: " + e.getMessage(), "erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void menu(){
        this.viewSaves.setVisible(false);
        JFrameMenuJogo telaMenu = new JFrameMenuJogo();
        ControllerMenu controllerMenu = new ControllerMenu(telaMenu, this.jogador);
        telaMenu.setTitle("MENU");
        telaMenu.setController(controllerMenu);
        telaMenu.setVisible(true);
    }
}
