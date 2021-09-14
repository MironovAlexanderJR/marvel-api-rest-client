package ru.mironov.marvel.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mironov.marvel.entity.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {

}
