/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fei.projetorpg.model;

/**
 *
 * @author vitoriamartins
 */
public class GameUtils {
    
    public static int calcVdMax(Protagonista p, int fase){
        if(p == null) return 100;
        
        int vdMax = p.getVidaInicial();
        
        for(int f = 1; f < fase; f++){
            if(f == 1){
                vdMax += 25;
            } else if(f == 4){
                vdMax += 30;
            }
        }
        
        return vdMax;
    }
}
