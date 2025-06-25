package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.EsperienzaService;
import it.uniroma3.siw.service.RistoranteService;
import jakarta.validation.Valid;
import it.uniroma3.siw.model.*;
@Controller
public class EsperienzaController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private RistoranteService ristoranteService;
	
	@Autowired
	private EsperienzaService esperienzaService;
	
	
	//visualizza form esperienza
	@GetMapping("/esperienze/formNewEsperienza/{ristorante_id}")
	public String mostraFormEsperienza(@PathVariable("ristorante_id") Long ristorante_id, Model model, Authentication authentication) {
		
		Ristorante ristorante=ristoranteService.findById(ristorante_id);
		//prendi l'utente in questione
		Credentials credentials=credentialsService.getCredentials(authentication.getName());
		User user=credentials.getUser();
				
		//verifica se l'utente ha già recensito il libro
		if(esperienzaService.hasUserReviewsRistorante(user, ristorante)) {
			return "ristorante/"+ristorante_id;
		}
		
		Esperienza esperienza=new Esperienza();
		esperienza.setRistorante(ristorante);
		model.addAttribute("esperienza", esperienza);
		model.addAttribute("ristorante", ristorante);
		return "user/formNewEsperienza";
		
	}
	
	
	//salva l'esperienza dell'utente
	@PostMapping("/esperienze/formNewEsperienza/{ristorante_id}")
	public String salvaEsperienza(@PathVariable("ristorante_id") Long ristorante_id,
			@Valid @ModelAttribute("esperienza") Esperienza esperienza,BindingResult bindingResult,Authentication authentication,Model model) {
		
		Ristorante ristorante=ristoranteService.findById(ristorante_id);
		Credentials credentials=credentialsService.getCredentials(authentication.getName());
		User user=credentials.getUser();
		
		if(bindingResult.hasErrors()) {
			return "user/formNewEsperienza";
		}
		
		esperienza.setRistorante(ristorante);
		esperienza.setUser(user);
		
		esperienzaService.save(esperienza);
		
		return "redirect:/ristorante/"+esperienza.getRistorante().getId();
	}

	
	//admin cancella esperienza
	@PostMapping("/admin/esperienze/{id}/rimuovi")
	public String rimuoviEsperienza(@PathVariable("id") Long id) {
		Esperienza esperienza=esperienzaService.findById(id);
		Long ristoranteId=esperienza.getRistorante().getId();
		esperienzaService.deleteEsperienza(id);
		return "redirect:/ristorante/" +ristoranteId;
	}
	
	
}
