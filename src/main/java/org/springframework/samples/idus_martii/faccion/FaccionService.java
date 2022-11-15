package org.springframework.samples.idus_martii.faccion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaccionService {
	
	FaccionRepository repo;
	
	@Autowired
	public FaccionService (FaccionRepository repo) {
		this.repo = repo;
	}
	
	public List<Faccion> getAllFacciones(){
		return repo.findAll();
	}
	
	public Faccion findFaccionById(int id) {
		return repo.findById(id);
	}
	
	public Faccion save(Faccion f) {
		return repo.save(f);
	}
	
	public void  setFaccionSelecionada(int jugadorid, int partidaid, String faccionElegida) {
		 repo.setFaccionSelecionada(jugadorid, partidaid, faccionElegida);
	}
}
