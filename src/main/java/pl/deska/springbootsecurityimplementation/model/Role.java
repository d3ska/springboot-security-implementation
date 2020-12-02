package pl.deska.springbootsecurityimplementation.model;

import javax.persistence.Entity;

public enum Role {
    USER,
    ADMIN;

    public String getName(){
        return this.name();
    }

}
