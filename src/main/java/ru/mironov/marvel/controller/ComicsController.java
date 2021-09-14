package ru.mironov.marvel.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mironov.marvel.dto.ComicsResponseDto;
import ru.mironov.marvel.entity.Comics;
import ru.mironov.marvel.model.CharacterModel;
import ru.mironov.marvel.service.ComicsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/public/comics")
@Tag(name = "Контроллер для комиксов", description = "Контроллер для работы с комиксами. Функционал:\n" +
        "1)Получение всех комиксов;\n" +
        "2)Получение комикса по id;\n" +
        "3)Получение всех персонажей комикса по id;\n" +
        "4)Добавление нового комикса;\n" +
        "5)Изменение имеющегося комикса.")
public class ComicsController {

    private final ComicsService comicsService;

    @GetMapping
    public ResponseEntity<Page<ComicsResponseDto>> getAllComics(Pageable pageable) {
        return ResponseEntity.ok(comicsService.getAllComics(pageable));
    }

    @GetMapping("/{comicId}")
    public ResponseEntity<ComicsResponseDto> getComics(@PathVariable("comicId") Long id) {
        return ResponseEntity.ok(comicsService.getComics(id));
    }

    @GetMapping("/{comicId}/characters")
    public ResponseEntity<Page<CharacterModel>> getComicsByCharacterId(
            @PathVariable("comicId") Long id,
            @PageableDefault(size = 20)Pageable pageable) {
        return ResponseEntity.ok(comicsService.getCharactersByComicId(id, pageable));
    }

    @PostMapping
    public ResponseEntity<Comics> saveCharacter(@RequestBody Comics comics) {
        return ResponseEntity.ok(comicsService.saveComics(comics));
    }

    @PutMapping
    public ResponseEntity<Comics> updateCharacter(@RequestBody Comics comics) {
        return ResponseEntity.ok(comicsService.saveComics(comics));
    }

}
