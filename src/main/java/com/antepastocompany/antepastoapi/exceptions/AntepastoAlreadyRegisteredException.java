package com.antepastocompany.antepastoapi.exceptions;

public class AntepastoAlreadyRegisteredException extends Throwable {
    public AntepastoAlreadyRegisteredException(Long id) {
        super ("Antepasto with id " + id + " already exists.");
    }
    public AntepastoAlreadyRegisteredException(String flavor) {
        super ("Antepasto with flavor " + flavor + " already exists.");
    }
}
