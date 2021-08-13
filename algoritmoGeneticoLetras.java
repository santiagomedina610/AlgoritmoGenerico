package algoritmoGenetico;

import static java.lang.System.exit;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * 
 * @author Juan Pablo Laverde Hernández
 * @author Santiago Medina Echeverri
 */

public class algoritmoGeneticoLetras {
	
	static int filas = 6;
	static int columnas = 4;
	static int Nganadores = 3;
	
	static String [][] Poblacion = new String [filas][columnas];
	static String [][] PoblacionTem = new String [filas][columnas];
	static String [] Parejas = new String [filas];
	static String [] Ganadores = new String [Nganadores];
	static double sumatoria =0;
	static String letras= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static String targetf= "PolitecnicoColombianoJaimeIsazaCadavid";
	
	public static void IniciarPoblacion(String [][] Poblacion, String letras) {
		
		System.out.println("************************************");
		System.out.println("*********Iniciar Poblacion**********");
		System.out.println("************************************");
		String Individuo = "";
		Random ri = new Random();
		
		for(int i= 0; i< Parejas.length; i++) {
			Individuo="";
			for(int k=0; k<targetf.length();k++) {
				Individuo+=letras.charAt(ri.nextInt(letras.length()));
			}
			Poblacion[i][0]= "" + i;
			if(Individuo.length()<targetf.length()) {
				Poblacion[i][1] = Individuo+"";
			}else {
				Poblacion[i][1]= Individuo;
			}
			Poblacion[i][2]=""+0;
		}
	}
	
	public static void convertir_individuo(String [][] Poblacion, String target) {
		int contador=0;
		for(int i=0; i< Parejas.length;i++) {
			String individuo = Poblacion[i][1];
			contador=0;
				for(int k=0;k<target.length();k++) {
					if(target.charAt(k)==individuo.charAt(k)) {
						contador++;
					}
				}
				Poblacion[i][2]= ""+contador;
				sumatoria+=contador;
		}
	}
	
	public static double Calidad_Individuo(String [][] Poblacion) {
		double mayor = Double.parseDouble(Poblacion[0][2]);
		double valor=0;
		for(int i=0; i< Parejas.length;i++) {
			mayor= Double.parseDouble(Poblacion[i][2]);
			if(mayor>valor) {
				mayor=valor;
			}
		}
		System.out.println("*******Mejor Adaptado**************");
		System.out.println("*******"+ mayor +"**************");
		return(mayor);
	}
	
	public static String mutacion(String individuo, String target) {
		Random ri= new Random();
		String resultado="";
		String [] vectora = individuo.split("");
		String mutables="",genI;
		String posicionesC= "0-";
		//System.out.println(target.length()+"target-individuo*******"+ individuo);
		
		for(int k=0; k<target.length();k++) {
			if(target.charAt(k)==individuo.charAt(k)) {
				
			}else {
				posicionesC+=""+k+"-";
			}
		}
		posicionesC+="0";
		System.out.println("**PosicionesC******"+posicionesC);
		if(posicionesC.length()>=0) {
			String [] pos = posicionesC.split("-");
			int pos1= ri.nextInt(pos.length);
			int pos2= ri.nextInt(pos.length);
			int gen1= Integer.parseInt(""+pos[pos1]);
			int gen2= Integer.parseInt(""+pos[pos2]);
			
			vectora[gen1]=""+letras.charAt(ri.nextInt(letras.length()));
			//vectora[gen2]=""+letras.charAt(ri.nextInt(letras.length()));
			resultado="";
			for(int i=0;i<vectora.length;i++) {
				resultado+=vectora[i];
			}
			return resultado;
		}else {
			return individuo;
		}
	}
	
	public static String combinar(String individuoA, String individuoB) {
		Random ri = new Random();
		String [] vectorA = individuoA.split("");
		String [] vectorB = individuoB.split("");
		String resultado="";
		int gen;
		for(int i=0; i<individuoA.length()/2;i++) {
			resultado+=vectorA[i];
		}
		for(int i= (individuoA.length()/2); i<individuoB.length(); i++){
			resultado+=vectorB[i];
		}
		return resultado;
	}
	
	public static void Combinacion_Mutacion(String [][] Poblacion, String [][] PoblacionTem) {
		System.out.println("*******Combinacion y Mutacion**************");
		System.out.println("*******************************************");
		Random ri = new Random();
		int puntocruce=0;
		String IndividuoA, IndividuoB, ParejaA = "";
		
		for(int i=0; i< Parejas.length/2; i++) {
			IndividuoA= Poblacion[i][1];
			ParejaA= Parejas[i];
			String cadAdn="";
			IndividuoB= Poblacion[Integer.parseInt(ParejaA)][1];
			cadAdn=combinar(IndividuoA, IndividuoB);
			System.out.println("[" + Poblacion[i][0]+"]" + "[" + Poblacion[i][1]+"] [Cruzado con] ["+ Poblacion[Integer.parseInt(ParejaA)][0]+ "["+ Poblacion[Integer.parseInt(ParejaA)][1]+"]");
			System.out.println("Nuevo Individuo [" + cadAdn+ "]");
			PoblacionTem[i][0] = ""+ i;
			PoblacionTem[i][1] = cadAdn;
		}
		for(int i=0; i< Parejas.length;i++) {
			Poblacion[i][0] = PoblacionTem[i][0];
			Poblacion[i][1] = PoblacionTem[i][1];
		}
		System.out.println("*******Mutacion*******************");
		System.out.println("*******Resultado******************");
		for(int k=0; k<2;k++) {
			int mutado = ri.nextInt(6);
			IndividuoA= Poblacion[mutado][1];
			String cadAdn = mutacion(IndividuoA, targetf);
			Poblacion[mutado][1] = cadAdn;
			System.out.println("["+Poblacion[mutado][0]+"]"+"["+Poblacion[mutado][1]);
		}
	}
	
