package ru.mironov.marvel.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comics")
@NoArgsConstructor
@Data
@Getter
public class Comics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "characters_comics",
            joinColumns = @JoinColumn(name = "comic_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private List<Character> characters;

    public Comics(Long id, String name, String description, List<Character> characters) {
        this.id = id;
        this.name = name;
        this.description = description;
        for (Character c : characters)
            this.addCharacter(c);
    }

    public void addCharacter(Character character) {
        this.characters.add(character);
        character.getComics().add(this);
    }

    public void removeCharacter(Character character) {
        this.characters.remove(character);
        character.getComics().remove(this);
    }

}
