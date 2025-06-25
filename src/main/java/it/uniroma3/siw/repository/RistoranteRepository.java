package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ristorante;

public interface RistoranteRepository extends CrudRepository<Ristorante, Long> {

	boolean existsByNomeAndCitta(String nome, String citta);

	List<Ristorante> findByNomeIgnoreCaseContainingOrCittaIgnoreCaseContaining(String nome, String citta);

}
