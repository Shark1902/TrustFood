package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long> {

	boolean existsByNomeAndCognome(String nome, String cognome);

	List<Chef> findByNomeIgnoreCaseContainingOrCognomeIgnoreCaseContainingOrOrigineIgnoreCaseContaining(String nome,
			String cognome, String origine);

}
