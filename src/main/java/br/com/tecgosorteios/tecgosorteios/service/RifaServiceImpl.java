package br.com.tecgosorteios.tecgosorteios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.tecgosorteios.tecgosorteios.dto.request.RifaRequestDto;
import br.com.tecgosorteios.tecgosorteios.dto.response.RifaResponseDto;
import br.com.tecgosorteios.tecgosorteios.model.Rifa;
import br.com.tecgosorteios.tecgosorteios.repository.RifaRepository;
import net.minidev.json.JSONObject;

@Service
public class RifaServiceImpl implements RifaService {	
	@Autowired
	private RifaRepository rifaRepository;
	
	public ResponseEntity<JSONObject> retornaJsonMensagem(JSONObject msgResposta, boolean erro, HttpStatus httpStatus) {
		msgResposta.put("erro", erro);
		return ResponseEntity.status(httpStatus).body(msgResposta);
	}
	
	public ResponseEntity<?> listAll() {
		List<Rifa> rifas = rifaRepository.findAll();
		List<RifaResponseDto> rifaResponseDtos = new ArrayList<>();
		
		rifas.stream().forEach(rifa -> {
			RifaResponseDto rifaResponseDto = mapEntityParaDto(rifa);
			rifaResponseDtos.add(rifaResponseDto);
		});
		
		return ResponseEntity.ok().body(rifaResponseDtos);
	}

	@Transactional
	public ResponseEntity<?> create(RifaRequestDto rifaRequestDto) {
		rifaRequestDto.setTitulo(rifaRequestDto.getTitulo().trim());
		
		Rifa rifa = new Rifa();
		rifa = mapDtoParaEntity(rifaRequestDto, rifa);
		
		Rifa rifaSalvo = this.rifaRepository.save(rifa);
		RifaResponseDto rifaResponseDto = mapEntityParaDto(rifaSalvo);
		return ResponseEntity.created(null).body(rifaResponseDto);
	}

	public ResponseEntity<?> read(Long id) {
		Optional<Rifa> optional = this.rifaRepository.findById(id);
		
		if(optional.isPresent()) {
			RifaResponseDto rifaResponseDto = mapEntityParaDto(optional.get());
			return ResponseEntity.ok().body(rifaResponseDto);
		} else {
			JSONObject msgResposta = new JSONObject();
			msgResposta.put("message", "Rifa #"+id+" não existe no banco de dados!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public ResponseEntity<?> update(Long id, RifaRequestDto rifaRequestDto) {
		Optional<Rifa> optional = this.rifaRepository.findById(id);
		JSONObject msgResposta = new JSONObject(); 
		
		if(optional.isPresent()) {
			rifaRequestDto.setTitulo(rifaRequestDto.getTitulo().trim());
			
			Rifa rifa = new Rifa();
			rifa = mapDtoParaEntity(rifaRequestDto, rifa);
			
			rifa.setId(optional.get().getId());
			
			Rifa rifaSalvo = this.rifaRepository.save(rifa);
			RifaResponseDto rifaResponseDto = mapEntityParaDto(rifaSalvo);
			return ResponseEntity.ok().body(rifaResponseDto);
		}
		
		msgResposta.put("message", "Rifa #"+id+" não existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> delete(Long id) {
		Optional<Rifa> optional = this.rifaRepository.findById(id);
		JSONObject msgResposta = new JSONObject();
		
		if(optional.isPresent()) {
			rifaRepository.deleteById(id);
			msgResposta.put("message", "Rifa #"+id+" excluída com sucesso!");
			return retornaJsonMensagem(msgResposta, false, HttpStatus.OK);
		}
		
		msgResposta.put("message", "Rifa #"+id+" não existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}
	
	public RifaResponseDto mapEntityParaDto(Rifa rifa) {
		RifaResponseDto rifaResponseDto = RifaResponseDto.builder()
		.id(rifa.getId())
		.titulo(rifa.getTitulo())
		.valor(rifa.getValor().toString())
		.dataSorteio(rifa.getDataSorteio())
		.build();
		
		return rifaResponseDto;
	}
	
	public Rifa mapDtoParaEntity(RifaRequestDto rifaRequestDto, Rifa rifa) {
		rifa = Rifa.builder()
		.titulo(rifaRequestDto.getTitulo())
		.valor(rifaRequestDto.getValor())
		.dataSorteio(rifaRequestDto.getDataSorteio())
		.build();
		
		return rifa;
	}
}