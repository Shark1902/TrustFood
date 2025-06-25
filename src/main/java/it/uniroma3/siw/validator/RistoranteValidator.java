package it.uniroma3.siw.validator;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import it.uniroma3.siw.model.Ristorante;
import it.uniroma3.siw.service.RistoranteService;
import jakarta.validation.ConstraintViolation;

import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.metadata.BeanDescriptor;

@Component
public class RistoranteValidator implements Validator{
	@Autowired
	private RistoranteService ristoranteService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Ristorante.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Ristorante ristorante = (Ristorante)o;
		if (ristorante.getNome()!=null && ristorante.getCitta()!=null 
				&& ristoranteService.existsByNomeAndCitta(ristorante.getNome(), ristorante.getCitta())) {
			errors.reject("libro.duplicate");
		
	}
	}
}
