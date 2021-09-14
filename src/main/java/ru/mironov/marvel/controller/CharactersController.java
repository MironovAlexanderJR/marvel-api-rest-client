package ru.mironov.marvel.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mironov.marvel.dto.CharacterResponseDto;
import ru.mironov.marvel.entity.Character;
import ru.mironov.marvel.model.ComicsModel;
import ru.mironov.marvel.service.CharactersService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/public/characters")
@Tag(name = "Контроллер для персонажей", description = "Контроллер для работы с персонажами. Функционал:\n" +
        "1)Получение всех персонажей;\n" +
        "2)Получение персонажа по идентификатору;\n" +
        "3)Получение всех комиксов персонажа по id;\n" +
        "4)Добавление нового персонажа;\n" +
        "5)Изменение имеющегося персонажа.")
public class CharactersController {

    private final CharactersService charactersService;

    @GetMapping
    public ResponseEntity<Page<CharacterResponseDto>> getAllCharacters(
            @PageableDefault(size = 20)Pageable pageable) {
        return ResponseEntity.ok(charactersService.getAllCharacters(pageable));
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterResponseDto> getCharacter(@PathVariable("characterId") Long id) {
        return ResponseEntity.ok(charactersService.getCharacter(id));
    }

    @GetMapping("/{characterId}/comics")
    public ResponseEntity<Page<ComicsModel>> getComicsByCharacterId(
            @PathVariable("characterId") Long id,
            @PageableDefault(size = 20)Pageable pageable) {
        return ResponseEntity.ok(charactersService.getComicsByCharacterId(id, pageable));
    }

    @PostMapping
    public ResponseEntity<Character> saveCharacter(@RequestBody Character character) {
        return ResponseEntity.ok(charactersService.saveCharacter(character));
    }

    @PutMapping
    public ResponseEntity<Character> updateCharacter(@RequestBody Character character) {
        return ResponseEntity.ok(charactersService.saveCharacter(character));
    }

}
