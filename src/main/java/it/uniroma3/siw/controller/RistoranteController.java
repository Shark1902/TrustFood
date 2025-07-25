package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.ImageEntity;
import it.uniroma3.siw.model.Ristorante;
import it.uniroma3.siw.service.ChefService;
import it.uniroma3.siw.service.ImageEntityService;
import it.uniroma3.siw.service.RistoranteService;
import jakarta.validation.Valid;

@Controller
public class RistoranteController {
	@Autowired
	private RistoranteService ristoranteService;
	
	@Autowired
	private ChefService chefService;
	@Autowired
	private ImageEntityService imageEntityService;
	
	//lista ristoranti
	@GetMapping("/ristoranti")
	public String mostraRistoranti(Model model) {
		model.addAttribute("ristoranti", ristoranteService.findAll());
		return "ristoranti";
		
	}
	
	//dettagli ristorante
	@GetMapping("/ristorante/{id}")
	public String mostraRistorante(@PathVariable("id") Long id,Model model) {
		model.addAttribute("ristorante", ristoranteService.findById(id));
		return "ristorante";
	}
	
	@GetMapping(value="/admin/dettagliRistorante/{id}")
	public String mostraDettagliRistorante(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ristorante", ristoranteService.findById(id));
		return "admin/dettagliRistorante";
	}
	
	@GetMapping(value="/admin/indexRistorante")
	public String mostraIndexRistorante(Model model) {
		model.addAttribute("ristoranti", ristoranteService.findAll());
		return "admin/indexRistorante";
	}
	
	@GetMapping(value="/admin/formNewRistorante")
	public String formNewRistorante(Model model) {
		model.addAttribute("ristorante", new Ristorante());
		model.addAttribute("chefs", chefService.findAll());
		return "admin/formNewRistorante";
	}
	
	@PostMapping(value="/admin/formNewRistorante")
	public String salvaRistorante(@Valid @ModelAttribute("ristorante") Ristorante ristorante, BindingResult bindingResult, 
			@RequestParam("imageFiles") MultipartFile[] imageFiles, Model model) {
		//controllo errori
		if(bindingResult.hasErrors()) {
			return "admin/formNewRistorante";
		}
		try {
			//gestione immagini
			Set<ImageEntity> images= new HashSet<ImageEntity>();
			for(MultipartFile file : imageFiles) {
				if(!file.isEmpty()) {
					String name= UUID.randomUUID().toString()+ '_' + file.getOriginalFilename();
					ImageEntity img=new ImageEntity(name);
					this.imageEntityService.savePhysicalImage(file.getBytes(), name);
					images.add(img);
					
				}
			}
			ristorante.setImages(images);
			ristoranteService.save(ristorante);
			model.addAttribute("ristoranti", ristoranteService.findAll());
			return "redirect:/ristoranti";
			
			
		} catch(Exception e){
			model.addAttribute("msgError", "Errore nel salvataggio del ristorante:\n"+ e.toString());
            return "admin/formNewRistorante";
		}
		
		
	}
	
	
	@GetMapping(value="/admin/formUpdateRistorante/{id}")
	public String formUpdateRistorante(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ristorante", ristoranteService.findById(id));
		return "admin/formUpdateRistorante";
		
	}
	
	@PostMapping(value="/admin/formUpdateRistorante/{id}")
	public String salvaModificaRistorante(@PathVariable("id") Long id,@Valid @ModelAttribute("ristorante") Ristorante ristorante, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "admin/formUpdateRistorante";
		}
		Ristorante ristoranteEsistente = ristoranteService.findById(id);

	    // Mantieni le immagini già salvate
	    ristorante.setImages(ristoranteEsistente.getImages());
	    ristorante.setChefs(ristoranteEsistente.getChefs());
		ristoranteService.save(ristorante);
		return "redirect:/ristorante/" +ristorante.getId();
	}
	
	@PostMapping(value="/admin/rimuoviRistorante/{id}")
	public String rimuoviRistorante(@PathVariable("id") Long id) {
		ristoranteService.deleteRistorante(id);
		return "redirect:/ristoranti";
		
	}
	//visualizza form per aggiungere uno chef al ristorante
	@GetMapping(value="/admin/formAddChefToRistorante/{id}")
	public String formAddChefTo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ristorante", ristoranteService.findById(id));
		model.addAttribute("chefs", chefService.findAll());
		return "admin/formAddChefToRistorante";
	}
	
	//salva lo chef nel ristorante
	@PostMapping(value="/admin/addChefToRistorante/{id}")
	public String salvaChefToRistorante(@PathVariable("id") Long id,
            @RequestParam Set<Long> chef_id) {
		Ristorante ristorante=ristoranteService.findById(id);
		Iterable<Chef> chefToAdd=chefService.findById(chef_id);
		
		List<Chef> chefs=ristorante.getChefs();
		for(Chef c : chefToAdd) {
			chefs.add(c);
		}
		ristoranteService.save(ristorante);
		return "redirect:/ristorante/"+id;
	}
	
	//rimuovi chef dal ristorante
	@PostMapping(value="/admin/rimuoviChefToRistorante/{ristoranteId}")
	public String rimuoviChefToRistorante(@PathVariable Long ristoranteId, @RequestParam Long chefId) {
		Ristorante ristorante=ristoranteService.findById(ristoranteId);
		Chef chef=chefService.findById(chefId);
		
		ristorante.getChefs().remove(chef);
		
		ristoranteService.save(ristorante);
		return "redirect:/ristorante/"+ristoranteId;
	}
	
	
	

}
