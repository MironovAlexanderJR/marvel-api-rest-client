package ru.mironov.marvel.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mironov.marvel.entity.Comics;

public interface ComicsRepository extends JpaRepository<Comics, Long> {

}
