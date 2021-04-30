package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.util.*;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	private List <PowerOutages> soluzione;
	private List <PowerOutages> partenza;
	 public int massimoPersoneSoluzione;
	private List <PowerOutages> parziale;
	private double numOreParziale;
	
	public Model(){
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutages> getPowerOutagesList(Nerc nerc){
		return podao.getPowerOutagesList(nerc);
	}
	
	public List <PowerOutages> trovaEventi(int massimoOre, int massimoAnni, Nerc nerc){
		List <PowerOutages> parziale = new ArrayList<>();
		partenza = podao.getPowerOutagesList(nerc);
		soluzione = new ArrayList<>();
		parziale =new ArrayList<>();
		numOreParziale =0;
		massimoPersoneSoluzione=0;
	    cerca(parziale, 0, massimoOre, massimoAnni);
			return soluzione;
	}

	private void cerca(List<PowerOutages> parziale, int livello, int massimoOre, int massimoAnni) {
		// casi terminali 
		
		double numeroOre= conteggioOre(parziale);
		int numeroAnni = conteggioAnni(parziale);
		int numeroPersone = conteggioPersone(parziale);
		
		if (partenza.isEmpty())
			return;
		
		if (numeroOre> massimoOre || numeroAnni>massimoAnni) 
		{
			return;
		}
		
		if (numeroPersone> massimoPersoneSoluzione) {
				soluzione = new ArrayList<>(parziale);
				massimoPersoneSoluzione = numeroPersone;
				numOreParziale = numeroOre;
				System.out.println(massimoPersoneSoluzione);
		
		}
		
		if (livello == partenza.size()) {
			return;
		}
		
		  
		// genero il sottoproblema
		parziale.add(partenza.get(livello));
		cerca(parziale, livello+1, massimoOre, massimoAnni);
		
		//backtracking
		parziale.remove(partenza.get(livello));
     	cerca(parziale, livello+1, massimoOre, massimoAnni);
		
	}

	private int conteggioPersone(List<PowerOutages> parziale) {
		if (parziale.isEmpty())
			return 0;
		
		int persone =0;
		
		for (PowerOutages p: parziale)
			persone = persone + p.getPersone();
		
		return persone;
	}

	private int conteggioAnni(List<PowerOutages> parziale) {
		if (parziale.isEmpty())
			return 0;
		
		PowerOutages vecchio = parziale.get(0);
		PowerOutages nuovo= parziale.get(0);
		
		for (PowerOutages p: parziale) {
			if (p.getFine().getYear()>(nuovo.getFine().getYear()))
				nuovo =p;
			if(p.getFine().getYear()<(vecchio.getFine().getYear()))
				vecchio = p;
		}
		
		return (nuovo.getFine().getYear()-vecchio.getFine().getYear());
	}

	private double conteggioOre(List<PowerOutages> parziale) {
	
		if (parziale.isEmpty())
			return 0;
		
		double numeroOre=0;
		
		for (PowerOutages p: parziale)
			numeroOre += Duration.between(p.getInizio(), p.getFine()).toMinutes()/60;
			//numeroOre = numeroOre +((p.getFine().getHour()-p.getInizio().getHour())*60+(p.getFine().getMinute()-p.getInizio().getMinute()));
		System.out.println(numeroOre);
		return numeroOre;
	}

}
