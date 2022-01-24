package entities;

import java.util.ArrayList;
import java.util.List;

public class Pista {
	public static boolean isFree = true; 
	public static boolean acidente;
    public static List<CabineComando> avioesPraDecolar = new ArrayList<CabineComando>();
    public static List<CabineComando> avioesPraAterrissar = new ArrayList<CabineComando>();
    public static List<CabineComando> avioesDecolaram = new ArrayList<CabineComando>();
    public static List<CabineComando> avioesAterrissaram = new ArrayList<CabineComando>();
    public static List<CabineComando> pistaDeTaxiamento = new ArrayList<CabineComando>();
    public static List<CabineComando> PistaAerea = new ArrayList<CabineComando>();
    public static List<Aviao> todosOsAvioes = new ArrayList<Aviao>();
    
}
