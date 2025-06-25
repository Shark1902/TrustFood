package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.ChefRepository;

@Component
public class ChefValidator implements Validator{
	@Autowired
	private ChefRepository chefRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Chef.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		// TODO Auto-generated method stub
		Chef autore = (Chef)o;
		if (autore.getNome()!=null && autore.getCognome()!=null 
				&& chefRepository.existsByNomeAndCognome(autore.getNome(), autore.getCognome())) {
			errors.reject("libro.duplicate");
		}
	}

}
