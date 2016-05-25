package it.polito.tdp.rivers.model;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import it.polito.tdp.rivers.db.RiversDAO;
import it.polito.tdp.rivers.model.Evento.tipo;

public class Simulatore {
	private double capienza;
	private double contenuto;
	private double uscitaMinima;
	private double cMed;
	private int mancati;
	private Queue<Evento> eventi;
	
	public void simula(double Q,double c,double min,River r){
		this.capienza=Q;
		this.contenuto=c;
		this.uscitaMinima=min;
		RiversDAO dao= new RiversDAO();
		List<Flow> flows= dao.flows(r);
		generaEventi(flows);
		while(!eventi.isEmpty())
			passo();
	}

	private void passo() {
		Evento e= eventi.remove();
		switch(e.getEvento()){
		case entrata:
			cMed+=e.getQuantita();
			contenuto+=e.getQuantita();
			if(contenuto>capienza)
				// creo evento tracimazione nello stesso giorno
			break;
		case uscita:
			if(e.getQuantita()>contenuto)
				mancati++;
			else
				contenuto=contenuto-e.getQuantita();
			break;
		case tracimazione:
			//svuoto flusso in eccesso
			break;
		default:
			System.out.println("Evento impossibilie");
			break;
		
		}
		
	}

	private void generaEventi(List<Flow> flows) {
		eventi= new PriorityQueue<>();
		for(Flow f:flows){
			Evento e= new Evento(f.getFlow(),f.getDay(),tipo.entrata);
			double uscita;
			if(irrigazione())
				uscita=10*this.uscitaMinima;
			else
				uscita=this.uscitaMinima;
			Evento ev= new Evento(uscita,f.getDay(),tipo.uscita);
			eventi.add(e);
			eventi.add(ev);
		}
	}

	private boolean irrigazione() {
		if(Math.random()>0.95)
			return true;
		else
			return false;
	}
	public double getcMedia() {
		return cMed/(eventi.size()/2);
	}

	public int getMancati() {
		return mancati;
	}
	
	
}
