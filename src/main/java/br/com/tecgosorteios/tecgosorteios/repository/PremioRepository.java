package br.com.tecgosorteios.tecgosorteios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tecgosorteios.tecgosorteios.model.Premio;

@Repository
public interface PremioRepository extends JpaRepository<Premio, Long> {
	
}
