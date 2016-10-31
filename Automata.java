package cl.usach.compiladores.laboratorio1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

public class Automata {
	
	public static List<String> listaAlfabeto(String archivo) throws Exception{
		
		String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        List<String> alfabeto = new ArrayList<>();
        
        while((cadena = b.readLine())!=null) {
            StringTokenizer dividir = new StringTokenizer(cadena,",");
            if (dividir.countTokens() > 1){
            	while(dividir.hasMoreTokens()){
            		alfabeto.add(dividir.nextToken().trim());
            	}
            }
        }
        
        b.close();
        
        return alfabeto;
        
	}
	
	public static List<String> listaEstado(String archivo,List<String> listaAlfabeto) throws Exception{
		
		String cadena = new String();
        FileReader f = new FileReader(archivo);
       
        BufferedReader b = new BufferedReader(f);
        
        List<String> estado = new ArrayList<>();
        
        ListIterator<String> iterador = listaAlfabeto.listIterator();
        
        int cont = 0;
        
        while((cadena = b.readLine())!=null) {
            StringTokenizer dividir = new StringTokenizer(cadena,"#");
            if (dividir.countTokens() > 1){
            	while(dividir.hasMoreTokens()){
            		estado.add(dividir.nextToken().trim());
            		cont++;
            	}
            }
        }
    
        b.close();
        
        if(cont == 0){
        	   
        	   cadena = new String();
        	   
        	   while(iterador.hasNext()){
        		   String simbolo = iterador.next();
        		   FileReader f2 = new FileReader(archivo);
        		   BufferedReader b2 = new BufferedReader(f2);
        		   while((cadena = b2.readLine())!=null) {
        			   StringTokenizer dividir = new StringTokenizer(cadena,simbolo);
        			   if (dividir.countTokens() > 1){
	            		   while(dividir.hasMoreTokens()){
	            			   estado.add(dividir.nextToken().trim());   
	            		   }
	            	   }
        		   }
        		   b2.close();
        		   
        	   }
        	 
        }
          
        return estado;
        
	}
	
