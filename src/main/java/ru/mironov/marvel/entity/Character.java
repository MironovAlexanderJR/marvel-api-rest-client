package ru.mironov.marvel.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "characters")
@NoArgsConstructor
@Data
@Getter
public class Character {

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
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "comic_id"))
    private List<Comics> comics;

    public Character(Long id, String name, String description, List<Comics> comics) {
        this.id = id;
        this.name = name;
        this.description = description;
        for (Comics c : comics)
            this.addComics(c);
    }

    public void addComics(Comics comics) {
        if (comics != null) {
            this.comics.add(comics);
            comics.getCharacters().add(this);
        }
    }

    public void removeComics(Comics comics) {
        this.comics.remove(comics);
        comics.getCharacters().remove(this);
    }

}
