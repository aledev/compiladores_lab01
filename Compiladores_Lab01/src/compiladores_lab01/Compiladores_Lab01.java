/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores_lab01;

import cl.usach.compiladores.laboratorio1.Automata;
import java.util.List;
import java.util.Scanner;

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
            Automata automataCom = new Automata();
            List<String> listaAlfabeto = automataCom.listaAlfabeto(args[0].trim());

            //<editor-fold defaultstate="collapsed" desc="Menu Principal">            
            System.out.println("----------------------------------");
            System.out.println("------GENERADOR DE AUTOMATAS------");
            System.out.println("----------------------------------");
            System.out.println("1) UNION");
            System.out.println("2) CONCATENACI�N");
            System.out.println("3) CLAUSURA");
            System.out.println("4) CLAUSURA POSITIVA");
            System.out.println("5) SALIR");
            System.out.println("----------------------------------");

            //CODIGO INPUT POR TECLADO
            System.out.println("********************");
            System.out.println("INGRESE LA OPCIÓN: ");
            System.out.println("********************");
            //</editor-fold>
            
            Scanner entrada = new Scanner(System.in);

            int caso = entrada.nextInt();
            String mensaje = new String();
            String automata = new String();

            entrada.close();

            switch (caso) {
                case 1:
                    //<editor-fold defaultstate="collapsed" desc="Automata Unión Generado">                    
                    mensaje = "AUTOMATA UNION GENERADO";

                    List<String> listaEstado = automataCom.listaEstado(args[0].trim(), listaAlfabeto);
                    listaAlfabeto.size();
                    listaEstado.size();
                    List<Integer> listaEstadoNuevo = automataCom.listaNuevoEstado(listaAlfabeto, listaEstado);
                    listaEstadoNuevo.size();

                    String centroUnion = automataCom.parteAutomataUnion(listaAlfabeto, listaEstadoNuevo);
                    String inicioUnion = automataCom.inicioAutomataUnion(listaAlfabeto, centroUnion);
                    String finUnion = automataCom.finAutomataUnion(listaAlfabeto, centroUnion, listaEstadoNuevo);

                    StringBuilder automataUnion = new StringBuilder();
                    automataUnion.append(inicioUnion);
                    automataUnion.append(centroUnion);
                    automataUnion.append("#");
                    automataUnion.append(finUnion);

                    automata = automataUnion.toString();
                   
                    break;
                    //</editor-fold>
                case 2:
                    mensaje = "AUTOMATA CONCATENACIÓN GENERADO";
                    break;
                case 3:
                    //<editor-fold defaultstate="collapsed" desc="Automata Clasura Generado">                   
                    mensaje = "AUTOMATA CLAUSURA GENERADO";
                    automata = automataCom.crearAutomataClausura(args[0].trim().toString(), listaAlfabeto);
                    break;
                    //</editor-fold>
                case 4:
                    mensaje = "AUTOMATA CLAUSURA POSITIVA GENERADO";
                    break;
                case 5:
                    break;
            }

            automataCom.crearArchivoAutomata(automata.toString());

            System.out.println("-------------------------------------");
            System.out.println(mensaje);
            System.out.println("-------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
