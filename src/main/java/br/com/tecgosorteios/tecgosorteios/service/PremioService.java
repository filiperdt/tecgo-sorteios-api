package br.com.tecgosorteios.tecgosorteios.service;

import org.springframework.http.ResponseEntity;

import br.com.tecgosorteios.tecgosorteios.dto.request.PremioGanhadorRequestDto;
import br.com.tecgosorteios.tecgosorteios.dto.request.PremioRequestDto;

public interface PremioService {
	public ResponseEntity<?> listAll();
	
	public ResponseEntity<?> create(PremioRequestDto premioRequestDto);

	public ResponseEntity<?> read(Long id);

	public ResponseEntity<?> update(Long id, PremioRequestDto premioRequestDto);
	
	public ResponseEntity<?> delete(Long id);
	
	public void atribuirGanhador(PremioGanhadorRequestDto premioGanhadorRequestDto);
}
