package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Esperienza;
import it.uniroma3.siw.model.Ristorante;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.EsperienzaRepository;
import jakarta.validation.Valid;

@Service
public class EsperienzaService {
	@Autowired
	private EsperienzaRepository esperienzaRepository;

	public boolean hasUserReviewsRistorante(User user, Ristorante ristorante) {
		// TODO Auto-generated method stub
		return esperienzaRepository.existsByUserAndRistorante(user, ristorante);
	}

	public void save( Esperienza esperienza) {
		esperienzaRepository.save(esperienza);
		
	}

	public Esperienza findById(Long id) {
		// TODO Auto-generated method stub
		return esperienzaRepository.findById(id).get();
	}

	public void deleteEsperienza(Long id) {
		// TODO Auto-generated method stub
		esperienzaRepository.deleteById(id);
	}
	

}
