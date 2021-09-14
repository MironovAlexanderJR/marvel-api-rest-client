package ru.mironov.marvel.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mironov.marvel.exception_handling.NoSuchCharactersByComicId;
import ru.mironov.marvel.exception_handling.NoSuchComicsException;
import ru.mironov.marvel.controller.ComicsController;
import ru.mironov.marvel.model.CharacterModel;
import ru.mironov.marvel.dto.ComicsResponseDto;
import ru.mironov.marvel.entity.Character;
import ru.mironov.marvel.entity.Comics;
import ru.mironov.marvel.entity.repository.ComicsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComicsService {

    private final Logger log = LoggerFactory.getLogger(ComicsController.class);

    private final ComicsRepository comicsRepository;

    public Page<ComicsResponseDto> getAllComics(Pageable pageable) {
        List<Comics> allComicsInOurDB = comicsRepository.findAll();
        List<ComicsResponseDto> comicsResponseDtoList = new ArrayList<>();
        for (Comics comics : allComicsInOurDB) {
            comicsResponseDtoList.add(convertComicsToComicsResponseDto(comics));
        }
        return new PageImpl<>(comicsResponseDtoList);
    }

    public ComicsResponseDto getComics(Long id) {
        Optional<Comics> optional = comicsRepository.findById(id);
        if (!optional.isPresent()) {
            log.error("There is no comic book with such an ID");
            throw new NoSuchComicsException("There is no comic book with such an ID");
        }
        Comics comics = optional.get();

        return convertComicsToComicsResponseDto(comics);
    }

    public Page<CharacterModel> getCharactersByComicId(Long id, Pageable pageable) {
        Optional<Comics> optional = comicsRepository.findById(id);
        if (!optional.isPresent()){
            log.error("The comic has no characters");
            throw new NoSuchCharactersByComicId("The comic has no characters");
        }
        Comics comics = optional.get();
        return new PageImpl<>(convertComicsToComicsResponseDto(comics).getComicsModelList());
    }

    public Comics saveComics(Comics comics) {
        comicsRepository.save(comics);
        return comics;
    }

    private ComicsResponseDto convertComicsToComicsResponseDto(Comics comics) {
        ComicsResponseDto comicsResponseDto = new ComicsResponseDto();
        comicsResponseDto.setId(comics.getId());
        comicsResponseDto.setName(comics.getName());
        comicsResponseDto.setDescription(comics.getDescription());
        List<CharacterModel> characterModelArrayList = new ArrayList<>();
        for (Character c : comics.getCharacters()) {
            CharacterModel characterModel = new CharacterModel();
            characterModel.setId(c.getId());
            characterModel.setName(c.getName());
            characterModel.setDescription(c.getDescription());
            characterModelArrayList.add(characterModel);
        }
        comicsResponseDto.setComicsModelList(characterModelArrayList);
        return comicsResponseDto;
    }

}
