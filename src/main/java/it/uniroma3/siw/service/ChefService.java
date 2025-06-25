package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.ChefRepository;
import jakarta.validation.Valid;

@Service
public class ChefService {
	
	@Autowired
	private ChefRepository chefRepository;

	public Iterable<Chef> findAll() {
		// TODO Auto-generated method stub
		return chefRepository.findAll();
	}

	public Chef findById(Long id) {
		// TODO Auto-generated method stub
		return chefRepository.findById(id).get();
	}

	public void save( Chef chef) {
		chefRepository.save(chef);
		
	}

	public void deleteChef(Long id) {
		chefRepository.deleteById(id);
		
	}

	public List<Chef> findByNomeOrCognomeOrOrigine(String parola) {
		// TODO Auto-generated method stub
		return this.chefRepository.findByNomeIgnoreCaseContainingOrCognomeIgnoreCaseContainingOrOrigineIgnoreCaseContaining(parola, parola, parola);
	}

}
