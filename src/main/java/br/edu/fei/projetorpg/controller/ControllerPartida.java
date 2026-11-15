    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.controller;
import br.edu.fei.projetorpg.model.GameUtils;
import br.edu.fei.projetorpg.model.Inimigo;
import br.edu.fei.projetorpg.model.Itens;
import br.edu.fei.projetorpg.model.Jogador;
import br.edu.fei.projetorpg.model.Protagonista;
import br.edu.fei.projetorpg.model.dao.Conexao;
import br.edu.fei.projetorpg.model.dao.ItemDAO;
import br.edu.fei.projetorpg.view.JFrameMenuJogo;
import br.edu.fei.projetorpg.view.JFramePartida;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author vitoriamartins
 */
public class ControllerPartida {
    protected Protagonista protagonista;
    private ArrayList<Inimigo> listaInimigos;
    private int faseAtual;
    private String acao;
    private final JFramePartida viewPartida;
    private final   Jogador jogador;
    private int vdInHeroi;
    private int vdInInimigo;
    private final int faseMax = 5; 
    private final Connection conexao;
    
    public ControllerPartida(JFramePartida viewPartida, Jogador jogador){
        this.viewPartida = viewPartida;
        this.jogador = jogador;
        this.protagonista = jogador.getPersonagemOn();
        this.faseAtual = 1;
        this.acao = "a batalha comecou! escolha sua acao";
        this.listaInimigos = new ArrayList<>();
        this.conexao = Conexao.getInstance();
            
        if(this.protagonista != null){
            this.vdInHeroi = GameUtils.calcVdMax(this.protagonista, 
                    this.faseAtual);
        }
        gerarInimigo();
        attVisual(); 
    }

