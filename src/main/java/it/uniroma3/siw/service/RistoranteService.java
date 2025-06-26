package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ristorante;
import it.uniroma3.siw.repository.RistoranteRepository;
import jakarta.validation.Valid;

@Service
public class RistoranteService {
	
	@Autowired
	private RistoranteRepository ristoranteRepository;

	public boolean existsByNomeAndCitta(String nome, String citta) {
		// TODO Auto-generated method stub
		return ristoranteRepository.existsByNomeAndCitta(nome,citta);
	}

	public Iterable<Ristorante> findAll() {
		// TODO Auto-generated method stub
		return ristoranteRepository.findAll();
	}

	public Ristorante findById(Long id) {
		// TODO Auto-generated method stub
		return ristoranteRepository.findById(id).get();
	}

	public void save( Ristorante ristorante) {
		ristoranteRepository.save(ristorante);
		
	}

	public void deleteRistorante(Long id) {
		ristoranteRepository.deleteById(id);
		
	}

	public List<Ristorante> findByNomeOrCittaOrCucina(String parola) {
		// TODO Auto-generated method stub
		return this.ristoranteRepository.findByNomeIgnoreCaseContainingOrCittaIgnoreCaseContainingOrCucinaIgnoreCaseContaining(parola,parola,parola);
	}

	public List<Ristorante> findTop3ByOrderByIdDesc() {
		// TODO Auto-generated method stub
		return ristoranteRepository.findTop3ByOrderByIdDesc();
	}
	
	

}
