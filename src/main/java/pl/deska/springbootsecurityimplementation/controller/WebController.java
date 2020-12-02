package pl.deska.springbootsecurityimplementation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.deska.springbootsecurityimplementation.model.AppUser;
import pl.deska.springbootsecurityimplementation.service.UserService;
import pl.deska.springbootsecurityimplementation.service.Verifier;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class WebController {

    private UserService userService;
    private Verifier verifier;

    @Autowired
    public WebController(UserService userService, Verifier verifier) {
        this.userService = userService;
        this.verifier = verifier;
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/signup")
    public ModelAndView signUp(){
        return new ModelAndView("registration", "user", new AppUser());
    }

    @RequestMapping("/register")
    public ModelAndView register(AppUser appUser, HttpServletRequest request) throws MessagingException {
        boolean result = userService.addUser(appUser, request);
        if(result)
            return new ModelAndView("redirect:/login");
        else
            return new ModelAndView("repeat-registration", "user", new AppUser());
    }

    @RequestMapping("/verify-token")
    public String verifyToken(@RequestParam String token){
        verifier.verifyToken(token);
        return "redirect:/login";
    }
      @RequestMapping("/add-authority")
    public String verifyAdminRole(@RequestParam String token){
        verifier.verifyAdminRole(token);
        return "redirect:/login";
    }



}
