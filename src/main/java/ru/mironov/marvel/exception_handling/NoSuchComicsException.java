package ru.mironov.marvel.exception_handling;

public class NoSuchComicsException extends RuntimeException{

    public NoSuchComicsException(String message) {
        super(message);
    }

}
