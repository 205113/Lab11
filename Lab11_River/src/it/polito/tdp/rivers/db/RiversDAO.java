package it.polito.tdp.rivers.db;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class RiversDAO {

	public List<River> getAllRivers() {
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}
			

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		return rivers;

	}

	public List<Flow> getAllFlows(List<River> rivers) {
		final String sql = "SELECT id, day, flow, river FROM flow";

		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet res = st.executeQuery();

			while (res.next()) {
				flows.add(new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"),
						rivers.get(rivers.indexOf(new River(res.getInt("river"))))));
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		return flows;

	}

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();

		List<River> rivers = dao.getAllRivers();
		System.out.println(rivers);

		List<Flow> flows = dao.getAllFlows(rivers);
		System.out.format("Loaded %d flows\n", flows.size());
		// System.out.println(flows) ;
	}

	public Statistiche statistiche(River r) {

		List<Flow> flows = flows(r);
		LocalDate prima=trovaPrima(flows);
		LocalDate ultima=trovaUltima(flows);
		double fmed= media(flows,prima,ultima);
		Statistiche s= new Statistiche(prima,ultima,fmed,flows.size());
		return s;
	
	}
	public List<Flow> flows(River r){
		final String sql = "SELECT day, flow FROM flow WHERE river=?";
		List<Flow> flows = new LinkedList<Flow>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Flow f= new Flow(res.getDate("day").toLocalDate(),res.getDouble("flow"),r);
				flows.add(f);
			}
			conn.close();
			return flows;
	} catch (SQLException e) {
		e.printStackTrace();
		throw new RuntimeException();
	}
	}
	private double media(List<Flow> flows, LocalDate prima, LocalDate ultima) {
		Flow vecchia=null;
		Flow nuova=null;
		for(Flow f:flows){
			if(f.getDay()==prima)
				vecchia=f;
		}
		for(Flow f:flows){
			if(f.getDay()==ultima)
				nuova=f;
		}
		return (vecchia.getFlow()+nuova.getFlow())/2;
	}

	private LocalDate trovaUltima(List<Flow> flows) {
		LocalDate ultima= flows.get(0).getDay();
		for(Flow f:flows){
			if(f.getDay().isAfter(ultima))
				ultima=f.getDay();
		}
		return ultima;
	}

	private LocalDate trovaPrima(List<Flow> flows) {
		LocalDate prima= flows.get(0).getDay();
		for(Flow f:flows){
			if(f.getDay().isBefore(prima))
				prima=f.getDay();
		}
		return prima;
		
	}

}