	public static void Copiarse(String [][] Poblacion, String [][] PoblacionTem) {
		System.out.println("*******Copiarse**************");
		int indice=0, t=0;
		for(int i=0; i< Ganadores.length;i++) {
			int ganador= Integer.parseInt(Ganadores[i]);
			PoblacionTem[indice][0]= ""+ (i+t);
			PoblacionTem[indice+1][0]= ""+ (i+1+t);
			for(int f=1;f<columnas;f++) {
				PoblacionTem[indice][f]=Poblacion[ganador][f];
				PoblacionTem[indice+1][f]=Poblacion[ganador][f];
			}
			indice+=2;
			t++;
		}
		for(int i=0; i<Parejas.length;i++) {
			Poblacion[i][0]=PoblacionTem[i][0];
			Poblacion[i][1]=PoblacionTem[i][1];
		}
	}
	
	public static void verGanadores(String [] Ganadores) {
		System.out.println("*******Ganadores**************");
		int gano=0;
		for(int i=0;i<Ganadores.length;i++) {
			gano= Integer.parseInt(Ganadores[i]);
			System.out.println("["+ Poblacion[gano][0]+"] [" + Poblacion[gano][1]);
		}
	}
	
	public static void Torneo(String [][] Poblacion) {
		System.out.println("*****************************");
		System.out.println("*******Torneo****************");
		String desempenoA="", ParejaA="", desempenoB="";
		int indP=0;
		for(int i=0;i<Parejas.length/2;i++) {
			desempenoA=Poblacion[i][2];
			ParejaA=Parejas[i];
			desempenoB =Poblacion[Integer.parseInt(ParejaA)][2];
			System.out.println("DesempeñoA"+desempenoA+ "DesempeñoB"+desempenoB);
			System.out.println("["+Poblacion[i][0]+"]"
								+ "["+ Poblacion[i][1]+"] ["+Poblacion[i][2]+"]"
								+ "["+ desempenoA + "] contra"
								+"["+ Poblacion[Integer.parseInt(ParejaA)][0]+"]"
								+"["+ Poblacion[Integer.parseInt(ParejaA)][1]+"]"
								+"["+ Poblacion[Integer.parseInt(ParejaA)][2]+"]"
								+"["+ desempenoB+"]");
			if(Double.parseDouble(desempenoA)>= Double.parseDouble(desempenoB)) {
				Ganadores[indP]= Poblacion[i][0];
			}else {
				Ganadores[indP]= ParejaA;
			}
			indP++;
		}
	}
	
	public static void Seleccion_Parejas(String [][] Poblacion) {
		System.out.println("****************************************");
		System.out.println("*******Seleccion Parejas****************");
		String aux= Poblacion[1][0];
		for(int i=0;i<Parejas.length;i++) {
			Parejas[(Parejas.length-1)-i] = Poblacion[i][0];
		}
	}
	
	public static void adaptabilidad(String[][] Poblacion, double sumatoria) {
		for(int i=0; i< Parejas.length;i++) {
			Poblacion[i][3] = "" + (Double.parseDouble(Poblacion[i][2])/sumatoria);
		}
	}
	
	public static void verPoblacion(String [][] Poblacion, boolean pareja) {
		System.out.println("********Poblacion Actual************");
		String Cadena="";
		for(int i=0;i<filas;i++) {
			for(int k=0;k<columnas;k++) {
				Cadena+="["+Poblacion[i][k]+"]";
			}
			if(pareja) {
				Cadena+="Pareja "+Parejas[i]+"\n";
			}else {
				Cadena+=""+ "\n";
			}
			if(Integer.parseInt(Poblacion[i][2])>= targetf.length()) {
				System.out.println("****Superado****"+ Poblacion[i][1]+" Idoneidad "+ Poblacion[i][2]+" ubicados "+ Poblacion[i][3]);
				exit(0);
			}
		}
		System.out.print(Cadena);
	}
	
	public static void main(String[] args) {
	
		IniciarPoblacion(Poblacion, letras);
		verPoblacion(Poblacion, false);
		int iterar=0;
		while(iterar<51000) {
			convertir_individuo(Poblacion, targetf);
			adaptabilidad(Poblacion, sumatoria);
			verPoblacion(Poblacion, true);
			Seleccion_Parejas(Poblacion);
			Torneo(Poblacion);
			verGanadores(Ganadores);
			Copiarse(Poblacion, PoblacionTem);
			verPoblacion(PoblacionTem, true);
			Seleccion_Parejas(Poblacion);
			Combinacion_Mutacion(Poblacion, PoblacionTem);
			iterar++;
		}
		
		verPoblacion(Poblacion, false);

	}

}
