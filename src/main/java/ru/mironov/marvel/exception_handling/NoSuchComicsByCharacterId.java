package ru.mironov.marvel.exception_handling;

public class NoSuchComicsByCharacterId extends RuntimeException {

    public NoSuchComicsByCharacterId(String message) {
        super(message);
    }

}
