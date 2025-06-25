package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UserService userService;

    // Visualizza il profilo utente
    @GetMapping("/profilo")
    public String mostraProfilo(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        Credentials credentials = credentialsService.getCredentials(currentUser.getUsername());
        if (credentials == null)
            return "redirect:/login";

        model.addAttribute("user", credentials.getUser());
        model.addAttribute("username", credentials.getUsername());
        return "user/profilo";
    }

    // Form per modificare profilo
    @GetMapping("/profilo/modificaProfilo")
    public String formModificaProfilo(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        Credentials credentials = credentialsService.getCredentials(currentUser.getUsername());
        if (credentials == null)
            return "redirect:/login";

        model.addAttribute("user", credentials.getUser());
        return "user/modificaProfilo";
    }

    // Salva i dati modificati
    @PostMapping("/profilo/modificaProfilo")
    public String salvaModificheProfilo(@ModelAttribute("user") User updatedUser,
                                        @AuthenticationPrincipal UserDetails currentUser) {

        Credentials credentials = credentialsService.getCredentials(currentUser.getUsername());
        if (credentials == null)
            return "redirect:/login";

        User user = credentials.getUser();
        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());

        userService.saveUser(user);

        return "redirect:/profilo";
    }
}
