package it.uniroma3.siw.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.model.ImageEntity;
import it.uniroma3.siw.service.ChefService;
import it.uniroma3.siw.service.ImageEntityService;
import it.uniroma3.siw.service.RistoranteService;
import jakarta.validation.Valid;

@Controller
public class ChefController {
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private RistoranteService ristoranteService;
	
	@Autowired
	private ImageEntityService imageEntityService;
	
	@GetMapping("/chefs")
	public String listaChefs(Model model) {
		model.addAttribute("chefs", chefService.findAll());
		return "chefs";
	}
	
	@GetMapping("/chef/{id}")
	public String mostraChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", chefService.findById(id).getRistoranti());
		model.addAttribute("ristoranti", chefService);
		return "chef";
	}
	
	@GetMapping(value="/admin/dettagliChef/{id}")
	public String mostraDettagliChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", chefService.findById(id));
		return "admin/dettagliChef";
	}
	
	@GetMapping(value="/admin/indexChef")
	public String mostraIndexChef(Model model) {
		model.addAttribute("chefs", chefService.findAll());
		return "admin/indexChef";
	}
	
	@GetMapping(value="/admin/formNewChef")
	public String formNewChef(Model model) {
		model.addAttribute("chef", new Chef());
		model.addAttribute("ristoranti", ristoranteService.findAll());
		return "admin/formNewChef";
	}
	//salva lo chef con foto
	@PostMapping(value="/admin/formNewChef")
	public String salvaChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, @RequestParam("fotoFile") MultipartFile fotoFile, Model model) {
		if(bindingResult.hasErrors()) {
			return "admin/formNewChef";
		}
		try {
			if(!fotoFile.isEmpty()) {
				String name=UUID.randomUUID().toString() + "_" + fotoFile.getOriginalFilename();
				ImageEntity photo= new ImageEntity(name);
				this.imageEntityService.savePhysicalImage(fotoFile.getBytes(), name);
				chef.setFoto(photo);
				chefService.save(chef);
				return "redirect:/chefs";
			}
			model.addAttribute("chefs", chefService.findAll());
			return "admin/formNewChef";
			
		} catch(Exception e) {
			model.addAttribute("msgError", "Errore nel salvataggio dello chef:\n"+ e.toString());
            return "admin/formNewChef";
			
		}
	}
	
	@GetMapping(value="/admin/formUpdateChef/{id}")
	public String formUpdateChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", chefService.findById(id));
		return "admin/formUpdateChef";
	}
	
	@PostMapping(value="/admin/formUpdateChef/{id}")
	public String updateChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "admin/formUpdateChef";
		}
		
		chefService.save(chef);
		return "redirect:/chef/"+chef.getId();
	}
	
	@PostMapping(value="/admin/rimuoviChef/{id}")
	public String rimuoviChef(@PathVariable("id") Long id) {
		chefService.deleteChef(id);
		return "redirect:/chefs";
	}
	
	
	

}
