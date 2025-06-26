package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.service.ChefService;
import it.uniroma3.siw.service.RistoranteService;

@Controller
public class SearchController {
	    @Autowired
	    private ChefService chefService;

	    @Autowired
	    private RistoranteService ristoranteService;

	    @GetMapping("/search")
	    public String search(@RequestParam("parola") String parola, Model model) {
	        model.addAttribute("parola", parola);

	        model.addAttribute("chefs", chefService.findByNomeOrCognomeOrOrigine(parola));
	        model.addAttribute("ristoranti", ristoranteService.findByNomeOrCittaOrCucina(parola));

	        return "searchResults";
	    }
	


}
