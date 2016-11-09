/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores_lab01;

import static compiladores_lab01.Automata.crearArchivoAutomata;
import static compiladores_lab01.Automata.finAutomataUnion;
import static compiladores_lab01.Automata.inicioAutomataUnion;
import static compiladores_lab01.Automata.listaAlfabeto;
import static compiladores_lab01.Automata.listaEstado;
import static compiladores_lab01.Automata.listaNuevoEstado;
import static compiladores_lab01.Automata.parteAutomataUnion;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class Compiladores_Lab01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try{
			
			List<String> listaAlfabeto = listaAlfabeto(args[0].trim());
			List<String> listaEstado = listaEstado(args[0].trim(),listaAlfabeto);
			listaAlfabeto.size();
			listaEstado.size();
			List<Integer> listaEstadoNuevo = listaNuevoEstado(listaAlfabeto, listaEstado);
			listaEstadoNuevo.size();
			
			String centroUnion = parteAutomataUnion(listaAlfabeto, listaEstadoNuevo);
			String inicioUnion = inicioAutomataUnion(listaAlfabeto,centroUnion);
			String finUnion = finAutomataUnion(listaAlfabeto,centroUnion,listaEstadoNuevo);
			
			StringBuilder automataUnion = new StringBuilder();
			automataUnion.append(inicioUnion);
			automataUnion.append(centroUnion);
			automataUnion.append("#");
			automataUnion.append(finUnion);
			
			crearArchivoAutomata(automataUnion.toString());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
