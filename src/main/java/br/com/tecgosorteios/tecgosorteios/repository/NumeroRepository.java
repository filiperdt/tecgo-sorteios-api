package br.com.tecgosorteios.tecgosorteios.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tecgosorteios.tecgosorteios.model.Numero;

@Repository
public interface NumeroRepository extends JpaRepository<Numero, Long> {
	@Transactional
	@Modifying
	@Query(nativeQuery = true,
			value = "DELETE FROM tecgo_sorteios.tbl_numeros WHERE rifa_id = ?1")
	public void deletarTodosPorRifa(Long id);
	
	@Query(nativeQuery = true,
			value = "SELECT * FROM tecgo_sorteios.tbl_numeros WHERE numero = ?1 AND rifa_id = ?2 AND status = ?3")
	public Optional<Numero> encontrarPorNumeroERifaEStatus(String numero, Long rifa, String status);
	
	public Optional<Numero> findByNumero(String numero);
	
	@Query(nativeQuery = true,
			value = "SELECT * FROM tecgo_sorteios.tbl_numeros WHERE rifa_id = ?1 AND status = ?2")
	public Optional<List<Numero>> encontrarTodosNumerosPorRifaEStatus(Long rifa, String status);
	
	@Query(nativeQuery = true,
			value = "SELECT COUNT(*) FROM tecgo_sorteios.tbl_numeros WHERE rifa_id = ?1 AND status = ?2")
	public Long encontrarQtdeNumerosPorRifaEStatus(Long id, String status);

	@Query(nativeQuery = true,
			value = "SELECT * FROM tecgo_sorteios.tbl_numeros WHERE rifa_id = ?1 order by numero")
	public Optional<List<Numero>> encontrarTodosNumerosPorRifa(Long id);

	@Query(nativeQuery = true,
			value = "SELECT * FROM tecgo_sorteios.tbl_numeros WHERE rifa_id = ?1 AND usuario_id = ?2")
	public Optional<List<Numero>> encontrarTodosNumerosMeusPorRifa(Long id, Long idUsuario);
	
	@Query(nativeQuery = true,
			value = "SELECT COUNT(*) FROM tecgo_sorteios.tbl_numeros WHERE rifa_id = ?1 AND usuario_id = ?2")
	public Long encontrarQtdeNumerosMeusPorRifa(Long id, Long idUsuarioLogado);
}
