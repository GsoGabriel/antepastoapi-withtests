package com.antepastocompany.antepastoapi.exceptions;

public class AntepastoNotFoundException extends Exception {
    public AntepastoNotFoundException(Long id) {
        super("Antepasto with id " + id + " not found.");
    }
    public AntepastoNotFoundException(String s) {
        super("Antepasto with flavor " + s + " not found.");
    }
}
