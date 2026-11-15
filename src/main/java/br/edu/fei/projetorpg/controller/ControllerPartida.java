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
 *classe responsavel por gerenciar a logica da partida e dos combates, controla
 * os turnos de ataque, uso dos poderes especiais, itens, progressao das fases, 
 * condicoes de vitorias/derrota e salvamento do progresso
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
    
    /**
     * construtor da classe, inicializa a partida, define a fase inicial, obtem a 
     * conexao via Singleton e prepara a lista de inimigos e o estado visual do heroi
     * @param viewPartida
     * @param jogador 
     */
    public ControllerPartida(JFramePartida viewPartida, Jogador jogador){
        this.viewPartida = viewPartida;
        this.jogador = jogador;
        this.protagonista = jogador.getPersonagemOn();
        this.faseAtual = 1;
        this.acao = "a batalha comecou! escolha sua acao";
        this.listaInimigos = new ArrayList<>();
        //realiza a conexao global e de forma segura atraves do Singleton
        this.conexao = Conexao.getInstance();
            
        if(this.protagonista != null){
            this.vdInHeroi = GameUtils.calcVdMax(this.protagonista, 
                    this.faseAtual);
        }
        gerarInimigo();
        attVisual(); 
    }
    
    /**
     * retorna o protagonista atualmente em jogo
     * @return 
     */
    public Protagonista getProtagonista() {
        return protagonista;
    }
    
    /**
     * define o protagonista/heroi da partida
     * @param protagonista 
     */
    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
    }

    /**
     * retorna a lista de inimigos da campanha
     * @return 
     */
    public ArrayList<Inimigo> getListaInimigos() {
        return listaInimigos;
    }
    
    /**
     * define a lista de inimigos da partida
     * @param listaInimigos 
     */
    public void setListaInimigos(ArrayList<Inimigo> listaInimigos) {
        this.listaInimigos = listaInimigos;
    }
    
    /**
     * retorna o numero da fase atual
     * @return 
     */
    public int getFaseAtual() {
        return faseAtual;
    }
    
    /**
     * define a fase atual e recalcula a vida maxima do heroi atualizando a tela
     * @param faseAtual 
     */
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
    
    /**
     * retorna o inimigo correspondente a fase atual
     * @return 
     */
    public Inimigo getInimigoAtual(){
        int indice = this.faseAtual -1;
        if (indice >= 0 && indice < this.listaInimigos.size()){
            return this.listaInimigos.get(indice);
        }
        return null;
    }
    
    /**
     * retorna a mensagem descritiva da acao durante a partida
     * @return 
     */
    public String getAcao(){
        return this.acao;
    }
    
    /**
     * retorna a janela da partida associada
     * @return 
     */
    public JFramePartida getViewPartida() {
        return viewPartida;
    }
    
    /**
     * retorna o jogador associado a partida
     * @return 
     */
    public Jogador getJogador() {
        return jogador;
    }
    
    /**
     * verifica se toda a campanha foi concluidas (todas as fases vencidas)
     * @return 
     */
    public boolean isCampanhaConcluida(){
        return this.faseAtual > faseMax || (this.faseAtual == faseMax &&
                getInimigoAtual() != null && getInimigoAtual().isDerrotado());
    }
    
    /**
     * verifica se o heroi foi derrotado
     * @return 
     */
    public boolean isGameOver(){
        return this.protagonista.isDerrotado() && this.protagonista != null;
    }
    
    /**
     * avanca para proxima fase aplicando o bonus de atributos ao heroi e concedendo
     * recompensas de itens apos a vitoria sobre um inimigo
     */
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
    
    /**
     * gera a recompensa de itens ao vencer uma fase
     * permitindo gerir o inventario caso esteja cheio
     * @param inimigoDerrotado 
     */
    public void concluirFaseVitoria(Inimigo inimigoDerrotado){
        Itens itemGanho = Itens.gerarItem();
        // guarda a info da fase anterior
        String acaoAnterior = this.acao;
        
        if(this.protagonista.getInventario().size() >= 3){
            // recupera  a lista de itens do protagonista
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
                //recupera o item armazenado naquela posicao que vai ser substituido
                Itens itemDescartado = inv.get(esc);
                //substitui o item da posicao escolhida pelo novo
                inv.set(esc, itemGanho);
                
                // registra a troca 
                this.acao = acaoAnterior + " |trocou " + 
                        itemDescartado.getNome() + " por " 
                        + itemGanho.getNome();
            }else{
                this.acao = acaoAnterior + " |manteve o inventario atual";
            }
            
            // registra a vitoria do heroi
            this.protagonista.regVitoria(inimigoDerrotado);
        }else{
            // registra o recebimento do novo item
            this.protagonista.ganharItemInimigo(inimigoDerrotado, itemGanho);
            this.acao = acaoAnterior + " |voce ganhou um novo item " +
                    itemGanho.getNome();
        }
    }
    
    /**
     * executa a acao de ataque basico do heroi/protagonista contra o inimigo atual
     * provocando o contra-ataque do inimigo caso ainda esteja vivo 
     */
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
    
    /**
     * executa a habilidade de poder especial do protagonista contra o inimigo atual
     */
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
    
    /**
     * permite ao jogador selecionar um item do inventario e utilizar durante a 
     * partida
     * @param indiceItem 
     */
    public void usarItem(int indiceItem){
        if(isGameOver()){
            this.acao = "GAME OVER, voce foi derrotado :(";
            desativarBtn();
            attVisual();
            return;
        }
        
        // recupera o inventario do protagonista
        ArrayList<Itens> inv = this.protagonista.getInventario();
        
        //verificando se tem itens no inventario
        if(inv == null || inv.isEmpty()){
            this.acao = "seu inventario esta vazio";
            attVisual();
            return;
        }
        // criando um vetor para armazenar os nomes e efeitos dos itens
        String[] nomeItens = new String[inv.size()];
        //percorre o inventario e preenche o vetor
        for(int i = 0; i < inv.size(); i++){
            nomeItens[i] = inv.get(i).getNome() + " (Efeito: " + 
                    inv.get(i).getDesc()+ ")";
        }
        
        // janela para o jogador escolher o item
        String itemEscolhido = (String) JOptionPane.showInputDialog(
                this.viewPartida, "qual item quer usar: ", "usar item", 
                JOptionPane.QUESTION_MESSAGE, null, nomeItens, nomeItens[0]);
        
        //verifica se o jogador escolheu algum item
        if(itemEscolhido != null){
            // inicializando o indice como invalido
            int indSelecionado = -1;
            
            // buscando a posicao do item escolhido no vetor
            for(int i=0; i < nomeItens.length; i++){
                if(nomeItens[i].equals(itemEscolhido)){
                    indSelecionado = i;
                    break;
                }
            }
            
            // verifica se encontrou uma posicao valida
            if(indSelecionado != -1){
                // recupera o inimigo da fase atual
                Inimigo inimigo = getInimigoAtual();
                // utiliza o item escolhido e recebe a desc do efeito
                String efeitoItem = this.protagonista.usarItem(indSelecionado);
                
                // verificando se existe um inimigo que ainda nao foi derrotado
                if(inimigo != null && !inimigo.isDerrotado()){
                    // o inimigo contra-ataca
                    inimigo.atacar(this.protagonista);
                    // registra a utilizacao do item e o contra-ataque
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
    
    /**
     * executa a acao de defesa do protagonista reduzindo o dano recebido pelo 
     * ataque do inimigo
     */
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
    
    /**
     * define o comportamento de ataque do inimigo durante o turno 
     * @param inimigo 
     */
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
    
    /**
     * verifica se o heroi foi derrotado e atualiza o estado da partida
     */
    private void verificaDerrotaHeroi(){
        if(isGameOver()){
            this.acao = "voce foi derrotado, GAME OVER :((";
            desativarBtn();
        }
    }
    
    /**
     * desativa os botoes de acao na interface grafica quando a partida termina
     */
    private void desativarBtn(){
        if(this.viewPartida != null){
            this.viewPartida.getBtnAtacar().setEnabled(false);
            this.viewPartida.getBtnEspecial().setEnabled(false);
            this.viewPartida.getBtnItem().setEnabled(false);
            this.viewPartida.getBtnDefesa().setEnabled(false);
        }
    }
    
    public void mostrarPersonagem(String classe){
        String caminho = "/img/" + classe.toLowerCase().trim() + ".png";
        
        System.out.println("procurando imagem: " + caminho);
        
        java.net.URL url = getClass().getResource(caminho);
        
        if(url != null){
            javax.swing.ImageIcon img = new javax.swing.ImageIcon(url);
            
            java.awt.Image tamConfig = img.getImage().getScaledInstance
                (this.viewPartida.getLbPersonagem().getWidth(),
                        this.viewPartida.getLbPersonagem().getHeight(), 
                        java.awt.Image.SCALE_SMOOTH);
            
            this.viewPartida.getLbPersonagem().setIcon(new javax.swing.ImageIcon(tamConfig));
            System.out.println("imagem encontrada");
        }else{
            this.viewPartida.getLbPersonagem().setIcon(null);
            System.out.println("imagem nao encontrada: " + caminho);
        }
    }
    
    public void mostrarInimigo(String nomeInimigo){
        String caminho = "/img/" + nomeInimigo.toLowerCase().trim() + ".png";
        
        java.net.URL url = getClass().getResource(caminho);
        
        if(url != null){
            javax.swing.ImageIcon imagem = new javax.swing.ImageIcon(url);
            java.awt.Image ajustada = imagem.getImage().getScaledInstance
                (this.viewPartida.getLbInimigo().getWidth(), 
                        this.viewPartida.getLbInimigo().getHeight(), 
                        java.awt.Image.SCALE_SMOOTH);
            this.viewPartida.getLbInimigo().setIcon(new javax.swing.ImageIcon(ajustada));
            
        }else{
            this.viewPartida.getLbInimigo().setIcon(null);
            System.out.println("imagem nao encontrada: " + caminho);
        }
    }

    
    /**
     * atualiza os componentes visuais da tela da partida
     * (barras de vida, texto de acoes, niveis e fases)
     */
    private void attVisual(){

        if(this.viewPartida != null){
            if(this.protagonista != null){
                // atualizando a imagem de acordo com a classe do heroi
                mostrarPersonagem(this.protagonista.getClass().getSimpleName());
                this.viewPartida.getNmPersonagem().
                        setText(this.protagonista.getNome());
                this.viewPartida.getNvHeroi().setText("nivel: " + 
                        this.protagonista.getNivel());
                this.viewPartida.getNumFase().setText("fase: " + this.faseAtual);
                
                // captura a vida atual e impede o valor negativo 
                int vidaHero = (int) Math.max(0, this.protagonista.getVida());
                // definindo o valor maximo da barra de vida do heroi
                this.viewPartida.getVdHeroi().setMaximum(this.vdInHeroi);
                // atualiza o preenchimento da barra de vida
                this.viewPartida.getVdHeroi().setValue(vidaHero);
                // atualiza o txt com a vida atual e vida maxima
                this.viewPartida.getQtdVidaH().setText(vidaHero + "/" + this.vdInHeroi);
            }
            // recupera o inimigo da fase atual
            Inimigo inimigoAtual = getInimigoAtual();
            
            if(inimigoAtual != null){
                // atualiza a imagem do inimigo
                mostrarInimigo(inimigoAtual.getNome());
                
                int vidaInimigo = (int) Math.max(0, inimigoAtual.getVida());
                
                this.viewPartida.getVdInimigo().setMaximum(this.vdInInimigo);
                this.viewPartida.getNmInimigo().setText(inimigoAtual.getNome());
                this.viewPartida.getVdInimigo().setValue(vidaInimigo);
                this.viewPartida.getQtdVidaI().setText(vidaInimigo + "/" + this.vdInInimigo);
            }else{
                //limpa a imagem quando nao existe inimigo atual
                mostrarInimigo("");
                // indica o inimigo que foi derrotado
                this.viewPartida.getNmInimigo().setText("Derrotado");
                // zera a barra de vida do inimigo
                this.viewPartida.getVdInimigo().setValue(0);
                // zera o txt que representa a vida do inimigo
                this.viewPartida.getQtdVidaI().setText("0 / 0");
            }
            // atualiza a ultma acao
            this.viewPartida.getAcao().setText(this.acao);
        }
    }
    
    /**
     * instancia e gera a lista dos inimigos da campanha em ordem crescente de 
     * dificuldade
     */
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
    
    /**
     * fecha a tela da partida atual e retorna ao menu principal
     */
    public void voltarMenu(){
        this.viewPartida.setVisible(false);
        JFrameMenuJogo telaMenu = new JFrameMenuJogo();
        ControllerMenu controllerMenu = new ControllerMenu(telaMenu, this.jogador);
        telaMenu.setTitle("MENU");
        telaMenu.setController(controllerMenu);
        telaMenu.setVisible(true);
    }
    
    /**
     * abre o menu de pause e da as opcoes de continuar, salvar o jogo com um nome
     *  ou abandonar a sessao para retornar ao menu principal
     */
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
