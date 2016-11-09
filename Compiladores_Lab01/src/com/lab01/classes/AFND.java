/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lab01.classes;

import com.lab01.objects.TransitionObj;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class AFND {
    //<editor-fold defaultstate="collapsed" desc="propiedades privadas">    
   private List<Integer> lstVertices;
   private List<TransitionObj> lstTransiciones;
   private int estadoFinal;
   //</editor-fold>
   
   //<editor-fold defaultstate="collapsed" desc="constructores">   
   public AFND(){
       this.lstVertices = new ArrayList<Integer>();
       this.lstTransiciones = new ArrayList<TransitionObj>();
   }
   //</editor-fold>
   
   //<editor-fold defaultstate="collapsed" desc="metodos accesores">   
   public int getCantidadVertices(){
       return this.lstVertices.size();
   }
   
   public int getEstadofinal(){
       return this.estadoFinal;
   }
   
   public void mostrarTransiciones(){
       for(int x = 0; x < this.lstTransiciones.size(); x++){
           Console.
       }
   }
   //</editor-fold>
   
   //<editor-fold defaultstate="collapsed" desc="metodos mutadores">   
   public void setVertice(int vertice){
       for(int x = 0; x < vertice; x++){
           this.lstVertices.add(vertice);
       }
   }
   
   public void setTransicion(TransitionObj trxObj){
       lstTransiciones.add(trxObj);
   }
   
   public void setEstadoFinal(int estadoFinal){
       this.estadoFinal = estadoFinal;
   }
   //</editor-fold>  
}
