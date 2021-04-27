package it.polito.tdp.poweroutages.model;
import java.time.*;

public class PowerOutages {
	int id;
	int nerc_id;
	LocalDateTime inizio, fine;
	int persone;
	
	public PowerOutages(int id, int nerc_id, LocalDateTime inizio, LocalDateTime fine, int persone) {
		super();
		this.id = id;
		this.nerc_id = nerc_id;
		this.inizio = inizio;
		this.fine = fine;
		this.persone = persone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNerc_id() {
		return nerc_id;
	}

	public void setNerc_id(int nerc_id) {
		this.nerc_id = nerc_id;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}

	public int getPersone() {
		return persone;
	}

	public void setPersone(int persone) {
		this.persone = persone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		PowerOutages other = (PowerOutages) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	

}
