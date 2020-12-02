package pl.deska.springbootsecurityimplementation.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.deska.springbootsecurityimplementation.service.message.MessageService;


import java.security.Principal;

@RestController
public class TestApi {

    private MessageService messageService;

    @Autowired
    public TestApi(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/forAll")
    public String forAll(Principal principal){
        return messageService.helloStranger(principal);
    }

    @GetMapping("/forUser")
    public String forUser(Principal principal){
        return messageService.helloUser(principal);
    }

    @GetMapping("/forAdmin")
    public String forAdmin(Principal principal){
        return messageService.helloAdmin(principal);
    }

    @GetMapping("/leave")
    public String goodBye(){
        return messageService.goodBye();
    }

}