    public Protagonista getProtagonista() {
        return protagonista;
    }

    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
    }

    public ArrayList<Inimigo> getListaInimigos() {
        return listaInimigos;
    }

    public void setListaInimigos(ArrayList<Inimigo> listaInimigos) {
        this.listaInimigos = listaInimigos;
    }

    public int getFaseAtual() {
        return faseAtual;
    }

    public void setFaseAtual(int faseAtual) {
        this.faseAtual = faseAtual;
        if(this.protagonista != null){
            this.vdInHeroi = GameUtils.calcVdMax(this.protagonista, faseAtual);
        }
        
        Inimigo inimigoAtual = getInimigoAtual();
        if(inimigoAtual != null){
            this.vdInInimigo = inimigoAtual.getVida();
        }
        
        attVisual();
    }
    
    public Inimigo getInimigoAtual(){
        int indice = this.faseAtual -1;
        if (indice >= 0 && indice < this.listaInimigos.size()){
            return this.listaInimigos.get(indice);
        }
        return null;
    }
    
    public String getAcao(){
        return this.acao;
    }
    
    public JFramePartida getViewPartida() {
        return viewPartida;
    }

    public Jogador getJogador() {
        return jogador;
    }
    
    public boolean isCampanhaConcluida(){
        return this.faseAtual > faseMax || (this.faseAtual == faseMax &&
                getInimigoAtual() != null && getInimigoAtual().isDerrotado());
    }
    
    public boolean isGameOver(){
        return this.protagonista.isDerrotado() && this.protagonista != null;
    }
    
    public void proximaFase(){
        Inimigo derrotado = getInimigoAtual();
        
        if(derrotado != null && derrotado.isDerrotado()){
            this.protagonista.getAdvDerrotados().add(derrotado);
            
            this.protagonista.setNivel(this.protagonista.getNivel() + 1);
            
            switch(this.faseAtual){
                case 1: 
                    this.vdInHeroi += 25;
                    this.protagonista.setVida(vdInHeroi);
                    this.acao = "ganhou +25 de vida maxima";
                    break;
                case 2:
                   
                    this.protagonista.setAtaque(this.protagonista.getAtaque() + 5);
                    this.acao = "ganhou +5 de poder de ataque";
                    break;
                case 3:
                    
                    this.protagonista.setDefesa(this.protagonista.getDefesa() + 3);
                    this.acao = "ganhou +3 de defesa";
                    break;
                case 4:
                   this.vdInHeroi += 30;
                   this.protagonista.setVida(vdInHeroi);
                   this.protagonista.setAtaque(this.protagonista.getAtaque() + 10);
                   this.acao = "bonus!!! voce ganhou +30 de vida e +10 de ataque";
                   break;
                default:
                    this.acao = "inimigo derrotado";
                    break;
            }
            this.protagonista.setVida(this.vdInHeroi);
            
            //concedendo item aleatorio
            concluirFaseVitoria(derrotado);
        }
        
        if(this.faseAtual >= faseMax){
            this.acao = "voce venceu todas as 5 campanhas";
            desativarBtn();
        }else{
            this.faseAtual ++;
            Inimigo proximoInimigo = getInimigoAtual();
            if(proximoInimigo != null){
                this.vdInInimigo = proximoInimigo.getVida();
            }
        }
        attVisual();
    }
    
    public void concluirFaseVitoria(Inimigo inimigoDerrotado){
        Itens itemGanho = Itens.gerarItem();
        // guarda a info da fase anterior
        String acaoAnterior = this.acao;
        
        if(this.protagonista.getInventario().size() >= 3){
            ArrayList<Itens> inv = this.protagonista.getInventario();
            String[] opcoesTroca = {
                "substituir 1: " + inv.get(0).getNome(),
                "substituir 2: " + inv.get(1).getNome(),
                "substituir 3: " + inv.get(2).getNome(),
                "manter inventario "
            };
            
            int esc = JOptionPane.showOptionDialog(this.viewPartida, "inventario "
                    + "cheio, voce ganhou: " + itemGanho.getNome() + ".\nescolha"
                            + " o item que deseja substituir ou mantenha seu inventario",
                    "inventario cheio",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    opcoesTroca, opcoesTroca[0]);
            if (esc >= 0 && esc < 3){
                Itens itemDescartado = inv.get(esc);
                inv.set(esc, itemGanho);
                this.acao = acaoAnterior + " |trocou " + 
                        itemDescartado.getNome() + " por " 
                        + itemGanho.getNome();
            }else{
                this.acao = acaoAnterior + " |manteve o inventario atual";
            }
            
            this.protagonista.regVitoria(inimigoDerrotado);
        }else{
            this.protagonista.ganharItemInimigo(inimigoDerrotado, itemGanho);
            this.acao = acaoAnterior + " |voce ganhou um novo item " +
                    itemGanho.getNome();
        }
    }
    
    public void execAtaque(){
        if(isGameOver()){
            this.acao = "GAME OVER, voce foi derrotado :(";
            desativarBtn();
            attVisual();
            return;
        }
        
        if(isCampanhaConcluida()){
            this.acao = "campanha concluida";
            attVisual();
            return;
        }
        
        Inimigo inimigo = getInimigoAtual();
        
        if(inimigo != null){
            this.protagonista.atacar(inimigo);
            
            if(inimigo.isDerrotado()){
                this.acao = "voce derrotou o " + inimigo.getNome();
                proximaFase();
            }else{
                turnoInimigoAtaca(inimigo);
                verificaDerrotaHeroi();
            }
            
            attVisual();
        }  
    }
    
    public void execPoderEspecial(){
        if(isGameOver()){
            this.acao = "GAME OVER, voce foi derrotado :(";
            desativarBtn();
            attVisual();
            return;
        }
        
        if(isCampanhaConcluida()){
            this.acao = "campanha concluida";
            attVisual();
            return;
        }
        
        Inimigo inimigo = getInimigoAtual();
        
        if(inimigo != null){
            this.protagonista.poderEspecial(inimigo);
            
            if(inimigo.isDerrotado()){
                this.acao = "o inimigo " + inimigo.getNome() + " foi derrotado "; 
                proximaFase();
            }else{
                this.acao = "o inimigo contra-atacou seu poder especial";
                inimigo.atacar(this.protagonista);
                verificaDerrotaHeroi();
            }
            attVisual();
        }
    }
    
    public void usarItem(int indiceItem){
        if(isGameOver()){
            this.acao = "GAME OVER, voce foi derrotado :(";
            desativarBtn();
            attVisual();
            return;
        }
        
        ArrayList<Itens> inv = this.protagonista.getInventario();
        
        //verificando se tem itens no inventario
        if(inv == null || inv.isEmpty()){
            this.acao = "seu inventario esta vazio";
            attVisual();
            return;
        }
            
        String[] nomeItens = new String[inv.size()];
        for(int i = 0; i < inv.size(); i++){
            nomeItens[i] = inv.get(i).getNome() + " (Efeito: " + 
                    inv.get(i).getDesc()+ ")";
        }
        
        String itemEscolhido = (String) JOptionPane.showInputDialog(
                this.viewPartida, "qual item quer usar: ", "usar item", 
                JOptionPane.QUESTION_MESSAGE, null, nomeItens, nomeItens[0]);
        
        if(itemEscolhido != null){
            int indSelecionado = -1;
            for(int i=0; i < nomeItens.length; i++){
                if(nomeItens[i].equals(itemEscolhido)){
                    indSelecionado = i;
                    break;
                }
            }
            
            if(indSelecionado != -1){
                Inimigo inimigo = getInimigoAtual();
                String efeitoItem = this.protagonista.usarItem(indSelecionado);
                
                if(inimigo != null && !inimigo.isDerrotado()){
                    inimigo.atacar(this.protagonista);
                    this.acao = efeitoItem + " | o " + inimigo.getNome() + " "
                            + "contra-atacou";
                    verificaDerrotaHeroi();
                }else{
                    this.acao = efeitoItem;
                }
                
                attVisual();
            }
        }
    }
    
    public void defender(){
        if(isGameOver()){
            this.acao = "GAME OVER, voce foi derrotado :((";
            desativarBtn();
            attVisual();
            return;
        }
        
        if(isCampanhaConcluida()){
            this.acao = "campanha concluida";
            attVisual();
            return;
        }
        
        Inimigo inimigoAtual = getInimigoAtual();
        
        if(inimigoAtual != null){
            //inimigo ataca e o heroi defende
            int danoInimigo = inimigoAtual.getAtaque();
            this.protagonista.danoDefesa(danoInimigo);
            this.acao = this.protagonista.getNome() + " defendeu";
            
            //verifica se o heroi esta sem vida
            verificaDerrotaHeroi();
            
            //atualiza a tela   
            attVisual();
        }
    }
    
    public void turnoInimigoAtaca(Inimigo inimigo){
        Random random = new Random();
        
        int tipoAtaque = random.nextInt(2);
        
        if(tipoAtaque == 0){
            inimigo.atacar(this.protagonista);
            this.acao = inimigo.getNome() + " usou ataque direto";
        }else{
            int danoEspecial = inimigo.getAtaque() + 2  ;
            this.protagonista.recebeDano(danoEspecial);
            this.acao = inimigo.getNome() + " usou poder especial";
        }
    }
    
    private void verificaDerrotaHeroi(){
        if(isGameOver()){
            this.acao = "voce foi derrotado, GAME OVER :((";
            desativarBtn();
        }
    }
    
    private void desativarBtn(){
        if(this.viewPartida != null){
            this.viewPartida.getBtnAtacar().setEnabled(false);
            this.viewPartida.getBtnEspecial().setEnabled(false);
            this.viewPartida.getBtnItem().setEnabled(false);
            this.viewPartida.getBtnDefesa().setEnabled(false);
        }
    }
    
    private void attVisual(){
        if(this.viewPartida != null){
            if(this.protagonista != null){
                this.viewPartida.mostrarPersonagem
                    (this.protagonista.getClass().getSimpleName());
                this.viewPartida.getNmPersonagem().
                        setText(this.protagonista.getNome());
                this.viewPartida.getNvHeroi().setText("nivel: " + this.protagonista.getNivel());
                this.viewPartida.getNumFase().setText("fase: " + this.faseAtual);
                
                int vidaHero = (int) Math.max(0, this.protagonista.getVida());
                this.viewPartida.getVdHeroi().setMaximum(this.vdInHeroi);
                this.viewPartida.getVdHeroi().setValue(vidaHero);
                this.viewPartida.getQtdVidaH().setText(vidaHero + "/" + this.vdInHeroi);
            }
            
            Inimigo inimigoAtual = getInimigoAtual();
            
            if(inimigoAtual != null){
                
                this.viewPartida.mostrarInimigo(inimigoAtual.getNome());
                
                int vidaInimigo = (int) Math.max(0, inimigoAtual.getVida());
                
                this.viewPartida.getVdInimigo().setMaximum(this.vdInInimigo);
                this.viewPartida.getNmInimigo().setText(inimigoAtual.getNome());
                this.viewPartida.getVdInimigo().setValue(vidaInimigo);
                this.viewPartida.getQtdVidaI().setText(vidaInimigo + "/" + this.vdInInimigo);
            }else{
                this.viewPartida.mostrarInimigo("");
                this.viewPartida.getNmInimigo().setText("Derrotado");
                this.viewPartida.getQtdVidaI().setText("0 / 0");
            }
            this.viewPartida.getAcao().setText(this.acao);
        }
    }
    
    public void gerarInimigo(){
        
        if(this.listaInimigos == null){
            this.listaInimigos = new ArrayList<>();
        }
        
        //criando os 5 inimigos em ordem de dificuldade
        this.listaInimigos.add(new Inimigo(0, "Goblin", 70, 15, 2, 1));
        this.listaInimigos.add(new Inimigo(0, "Orc", 90, 25, 4, 2));
        this.listaInimigos.add(new Inimigo(0, "Troll", 105, 30, 6, 3));
        this.listaInimigos.add(new Inimigo(0, "Wargs", 115, 45, 8, 4));
        this.listaInimigos.add(new Inimigo(0, "Balrog", 150, 60, 10, 5));
        
        Inimigo inimigoAtual = getInimigoAtual();
        if(inimigoAtual != null){
            this.vdInInimigo = inimigoAtual.getVida();
        }
    }
    
    public void voltarMenu(){
        this.viewPartida.setVisible(false);
        JFrameMenuJogo telaMenu = new JFrameMenuJogo();
        ControllerMenu controllerMenu = new ControllerMenu(telaMenu, this.jogador);
        telaMenu.setTitle("MENU");
        telaMenu.setController(controllerMenu);
        telaMenu.setVisible(true);
    }
    
    public void pause() {
        String[] opcoes = {
            "continuar", "salvar partida", "menu principal"
        };
        
        int op = JOptionPane.showOptionDialog(this.viewPartida, "jogo pausado","pausado",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null,
                opcoes,
                opcoes[0]);
        
        switch(op){
            //continuar
            case 0:
                //jogo segue normal
                break;
            case 1:
                //salvar jogo com nome
                String nomeSave = JOptionPane.showInputDialog(this.viewPartida, 
                        "digite o nome do seu save: ", "salvar", 
                        JOptionPane.QUESTION_MESSAGE);
                
                if(nomeSave != null && !nomeSave.isEmpty()){
                    try{
                        ControllerSaves gerenciador = 
                            new ControllerSaves(this.conexao);
                        
                        int vdInimigoAtual = 0;
                        if(getInimigoAtual() != null){
                            vdInimigoAtual = getInimigoAtual().getVida();
                        }
                        gerenciador.salvarPartida(this.protagonista, 
                                this.faseAtual, nomeSave, 
                                this.protagonista.getClass().getSimpleName(),
                                vdInimigoAtual);
                        
                        //salvando os itens do inventario
                        
                        ItemDAO itemDAO = new ItemDAO(this.conexao);
                        itemDAO.salvarItem(this.protagonista.getInventario(), 
                                this.protagonista.getIdPersonagem(), nomeSave);
                    }catch(SQLException e){
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this.viewPartida, 
                                "erro ao salvar inventario: " + e.getMessage());
                    }
                }
                break;  
            case 2:
                int confirmacao = JOptionPane.showConfirmDialog(this.viewPartida,
                        "deseja abandonar a partida? o jogo nao salvo sera "
                                + "perdido", "aviso", JOptionPane.YES_NO_OPTION);
                
                if(confirmacao == JOptionPane.YES_NO_OPTION){
                    voltarMenu();
                }
                break;
        }
    } 
}
