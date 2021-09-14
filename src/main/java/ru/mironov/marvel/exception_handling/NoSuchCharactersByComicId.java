package ru.mironov.marvel.exception_handling;

public class NoSuchCharactersByComicId extends RuntimeException {

    public NoSuchCharactersByComicId(String message) {
        super(message);
    }

}
