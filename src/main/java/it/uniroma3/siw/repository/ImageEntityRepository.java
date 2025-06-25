package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.ImageEntity;

public interface ImageEntityRepository extends CrudRepository<ImageEntity, Long>{

}
