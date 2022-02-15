package br.com.tecgosorteios.tecgosorteios.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tecgosorteios.tecgosorteios.model.Premio;

@Repository
public interface PremioRepository extends JpaRepository<Premio, Long> {
	@Transactional
	@Modifying
	@Query(nativeQuery = true,
			value = "DELETE FROM tecgo_sorteios.tbl_premios WHERE rifa_id = ?1")
	public void deletarTodosPorRifa(Long id);
	
	@Query(nativeQuery = true,
			value = "SELECT * FROM tecgo_sorteios.tbl_premios WHERE rifa_id = ?1 AND numero_id IS null order by id LIMIT 1")
	public Optional<Premio> encontrarTodosPremiosPorRifaNaoSorteados(Long id);
	
	@Query(nativeQuery = true,
			value = "SELECT * FROM tecgo_sorteios.tbl_premios WHERE rifa_id = ?1 order by id")
	public Optional<List<Premio>> encontrarTodosPremiosPorRifa(Long id);
}
