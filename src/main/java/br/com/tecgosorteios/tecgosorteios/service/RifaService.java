package br.com.tecgosorteios.tecgosorteios.service;

import org.springframework.http.ResponseEntity;

import br.com.tecgosorteios.tecgosorteios.dto.request.RifaRequestDto;
import net.minidev.json.JSONObject;

public interface RifaService {
	public ResponseEntity<?> listAll();
	
	public ResponseEntity<?> create(RifaRequestDto rifaRequestDto);

	public ResponseEntity<?> read(Long id);

	public ResponseEntity<?> update(Long id, RifaRequestDto rifaRequestDto);
	
	public ResponseEntity<?> delete(Long id);

	public ResponseEntity<?> encontrarTodosPremiosPorRifa(Long id);

	public ResponseEntity<?> encontrarTodosNumerosPorRifa(Long id);
	
	public ResponseEntity<?> encontrarTodosNumerosPorRifaEStatus(Long id, String status);
	
	public ResponseEntity<?> encontrarQtdeNumerosPorRifaEStatus(Long id, Long idUsuarioLogado);
		
	public ResponseEntity<?> encontrarTodosNumerosMeusPorRifa(Long id, Long idUsuario);

	public ResponseEntity<?> encontrarTodosUsuariosPorRifa(JSONObject requestBody);
}
