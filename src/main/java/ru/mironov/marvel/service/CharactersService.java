package ru.mironov.marvel.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mironov.marvel.exception_handling.NoSuchComicsByCharacterId;
import ru.mironov.marvel.exception_handling.NoSuchComicsException;
import ru.mironov.marvel.controller.CharactersController;
import ru.mironov.marvel.dto.CharacterResponseDto;
import ru.mironov.marvel.entity.Character;
import ru.mironov.marvel.entity.Comics;
import ru.mironov.marvel.entity.repository.CharacterRepository;
import ru.mironov.marvel.model.ComicsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharactersService {
    private final Logger log = LoggerFactory.getLogger(CharactersController.class);

    private final CharacterRepository characterRepository;

    public Page<CharacterResponseDto> getAllCharacters(Pageable pageable) {
        Page<Character> allCharacterInOurDB = characterRepository.findAll(pageable);

        return new PageImpl<>(allCharacterInOurDB.stream()
                .map(this::convertCharacterToCharacterResponseDto)
                .collect(Collectors.toList()));
    }

    public CharacterResponseDto getCharacter(Long id) {
        Optional<Character> optional = characterRepository.findById(id);
        if (!optional.isPresent()){
            log.error("There is no character with this ID");
            throw new NoSuchComicsException("There is no character with this ID");
        }
        Character character = optional.get();
        return convertCharacterToCharacterResponseDto(character);
    }

    public Page<ComicsModel> getComicsByCharacterId(Long id, Pageable pageable) {
        Optional<Character> optional = characterRepository.findById(id);
        if (!optional.isPresent()) {
            log.error("The character doesn't have any comics");
            throw new NoSuchComicsByCharacterId("The character doesn't have any comics");
        }
        Character character = optional.get();
        return new PageImpl<>(convertCharacterToCharacterResponseDto(character).getComicsModelList());
    }

    public Character saveCharacter(Character character) {
        characterRepository.save(character);
        return character;
    }

    private CharacterResponseDto convertCharacterToCharacterResponseDto(Character character) {
        CharacterResponseDto characterResponseDto = new CharacterResponseDto();
        characterResponseDto.setId(character.getId());
        characterResponseDto.setName(character.getName());
        characterResponseDto.setDescription(character.getDescription());
        List<ComicsModel> comicsModelList = new ArrayList<>();
        for (Comics c : character.getComics()) {
            ComicsModel comicsModel = new ComicsModel();
            comicsModel.setId(c.getId());
            comicsModel.setName(c.getName());
            comicsModel.setDescription(c.getDescription());
            comicsModelList.add(comicsModel);
        }
        characterResponseDto.setComicsModelList(comicsModelList);
        return characterResponseDto;
    }

}
