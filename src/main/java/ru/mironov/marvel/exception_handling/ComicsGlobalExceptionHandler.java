package ru.mironov.marvel.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ComicsGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ComicsAndCharactersIncorrectData> handleException(NoSuchComicsException exception) {
        ComicsAndCharactersIncorrectData data = new ComicsAndCharactersIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ComicsAndCharactersIncorrectData> handleException(Exception exception) {
        ComicsAndCharactersIncorrectData data = new ComicsAndCharactersIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ComicsAndCharactersIncorrectData> handleException(NoSuchCharactersException exception) {
        ComicsAndCharactersIncorrectData data = new ComicsAndCharactersIncorrectData();
        data.setInfo(exception.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

}
