package pl.deska.springbootsecurityimplementation.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class MessageServiceImpl implements MessageService{

    private Counter counter;

    @Autowired
    public MessageServiceImpl(Counter counter) {
        this.counter = counter;
    }


    @Override
    public String helloAdmin(Principal principal) {
        return "Hello admin "  + principal.getName() + ", you are authenticated for " + counter.getVisitNumb(principal.getName()) + " time";
    }

    @Override
    public String helloUser(Principal principal) {
        return "Hello user "  + principal.getName() + ", you are authenticated for " + counter.getVisitNumb(principal.getName()) + " time";
    }

    @Override
    public String helloStranger(Principal principal) {
        String message = null;
        if(principal != null)
            message = "Hello " + principal.getName();
        else
            message = "Hello stranger";
        return message;
    }

    @Override
    public String goodBye() {
        return "Goodbye!";

    }
}