	public static List<Integer> listaNuevoEstado(List<String> alfabeto, List<String> estado) throws Exception{
		
		ListIterator<String> iterador = alfabeto.listIterator();
		String simbolo = new String();
		int estadoContinuar=0;
		boolean continuar=false;
		List<Integer> listaNuevoEstado = new ArrayList<>();
		int contAlfabeto=0;

		if(estado.get(0).length() > 1){
			//MAYOR A DOS ESTADOS POR AUTOMATA
			while(iterador.hasNext()){
				
				simbolo=iterador.next();
				ListIterator<String> iteradorEstado = estado.listIterator();
				String elemento = new String();
				
				int contX1=0;
				
				while(iteradorEstado.hasNext()){
					
						elemento = iteradorEstado.next();
						StringTokenizer dividir = new StringTokenizer(elemento,simbolo);
						if (dividir.countTokens() > 1){
							int contX2=0;
							int elemEstado=0;
							while(dividir.hasMoreTokens()){
								
								elemEstado = Integer.parseInt(dividir.nextToken());
			            		elemEstado++;
			            		
			            		if( (contAlfabeto == 0) && (contX1 > 0) && (contX2 > 0) && (contX1 == contX2) ){
								   continuar=true;
							   	   estadoContinuar=elemEstado;
							    }
			            		
		                        if(continuar){
									
									if( (contX2 >= contX1) && (contAlfabeto > 0) ){
									   estadoContinuar++;	
									}
									listaNuevoEstado.add(estadoContinuar);
									
		                        }else{
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
		}else{
			//DOS ESTADOS POR AUTOMATA
			
			ListIterator<String> iteradorEstado = estado.listIterator();
			String elemento = new String();
			
			int cantidadEstado = estado.size();
			int cantidadAlfabeto = alfabeto.size();
			int mitad = cantidadEstado / cantidadAlfabeto;
			int cont=0;
			int suma=1;
			while(iteradorEstado.hasNext()){
				
				elemento = iteradorEstado.next();
				if(cont == mitad){
				   suma=3;	
				}
				listaNuevoEstado.add(Integer.parseInt(elemento)+suma);
				
				cont++;
			}
			
		}
		
		return listaNuevoEstado;
	}
	
	public static String parteAutomataUnion(List<String> alfabeto, List<Integer> listaNuevoEstado) throws Exception{
		
		int cantidadAlfabeto = alfabeto.size();
		int cantidadEstado = listaNuevoEstado.size();
		int corte = cantidadEstado / cantidadAlfabeto;
		
		ListIterator<Integer> iterador = listaNuevoEstado.listIterator();
		StringBuilder cadenaFinal = new StringBuilder();
		int cont = 1;
		int cont2 = 1;
		int estado = 0;
		int totalEstado = listaNuevoEstado.size();
		
		int contGeneral=1;
		
		while(iterador.hasNext()){
			
			estado = iterador.next();
			
			if(cont <= corte){
				//PRIMERA PARTE
				if(cont == 1){
					cadenaFinal.append(estado);
				}
				if( (cont >2) && (((cont % 2) != 0) || ((cont % 4) != 0)) ){
					cadenaFinal.append("#");
					cadenaFinal.append(estado);
				}
				if( (cont % 4) == 0){
					cadenaFinal.append(alfabeto.get(0));
					cadenaFinal.append(estado);
				}else{
					if((cont % 2)==0){ 
						int indice=0;
						if( (contGeneral < 5) && (contGeneral==totalEstado) ){
							indice=1;
						}
						cadenaFinal.append(alfabeto.get(indice));
					    cadenaFinal.append(estado);
					}
				}
				cont++;
		
			}else{
				//SEGUNDA PARTE
				if(cont2 == 1){
					cadenaFinal.append("#");
					cadenaFinal.append(estado);
				}
				if( (cont2 >2) && (((cont2 % 2) != 0) || ((cont2 % 4) != 0)) ){
					cadenaFinal.append("#");
					cadenaFinal.append(estado);
				}
				if( (cont2 % 4) == 0){
					cadenaFinal.append(alfabeto.get(1));
					cadenaFinal.append(estado);
				}else{
					if((cont2 % 2)==0){ 
						cadenaFinal.append(alfabeto.get(1));
					    cadenaFinal.append(estado);
					}
				}
				cont2++;
			}
			
			contGeneral++;
			
		}
		
		return cadenaFinal.toString();
	}

	public static String inicioAutomataUnion(List<String> alfabeto,String centroAutomataUnion) throws Exception{
		
		ListIterator<String> iterador = alfabeto.listIterator();
		
		String simbolo = new String();
		String nuevaCadena = new String();
		StringBuilder cadenaRetorno = new StringBuilder();
		
		while(iterador.hasNext()){
			nuevaCadena = centroAutomataUnion;
			simbolo = iterador.next();
			int inicio = nuevaCadena.indexOf(simbolo);
			String valor = nuevaCadena.substring((inicio-1),inicio);
			cadenaRetorno.append("1");
			cadenaRetorno.append("e");
			cadenaRetorno.append(valor);
			cadenaRetorno.append("#");
			nuevaCadena = new  String();
		}	
		
		return cadenaRetorno.toString();
		
	}
	
	public static String finAutomataUnion(List<String> alfabeto,String centroAutomataUnion, List<Integer> listaEstadoNuevo) throws Exception{
		

		ListIterator<String> iterador = alfabeto.listIterator();
		
		String simbolo = new String();
		String nuevaCadena = new String();
		StringBuilder cadenaRetorno = new StringBuilder();
		
		int ultimoEstado = (listaEstadoNuevo.get(listaEstadoNuevo.size()-1))+1;
		
		int cont=1; 
		
		while(iterador.hasNext()){
			nuevaCadena = centroAutomataUnion;
			simbolo = iterador.next();
			int fin = nuevaCadena.lastIndexOf(simbolo);
			String valor = nuevaCadena.substring(fin+1,fin+2);
			cadenaRetorno.append(valor);
			cadenaRetorno.append("e");
			cadenaRetorno.append(ultimoEstado);
			if(cont < alfabeto.size()){
			   cadenaRetorno.append("#");	
			}
			nuevaCadena = new  String();
			cont++;
		}	
		
		return cadenaRetorno.toString();
		
	}
	
	public static void crearArchivoAutomata(String automata) throws Exception{
		
		String archivoResultado = new String("automataResultado.txt");
		File archivo = new File(archivoResultado);
		BufferedWriter bw = null;
		
		try{
			if(archivo.exists()) {
			      bw = new BufferedWriter(new FileWriter(archivo));
			      bw.write(automata);
			} else {
			      bw = new BufferedWriter(new FileWriter(archivo));
			      bw.write(automata);
			}
			
			System.out.println("-----------------");
			System.out.println("Automata generado");
			System.out.println("-----------------");
			
		}catch(Exception e){
			throw new Exception(e);
		}finally{
			bw.close();
		}
		
	}
	
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