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
	private int tracimazioni;
	private int giorni;
	
	public void simula(double Q,double c,double min,River r){
		this.cMed=0;
		this.mancati=0;
		this.capienza=Q;
		this.contenuto=c;
		this.uscitaMinima=min;
		this.tracimazioni=0;
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
			contenuto+=e.getQuantita();
			if(contenuto>capienza+uscitaMinima)
				// creo evento tracimazione nello stesso giorno
				this.eventi.add(new Evento(e.getQuantita(),e.getData(),tipo.tracimazione));
			break;
		case uscita:
			if(e.getQuantita()>contenuto)
				mancati++;
			else
				contenuto=contenuto-e.getQuantita();
			cMed+=contenuto;
			break;
		case tracimazione:
			//svuoto flusso in eccesso
			contenuto=contenuto+e.getQuantita()-capienza-uscitaMinima;
			tracimazioni ++;
			break;
		default:
			System.out.println("Evento impossibilie");
			break;
		
		}
		
	}

	private void generaEventi(List<Flow> flows) {
		eventi= new PriorityQueue<>();
		for(Flow f:flows){
			Evento e= new Evento(f.getFlow()*3600*24,f.getDay(),tipo.entrata);
			double uscita;
			if(irrigazione())
				uscita=10*this.uscitaMinima;
			else
				uscita=this.uscitaMinima;
			Evento ev= new Evento(uscita,f.getDay(),tipo.uscita);
			eventi.add(e);
			eventi.add(ev);
		}
		giorni=eventi.size()/2;
	}

	private boolean irrigazione() {
		if(Math.random()>0.95)
			return true;
		else
			return false;
	}
	public double getcMedia() {
		return cMed/giorni;
	}

	public int getMancati() {
		return mancati;
	}
	
	
}
