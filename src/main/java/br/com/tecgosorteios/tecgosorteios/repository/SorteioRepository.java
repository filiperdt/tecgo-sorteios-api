package br.com.tecgosorteios.tecgosorteios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tecgosorteios.tecgosorteios.model.Sorteio;

@Repository
public interface SorteioRepository extends JpaRepository<Sorteio, Long> {
	
}
