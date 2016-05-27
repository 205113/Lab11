package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Evento implements Comparable<Evento>{
	public enum tipo{
		entrata,uscita,tracimazione
	}
	private double quantita;
	private LocalDate data;
	private tipo evento;
	
	
	public Evento(double quantita, LocalDate data, tipo evento) {
		this.quantita = quantita;
		this.data = data;
		this.evento = evento;
	}
	public double getQuantita() {
		return quantita;
	}
	public void setQuantita(double quantita) {
		this.quantita = quantita;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public tipo getEvento() {
		return evento;
	}
	public void setEvento(tipo evento) {
		this.evento = evento;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((evento == null) ? 0 : evento.hashCode());
		long temp;
		temp = Double.doubleToLongBits(quantita);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (evento != other.evento)
			return false;
		if (Double.doubleToLongBits(quantita) != Double.doubleToLongBits(other.quantita))
			return false;
		return true;
	}
	@Override
	public int compareTo(Evento altro) {
		if(this.evento==tipo.tracimazione)
			return -1;
		if(altro.getEvento()==tipo.tracimazione)
			return 1;
		return this.data.compareTo(altro.getData());
	}
	
	
}
