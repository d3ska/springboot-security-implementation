package pl.deska.springbootsecurityimplementation.service.message;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Counter {

    private Map<String, Integer> visitCounter = new HashMap<>();

    public int getVisitNumb(String name){
        updateVisitNumb(name);
        return visitCounter.get(name);
    }

    private void updateVisitNumb(String name) {
        if(visitCounter.containsKey(name)){
            Integer visitNumb = visitCounter.get(name);
            visitCounter.put(name, (visitNumb + 1));
        }else{
            visitCounter.put(name, 1);
        }
    }

}
