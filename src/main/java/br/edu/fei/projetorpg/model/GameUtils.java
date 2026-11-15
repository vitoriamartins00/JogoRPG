/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.edu.fei.projetorpg.model;

/**
 * classe utilitária responsável por realizar cálculos
 * auxiliares relacionados à progressão da partida.
 *
 * @author vitoriamartins
 */
public class GameUtils {

    /**
     * calcula a vida máxima do protagonista de acordo com
     * sua vida inicial e os bônus recebidos ao avançar
     * pelas fases
     * @param p 
     * @param fase 
     * @return
     */
    public static int calcVdMax(Protagonista p, int fase){

        // retorna um valor padrão caso o protagonista seja nulo
        if(p == null) return 100;

        // captura a vida inicial definida pela classe do protagonista
        int vdMax = p.getVidaInicial();

        // percorre as fases anteriores à fase atual
        for(int f = 1; f < fase; f++){

            // adiciona 25 pontos de vida após a primeira fase
            if(f == 1){
                vdMax += 25;

            // adiciona 30 pontos de vida após a quarta fase
            } else if(f == 4){
                vdMax += 30;
            }
        }
        return vdMax;
    }
}
