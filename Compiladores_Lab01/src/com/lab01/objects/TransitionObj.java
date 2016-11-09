/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lab01.objects;

/**
 *
 * @author Alejandro
 */
public class TransitionObj {    
    // <editor-fold defaultstate="collapsed" desc="propiedades privadas">
    private int trxDesde;
    private int trxHasta;
    private char trxChar;
    // </editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="constructores">
    public TransitionObj(){
        
    }
    
    public TransitionObj(int trxDesde, int trxHasta, char trxChar){
        this.trxDesde = trxDesde;
        this.trxHasta = trxHasta;
        this.trxChar = trxChar;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="metodos accesores">       
    public int getTrxDesde(){
        return this.trxDesde;
    }
    
    public int getTrxHasta(){
        return this.trxHasta;
    }
    
    public char getTrxChar(){
        return this.trxChar;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="metodos mutadores">   
    public void setTrxDesde(int trxDesde){
        this.trxDesde = trxDesde;
    }
    
    public void setTrxHasta(int trxHasta){
        this.trxHasta = trxHasta;
    }
    
    public void setTrxChar(char trxChar){
        this.trxChar = this.trxChar;
    }
    //</editor-fold>
}
