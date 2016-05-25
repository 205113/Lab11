package it.polito.tdp.rivers.db;

import java.time.LocalDate;

public class Statistiche {
	private LocalDate prima;
	private LocalDate ultima;
	private double fmed;
	private int misurazioni;
	
	
	public Statistiche(LocalDate prima, LocalDate ultima, double fmed, int misurazioni) {
		this.prima = prima;
		this.ultima = ultima;
		this.fmed = fmed;
		this.misurazioni = misurazioni;
	}
	public LocalDate getPrima() {
		return prima;
	}
	public LocalDate getUltima() {
		return ultima;
	}
	public double getFmed() {
		return fmed;
	}
	public int getMisurazioni() {
		return misurazioni;
	}
	
	
}
