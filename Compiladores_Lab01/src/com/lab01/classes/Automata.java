package cl.usach.compiladores.laboratorio1;

import com.lab01.objects.TransitionObj;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import static java.lang.System.console;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Automata {
    //<editor-fold defaultstate="collapsed" desc="constructores">
    public Automata(){
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="metodos publicos">  
    //<editor-fold defaultstate="collapsed" desc="listaAlfabeto">   
    @SuppressWarnings("resource")
    public List<String> listaAlfabeto(String archivo) throws Exception{	
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        List<String> alfabeto = new ArrayList<>();
        int cantidadLinea = 0;

        while ((cadena = b.readLine()) != null) {
            if (cantidadLinea == 0) {
                StringTokenizer dividir = new StringTokenizer(cadena, ",");

                if (dividir.countTokens() > 1) {
                    while (dividir.hasMoreTokens()) {
                        alfabeto.add(dividir.nextToken().trim());
                    }
                } 
                else if (cadena.length() > 1) {
                    throw new Exception("Ingrese un simbolo correcto.");
                } 
                else {
                    alfabeto.add(cadena.toString());
                }
            }
            cantidadLinea++;
        }

        b.close();

        return alfabeto;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="listaEstado">       
    public List<String> listaEstado(String archivo,List<String> listaAlfabeto) throws Exception{
        String cadena = new String();
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        List<String> estado = new ArrayList<>();
        ListIterator<String> iterador = listaAlfabeto.listIterator();

        int cont = 0;

        while ((cadena = b.readLine()) != null) {
            StringTokenizer dividir = new StringTokenizer(cadena, "#");

            if (dividir.countTokens() > 1) {
                while (dividir.hasMoreTokens()) {
                    estado.add(dividir.nextToken().trim());
                    cont++;
                }
            }
        }

        b.close();

        if (cont == 0) {
            cadena = new String();

            while (iterador.hasNext()) {
                String simbolo = iterador.next();
                FileReader f2 = new FileReader(archivo);
                BufferedReader b2 = new BufferedReader(f2);

                while ((cadena = b2.readLine()) != null) {
                    StringTokenizer dividir = new StringTokenizer(cadena, simbolo);

                    if (dividir.countTokens() > 1) {
                        while (dividir.hasMoreTokens()) {
                            estado.add(dividir.nextToken().trim());
                        }
                    }
                }

                b2.close();
            }
        }

        return estado;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="listaNuevoEstado">        
    public List<Integer> listaNuevoEstado(List<String> alfabeto, List<String> estado) throws Exception{
        ListIterator<String> iterador = alfabeto.listIterator();
        String simbolo = new String();
        int estadoContinuar = 0;
        boolean continuar = false;
        List<Integer> listaNuevoEstado = new ArrayList<>();
        int contAlfabeto = 0;

        //MAYOR A DOS ESTADOS POR AUTOMATA
        if (estado.get(0).length() > 1) {
            while (iterador.hasNext()) {
                simbolo = iterador.next();
                ListIterator<String> iteradorEstado = estado.listIterator();
                String elemento = new String();

                int contX1 = 0;

                while (iteradorEstado.hasNext()) {
                    elemento = iteradorEstado.next();
                    StringTokenizer dividir = new StringTokenizer(elemento, simbolo);

                    if (dividir.countTokens() > 1) {
                        int contX2 = 0;
                        int elemEstado = 0;

                        while (dividir.hasMoreTokens()) {
                            elemEstado = Integer.parseInt(dividir.nextToken());
                            elemEstado++;

                            if ((contAlfabeto == 0) && (contX1 > 0) && (contX2 > 0) && (contX1 == contX2)) {
                                continuar = true;
                                estadoContinuar = elemEstado;
                            }

                            if (continuar) {
                                if ((contX2 >= contX1) && (contAlfabeto > 0)) {
                                    estadoContinuar++;
                                }

                                listaNuevoEstado.add(estadoContinuar);

                            } else {
                                listaNuevoEstado.add(elemEstado);
                            }

                            elemEstado++;
                            contX2++;
                        }

                        iteradorEstado.remove();
                        contX1++;
                    }
                }
                contAlfabeto++;
            }
        } else {
            //DOS ESTADOS POR AUTOMATA
            ListIterator<String> iteradorEstado = estado.listIterator();
            String elemento = new String();

            int cantidadEstado = estado.size();
            int cantidadAlfabeto = alfabeto.size();
            int mitad = cantidadEstado / cantidadAlfabeto;
            int cont = 0;
            int suma = 1;

            while (iteradorEstado.hasNext()) {
                elemento = iteradorEstado.next();
                if (cont == mitad) {
                    suma = 3;
                }
                listaNuevoEstado.add(Integer.parseInt(elemento) + suma);
                cont++;
            }
        }

        return listaNuevoEstado;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="parteAutomataUnion">        
    public String parteAutomataUnion(List<String> alfabeto, List<Integer> listaNuevoEstado) throws Exception{
        int cantidadAlfabeto = alfabeto.size();
        int cantidadEstado = listaNuevoEstado.size();
        int corte = cantidadEstado / cantidadAlfabeto;

        ListIterator<Integer> iterador = listaNuevoEstado.listIterator();
        StringBuilder cadenaFinal = new StringBuilder();

        int cont = 1;
        int cont2 = 1;
        int estado = 0;
        int totalEstado = listaNuevoEstado.size();

        int contGeneral = 1;

        while (iterador.hasNext()) {

            estado = iterador.next();

            if (cont <= corte) {
                //PRIMERA PARTE
                if (cont == 1) {
                    cadenaFinal.append(estado);
                }
                if ((cont > 2) && (((cont % 2) != 0) || ((cont % 4) != 0))) {
                    cadenaFinal.append("#");
                    cadenaFinal.append(estado);
                }
                if ((cont % 4) == 0) {
                    cadenaFinal.append(alfabeto.get(0));
                    cadenaFinal.append(estado);
                } else if ((cont % 2) == 0) {
                    int indice = 0;
                    if ((contGeneral < 5) && (contGeneral == totalEstado)) {
                        indice = 1;
                    }
                    cadenaFinal.append(alfabeto.get(indice));
                    cadenaFinal.append(estado);
                }
                cont++;

            } else {
                //SEGUNDA PARTE
                if (cont2 == 1) {
                    cadenaFinal.append("#");
                    cadenaFinal.append(estado);
                }
                if ((cont2 > 2) && (((cont2 % 2) != 0) || ((cont2 % 4) != 0))) {
                    cadenaFinal.append("#");
                    cadenaFinal.append(estado);
                }
                if ((cont2 % 4) == 0) {
                    cadenaFinal.append(alfabeto.get(1));
                    cadenaFinal.append(estado);
                } else if ((cont2 % 2) == 0) {
                    cadenaFinal.append(alfabeto.get(1));
                    cadenaFinal.append(estado);
                }
                cont2++;
            }
            contGeneral++;
        }
        return cadenaFinal.toString();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="inicioAutomataUnion">       
    public String inicioAutomataUnion(List<String> alfabeto,String centroAutomataUnion) throws Exception{
        ListIterator<String> iterador = alfabeto.listIterator();
        String simbolo = new String();
        String nuevaCadena = new String();
        StringBuilder cadenaRetorno = new StringBuilder();

        while (iterador.hasNext()) {
            nuevaCadena = centroAutomataUnion;
            simbolo = iterador.next();
            int inicio = nuevaCadena.indexOf(simbolo);
            String valor = nuevaCadena.substring((inicio - 1), inicio);
            cadenaRetorno.append("1");
            cadenaRetorno.append("ε");
            cadenaRetorno.append(valor);
            cadenaRetorno.append("#");
            nuevaCadena = new String();
        }

        return cadenaRetorno.toString();	
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="finAutomataUnion">       
    public String finAutomataUnion(List<String> alfabeto,String centroAutomataUnion, List<Integer> listaEstadoNuevo) throws Exception{
        ListIterator<String> iterador = alfabeto.listIterator();
        String simbolo = new String();
        String nuevaCadena = new String();
        StringBuilder cadenaRetorno = new StringBuilder();
        int ultimoEstado = (listaEstadoNuevo.get(listaEstadoNuevo.size() - 1)) + 1;
        int cont = 1;

        while (iterador.hasNext()) {
            nuevaCadena = centroAutomataUnion;
            simbolo = iterador.next();
            int fin = nuevaCadena.lastIndexOf(simbolo);
            String valor = nuevaCadena.substring(fin + 1, fin + 2);
            cadenaRetorno.append(valor);
            cadenaRetorno.append("ε");
            cadenaRetorno.append(ultimoEstado);
            if (cont < alfabeto.size()) {
                cadenaRetorno.append("#");
            }
            nuevaCadena = new String();
            cont++;
        }

        return cadenaRetorno.toString();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="crearAutomataConcatenacion">
    public String crearAutomataConcatenacion(String archivo, List<String> listaAlfabeto) throws Exception{	
        
        //<editor-fold defaultstate="collapsed" desc="declaración de variables ">
        //LECTURA DEL ARCHIVO
        ArrayList<TransitionObj> trxList01 = new ArrayList<>();
        ArrayList<TransitionObj> trxList02 = new ArrayList<>();
        
        String automata = new String();
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        StringBuilder automataConcat = new StringBuilder();
        int cantidadLinea = 0;
        //</editor-fold>
        
        try {
            //AGREGAR 'ε' AL ALFABETO
            listaAlfabeto.add("e");
            
            // Recorremos las lineas del archivo
            while ((automata = b.readLine()) != null) {
                //FORMAR AUTOMATA        		
                StringTokenizer dividir = new StringTokenizer(automata, "#");

                if (dividir.countTokens() > 1) {
                    // Obtenemos el total de transiciones
                    int totalTransiciones = dividir.countTokens();
                    StringBuilder nuevoAutomata = new StringBuilder();
                    int contadorTransicion = 1;
                    
                    // Recorremos las transiciones
                    while (dividir.hasMoreTokens()) {
                        // Creamos un nuevo objeto de tipo TransitionObj
                        // Para almacenar la transición actual del automata
                        TransitionObj currentTrx = new TransitionObj();
                        // Obtenemos la transicion actual
                        String transicion = dividir.nextToken();
                        
                        // Recorremos los simbolos del alfabeto
                        for (String simbolo : listaAlfabeto) {
                            // Preguntamos si la transición actual contiene el simbolo actual
                            if(transicion.contains(simbolo)){
                                // En caso de contenerlo, hacemos un split del string
                                // para poder obtener el conjunto de inicio, el conjunto de termino
                                // y el alfabeto de la transición para almacenarlos en el objeto "currentTrx"
                                String[] trxSplit = transicion.split(simbolo);
                                
                                // Chequeamos que el split haya obtenido más de un elemento
                                if(trxSplit.length > 1){
                                    // Seteamos las propiedades del objeto "currentTrx"
                                    currentTrx.setTrxDesde(Integer.parseInt(trxSplit[0]));
                                    currentTrx.setTrxChar(simbolo);
                                    currentTrx.setTrxHasta(Integer.parseInt(trxSplit[1]));
                                        
                                    // Si la línea actual es igual a 1
                                    // Guardamos el objeto en la lista de transiciones 1 que corresponde
                                    // a las transiciones del primer automáta del archivo txt
                                    if(cantidadLinea == 1){
                                        trxList01.add(currentTrx);
                                    }
                                    else{
                                        // En caso contrario lo guardamos en la segunda lista
                                        trxList02.add(currentTrx);
                                    }
                                    
                                    // Detenemos la iteración
                                    break;
                                }
                            }
                        }
                        // Aumentamos el contador
                        contadorTransicion++;
                    }
                }
                //FIN FORMAR AUTOMATA               
                cantidadLinea++;
            }
            
            // Generamos 4 variables para obtener los siguientes datos:
            // Conjunto menor del Automáta 1
            // Conjunto mayor del Automáta 1
            // Conjunto menor del Automáta 2
            // Conjunto mayor del Automáta 2
            int menor1 = 0;
            int mayor1 = 0;
            int menor2 = 0;
            int mayor2 = 0;
            
            // Creamos una lista en el que guardaremos el automata concatenado
            ArrayList<TransitionObj> trxListFin = new ArrayList<>();

            // Recorremos las transiciones del automata 01
            // Para poder obtener los conjuntos "menor" y "mayor"
            for(int x = 0; x < trxList01.size(); x++){       
                if(menor1 == 0){
                    menor1 = trxList01.get(x).getTrxDesde();
                }
                else{
                    if(trxList01.get(x).getTrxDesde() < menor1){
                        menor1 = trxList01.get(x).getTrxDesde();
                    }
                }
                
                if(trxList01.get(x).getTrxHasta() > mayor1){
                    mayor1 = trxList01.get(x).getTrxHasta();
                }
            }
 
            // Recorremos las transiciones del automata 02
            // Para poder obtener los conjuntos "menor" y "mayor"
            for(int x = 0; x < trxList02.size(); x++){
                if(menor2 == 0){
                    menor2 = trxList02.get(x).getTrxDesde();
                }
                else{
                    if(trxList02.get(x).getTrxDesde() < menor1){
                        menor2 = trxList02.get(x).getTrxDesde();
                    }
                }
                
                if(trxList02.get(x).getTrxHasta() > mayor2){
                    mayor2 = trxList02.get(x).getTrxHasta();
                }
            }
            
            // Recorremos nuevamente el automáta 02 
            // Y aumentamos los valores de los conjuntos dado que en el automata final
            // no se pueden repetir los mismos numeros para los conjuntos
            for(int x = 0; x < trxList02.size(); x++){
                TransitionObj trxAux = trxList02.get(x);
                trxAux.setTrxDesde(trxAux.getTrxDesde() + mayor1);
                trxAux.setTrxHasta(trxAux.getTrxHasta() + mayor1);   
                trxAux.setTrxChar(trxAux.getTrxChar());
            }
            
            // Recorremos el automata 01 y lo guardamos en la lista final
            for(int x = 0; x < trxList01.size(); x++){       
                TransitionObj trxAux = trxList01.get(x);
                
                if(trxAux.getTrxChar().contains("e")){
                    trxAux.setTrxChar("ε");
                }
                
                trxListFin.add(trxAux);
            }
            
            // Creamos una nueva transición que va desde el automata 01
            // al automata 02 con un caracter epsilon 'ε'
            TransitionObj trxAuxConcat = new TransitionObj(mayor1, mayor1 + menor2, "ε");
            trxListFin.add(trxAuxConcat);
            
            // Recorremos el automata 02 y lo guardamos en la lista final
            for(int x = 0; x < trxList02.size(); x++){
                TransitionObj trxAux = trxList02.get(x);
                
                if(trxAux.getTrxChar().contains("e")){
                    trxAux.setTrxChar("ε");
                }
                
                trxListFin.add(trxAux);
            }
            
            // Recorremos el automata final y obtenemos las transiciones
            // para poder almacenar dicha transición en el StringBuilder
            // "automataConcat" que contendrá todas las transiciones del 
            // automata y separará dichos elementos con un numeral '#'
            for(int x = 0; x < trxListFin.size(); x++){
                 TransitionObj trxAux = trxListFin.get(x);
                
                automataConcat.append(trxAux.getTrxDesde()+
                        trxAux.getTrxChar() + 
                        trxAux.getTrxHasta() + "#");
            }
           
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            b.close();
        }
        
        // Retornamos el string con el automata concatenado
        return automataConcat.toString();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="crearArchivoAutomata">        
    public void crearArchivoAutomata(String path, String automata) throws Exception{
        String archivoResultado = new String(path);
        File archivo = new File(archivoResultado);
        BufferedWriter bw = null;

        try {
            if (archivo.exists()) {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(automata);
            } else {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(automata);
            }
            
            String automataPrint = "";
            // Obtenemos las transiciones
            String[] trxArray = automata.split("#");
            // Recorremos las transiciones
            for(int x = 0; x < trxArray.length; x++){
                automataPrint += String.format("Transición %d => %s\n", (x + 1), trxArray[x]);
            }
            // Mostramos el resultado en la consola
            System.out.println("Resultado:");
            System.out.println(automataPrint);
            
            System.out.format("Archivo guardado en: %s", archivo.getPath());
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            bw.close();
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="crearAutomataClausura">       
    public String crearAutomataClausura(String archivo,List<String> listaAlfabeto) throws Exception{	
        //LECTURA DEL ARCHIVO
        String automata = new String();
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        StringBuilder extremoAutomata = new StringBuilder();
        int cantidadLinea = 0;

        try {

            while ((automata = b.readLine()) != null) {

                if (cantidadLinea > 1) {
                    throw new Exception("Para esta opción el automata debe estar expresado en una línea. ");
                }

                //FORMAR AUTOMATA        		
                StringTokenizer dividir = new StringTokenizer(automata, "#");

                if (dividir.countTokens() > 1) {

                    //AGREGAR ε AL ALFABETO
                    listaAlfabeto.add("ε");

                    int estadoMenor = 0;
                    int estadoMayor = 0;

                    int totalTransiciones = dividir.countTokens();
                    StringBuilder nuevoAutomata = new StringBuilder();
                    int contadorTransicion = 1;

                    while (dividir.hasMoreTokens()) {

                        String transicion = dividir.nextToken().trim();
                        int contadorAlfabeto = 1;
                        for (String simbolo : listaAlfabeto) {
                            //DIVIDIR ESTADO
                            StringTokenizer dividirEstado = new StringTokenizer(transicion, simbolo);

                            if (dividirEstado.countTokens() > 1) {
                                int contEstado = 0;
                                while (dividirEstado.hasMoreTokens()) {
                                    int estadoMasUno = Integer.parseInt(dividirEstado.nextToken()) + 1;

                                    //LOGICA ESTADO MAYOR
                                    if (estadoMasUno > estadoMayor) {
                                        estadoMayor = estadoMasUno;
                                    }

                                    //LOGICA ESTADO MENOR
                                    if ((contadorTransicion == 1) && (contEstado == 0)) {
                                        estadoMenor = estadoMasUno;
                                    }

                                    if (estadoMasUno <= estadoMenor) {
                                        estadoMenor = estadoMasUno;
                                    }

                                    nuevoAutomata.append(estadoMasUno);
                                    if (contEstado == 0) {
                                        nuevoAutomata.append(simbolo);
                                    }
                                    contEstado++;
                                }

                                if ((contadorTransicion != totalTransiciones) || (contadorAlfabeto != listaAlfabeto.size())) {
                                    nuevoAutomata.append("#");
                                }

                            }

                            contadorAlfabeto++;
                        }

                        contadorTransicion++;

                    }

                    //GENERAR TRANSICIONES PARA FORMAR CLAUSURA.(x)*
                    extremoAutomata.append("1");
                    extremoAutomata.append("ε");
                    extremoAutomata.append(estadoMenor);
                    extremoAutomata.append("#");
                    //
                    if (listaAlfabeto.size() > 2) {
                        nuevoAutomata.append("#");
                    }

                    nuevoAutomata.append(estadoMayor);
                    nuevoAutomata.append("ε");
                    nuevoAutomata.append(estadoMenor);
                    extremoAutomata.append(nuevoAutomata.toString());
                    extremoAutomata.append("#");
                    extremoAutomata.append(estadoMayor);
                    extremoAutomata.append("ε");
                    extremoAutomata.append(estadoMayor + 1);
                    extremoAutomata.append("#");
                    extremoAutomata.append("1");
                    extremoAutomata.append("ε");
                    extremoAutomata.append(estadoMayor + 1);
                }
                //FIN FORMAR AUTOMATA               
                cantidadLinea++;
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            b.close();
        }

        return extremoAutomata.toString();
    }
    //</editor-fold>      
    
    //<editor-fold defaultstate="collapsed" desc="crearAutomataClausuraPositiva">       
    public String crearAutomataClausuraPositiva(String archivo,List<String> listaAlfabeto) throws Exception{	
        //LECTURA DEL ARCHIVO
        String automata = new String();
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        StringBuilder extremoAutomata = new StringBuilder();
        int cantidadLinea = 0;

        try {

            while ((automata = b.readLine()) != null) {

                if (cantidadLinea > 1) {
                    throw new Exception("Para esta opción el automata debe estar expresado en una línea. ");
                }

                //FORMAR AUTOMATA        		
                StringTokenizer dividir = new StringTokenizer(automata, "#");

                if (dividir.countTokens() > 1) {

                    //AGREGAR ε AL ALFABETO
                    listaAlfabeto.add("ε");

                    int estadoMenor = 0;
                    int estadoMayor = 0;
                    int estadoMenorR = 0;

                    int totalTransiciones = dividir.countTokens();
                    StringBuilder nuevoAutomata = new StringBuilder();
                    int contadorTransicion = 1;

                    while (dividir.hasMoreTokens()) {

                        String transicion = dividir.nextToken().trim();
                        int estadoMenorAux = Character.getNumericValue(transicion.charAt(0));
                        if(estadoMenorR == 0){
                            estadoMenorR = estadoMenorAux;
                        }
                        else{
                            if(estadoMenorAux < estadoMenorR){
                                estadoMenorR = estadoMenorAux;
                            }
                        }
                        
                        int contadorAlfabeto = 1;
                        
                         for (String simbolo : listaAlfabeto) {
                            //DIVIDIR ESTADO
                            StringTokenizer dividirEstado = new StringTokenizer(transicion, simbolo);
                            if (dividirEstado.countTokens() > 1) {
                                int contEstado = 0;
                                while (dividirEstado.hasMoreTokens()) {
                                    int estadoMasUno = Integer.parseInt(dividirEstado.nextToken()) + 1;

                                    //LOGICA ESTADO MAYOR
                                    if (estadoMasUno > estadoMayor) {
                                        estadoMayor = estadoMasUno;
                                    }

                                    //LOGICA ESTADO MENOR
                                    if ((contadorTransicion == 1) && (contEstado == 0)) {
                                        estadoMenor = estadoMasUno;
                                    }

                                    if (estadoMasUno <= estadoMenor) {
                                        estadoMenor = estadoMasUno;
                                    }
                              
                                    contEstado++;
                                }
                            }

                            contadorAlfabeto++;
                        }
                       
                        transicion = transicion.replace("e", "ε");
                        nuevoAutomata.append(transicion);
                        nuevoAutomata.append("#");

                        contadorTransicion++;
                    }
                  
                    //GENERAR TRANSICIONES PARA FORMAR CLAUSURA.(x)+
                    nuevoAutomata.append(estadoMayor);
                    nuevoAutomata.append("ε");
                    nuevoAutomata.append(estadoMenorR);
                    
                    extremoAutomata.append(nuevoAutomata);
                }
                //FIN FORMAR AUTOMATA               
                cantidadLinea++;
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            b.close();
        }

        return extremoAutomata.toString();
    }
    //</editor-fold>   
    
    //</editor-fold>        
}