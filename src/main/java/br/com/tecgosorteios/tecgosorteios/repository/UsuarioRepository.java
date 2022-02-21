package br.com.tecgosorteios.tecgosorteios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.tecgosorteios.tecgosorteios.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public Optional<Usuario> findByEmail(String email);
	
	@Query(nativeQuery = true,
			value = "SELECT * FROM tecgo_sorteios.tbl_usuarios WHERE id in ?1 order by id")
	public Optional<List<Usuario>> encontrarTodosUsuariosPorRifa(List<Integer> usuarioIds);
}
