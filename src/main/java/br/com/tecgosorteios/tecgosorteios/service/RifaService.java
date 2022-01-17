package br.com.tecgosorteios.tecgosorteios.service;

import org.springframework.http.ResponseEntity;

import br.com.tecgosorteios.tecgosorteios.dto.request.RifaRequestDto;

public interface RifaService {
	public ResponseEntity<?> listAll();
	
	public ResponseEntity<?> create(RifaRequestDto rifaRequestDto);

	public ResponseEntity<?> read(Long id);

	public ResponseEntity<?> update(Long id, RifaRequestDto rifaRequestDto);
	
	public ResponseEntity<?> delete(Long id);
}
