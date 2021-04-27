package it.polito.tdp.poweroutages.model;

import java.util.*;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	private List <PowerOutages> soluzione;
	private List <PowerOutages> partenza;
	private int massimoPersoneSoluzione;
	private List <PowerOutages> parziale;
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
		massimoPersoneSoluzione=0;
	    cerca(parziale, 1, massimoOre, massimoAnni);
			return parziale;
	}

	private void cerca(List<PowerOutages> parziale2, int livello, int massimoOre, int massimoAnni) {
		// casi terminali 
		
		int numeroOre= conteggioOre(parziale);
		int numeroAnni = conteggioAnni(parziale);
		int numeroPersone = conteggioPersone(parziale);
		
		if (partenza.isEmpty())
			return;
		
		if (numeroOre> massimoOre || numeroAnni>massimoAnni)
			return;
		
		if (livello == partenza.size())
			return;
		
		if (numeroPersone> massimoPersoneSoluzione) {
				soluzione = new ArrayList<>(parziale);
				massimoPersoneSoluzione = numeroPersone;
		
		}
		  
		// genero il sottoproblema
		parziale.add(partenza.get(livello));
		cerca(parziale, livello+1, massimoOre, massimoAnni);
		
		//backtracking
		parziale.remove(partenza.get(livello));
		cerca(parziale, livello+1, massimoOre, massimoAnni);
		
	}

	private int conteggioPersone(List<PowerOutages> parziale2) {
		int persone =0;
		for (PowerOutages p: parziale2)
			persone = persone + p.getPersone();
		return persone;
	}

	private int conteggioAnni(List<PowerOutages> parziale2) {
		if (parziale.isEmpty())
			return 0;
		
		PowerOutages vecchio = parziale2.get(0);
		PowerOutages nuovo= parziale2.get(0);
		for (PowerOutages p: parziale2) {
			if (p.getFine().getYear()>(nuovo.getFine().getYear()))
				nuovo =p;
			if(p.getFine().getYear()<(vecchio.getFine().getYear()))
				vecchio = p;
		}
		return (nuovo.getFine().getYear()-vecchio.getFine().getYear());
	}

	private int conteggioOre(List<PowerOutages> parziale2) {
	
		if (parziale.isEmpty())
			return 0;
		
		int numeroOre=0;
		
		for (PowerOutages p: parziale2)
			numeroOre = ((p.getInizio().getHour()-p.getFine().getHour())*60+(p.getInizio().getMinute()-p.getFine().getMinute()));
		
		return (numeroOre/60);
	}

}
