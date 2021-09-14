package ru.mironov.marvel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mironov.marvel.model.CharacterModel;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ComicsResponseDto {

    private Long id;

    private String name;

    private String description;

    private List<CharacterModel> comicsModelList;

}
