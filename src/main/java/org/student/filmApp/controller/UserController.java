package org.student.filmApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.student.filmApp.entity.User;
import org.student.filmApp.service.SecurityService;
import org.student.filmApp.service.UserService;
import org.student.filmApp.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = { "/start", "/register", "/login" }, method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("loginUserForm", new User());
        model.addAttribute("registerUserForm", new User());

        return "start";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@ModelAttribute("registerUserForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("loginUserForm", new User());
            return "start";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        //return "start";
        return "redirect:/films";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("loginUserForm") User userForm, Model model, String error, String logout) {

        if (error != null) {
            model.addAttribute("error", "Nieprawidłowy login lub hasło");
        }

        if (logout != null) {
            model.addAttribute("message", "Zostałeś wylogowany.");
        }

        //return "start";
        return "redirect:/films";
    }

    /*@RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }*/
}