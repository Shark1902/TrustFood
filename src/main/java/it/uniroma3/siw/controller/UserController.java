package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/profilo")
    public String showProfile(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        Credentials credentials = credentialsService.getCredentials(currentUser.getUsername());

        if (credentials == null)
            return "redirect:/login";

        model.addAttribute("user", credentials.getUser());
        model.addAttribute("username", credentials.getUsername());
        return "user/profilo";
    }

    @GetMapping("/profilo/modifica")
    public String editProfileForm(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        Credentials credentials = credentialsService.getCredentials(currentUser.getUsername());

        if (credentials == null)
            return "redirect:/login";

        model.addAttribute("user", credentials.getUser());
        return "user/modificaProfilo";
    }

    @PostMapping("/profilo/modifica")
    public String saveProfile(@ModelAttribute("user") User updatedUser,
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
