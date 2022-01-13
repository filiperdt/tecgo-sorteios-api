package br.com.tecgosorteios.tecgosorteios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tecgosorteios.tecgosorteios.model.Rifa;

@Repository
public interface RifaRepository extends JpaRepository<Rifa, Long> {
	
}
