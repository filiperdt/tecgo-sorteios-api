package br.com.tecgosorteios.tecgosorteios.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tecgosorteios.tecgosorteios.model.Rifa;

@Repository
public interface RifaRepository extends JpaRepository<Rifa, Long> {
	@Query(nativeQuery = true,
			value = "SELECT * FROM tecgo_sorteios.tbl_rifas WHERE data_sorteio = ?1")
	public Optional<List<Rifa>> encontrarTodasRifasPorDataSorteio(LocalDateTime dataSorteioAtual);
}
