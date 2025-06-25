package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Esperienza;
import it.uniroma3.siw.model.Ristorante;
import it.uniroma3.siw.model.User;

public interface EsperienzaRepository extends CrudRepository<Esperienza, Long> {

	boolean existsByUserAndRistorante(User user, Ristorante ristorante);

}
