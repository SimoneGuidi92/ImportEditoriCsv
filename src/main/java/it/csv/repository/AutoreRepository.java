package it.csv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.csv.model.Autore;

public interface AutoreRepository extends JpaRepository<Autore, Long> {

}
