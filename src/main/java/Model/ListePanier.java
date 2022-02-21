package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class ListePanier implements Serializable {

	private static final long serialVersionUID = 1L;
	
	ArrayList<Panier> liste;
	private int quantiteTotale;
	private double montantTotal;
	
	public ListePanier() {
		
		this.liste = new ArrayList<Panier>();
		this.quantiteTotale = 0;
		this.montantTotal = 0;
		
	}
	
	public ListePanier(ArrayList<Panier> liste, int quantiteTotale, double montantTotal) {
		super();
		this.liste = liste;
		this.quantiteTotale = quantiteTotale;
		this.montantTotal = montantTotal;
	}
	
	public ArrayList<Panier> getListe() {
		return liste;
	}
	
	public void setListe(ArrayList<Panier> liste) {
		this.liste = liste;
	}
	
	public int getQuantiteTotale() {
		return quantiteTotale;
	}
	
	public void setQuantiteTotale(int quantiteTotale) {
		this.quantiteTotale = quantiteTotale;
	}
	
	public double getMontantTotal() {
		return montantTotal;
	}
	
	public void setMontantTotal(double montantTotal) {
		this.montantTotal = montantTotal;
	}
	
}
