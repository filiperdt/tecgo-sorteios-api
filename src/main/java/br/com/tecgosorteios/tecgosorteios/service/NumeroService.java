package br.com.tecgosorteios.tecgosorteios.service;

import org.springframework.http.ResponseEntity;

import br.com.tecgosorteios.tecgosorteios.dto.request.NumeroRequestDto;

public interface NumeroService {
	public ResponseEntity<?> listAll();
	
	public ResponseEntity<?> create(NumeroRequestDto numeroRequestDto);

	public ResponseEntity<?> read(Long id);

	public ResponseEntity<?> update(Long id, NumeroRequestDto numeroRequestDto);
	
	public ResponseEntity<?> delete(Long id);
}
