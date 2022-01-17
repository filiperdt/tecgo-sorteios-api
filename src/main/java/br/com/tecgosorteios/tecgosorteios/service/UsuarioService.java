package br.com.tecgosorteios.tecgosorteios.service;

import org.springframework.http.ResponseEntity;

import br.com.tecgosorteios.tecgosorteios.dto.request.UsuarioRequestDto;

public interface UsuarioService {
	public ResponseEntity<?> listAll();
	
	public ResponseEntity<?> create(UsuarioRequestDto usuarioRequestDto);

	public ResponseEntity<?> read(Long id);

	public ResponseEntity<?> update(Long id, UsuarioRequestDto usuarioRequestDto);
	
	public ResponseEntity<?> delete(Long id);
}
