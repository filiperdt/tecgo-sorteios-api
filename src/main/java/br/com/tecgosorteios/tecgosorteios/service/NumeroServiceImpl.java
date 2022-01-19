package br.com.tecgosorteios.tecgosorteios.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.tecgosorteios.tecgosorteios.dto.request.NumeroRequestDto;
import br.com.tecgosorteios.tecgosorteios.dto.request.UsuarioCompraRequestDto;
import br.com.tecgosorteios.tecgosorteios.dto.response.NumeroResponseDto;
import br.com.tecgosorteios.tecgosorteios.dto.response.RifaResponseDto;
import br.com.tecgosorteios.tecgosorteios.dto.response.UsuarioResponseDto;
import br.com.tecgosorteios.tecgosorteios.model.EnumStatus;
import br.com.tecgosorteios.tecgosorteios.model.Numero;
import br.com.tecgosorteios.tecgosorteios.model.Rifa;
import br.com.tecgosorteios.tecgosorteios.model.Usuario;
import br.com.tecgosorteios.tecgosorteios.repository.NumeroRepository;
import br.com.tecgosorteios.tecgosorteios.repository.RifaRepository;
import br.com.tecgosorteios.tecgosorteios.repository.UsuarioRepository;
import net.minidev.json.JSONObject;

@Service
public class NumeroServiceImpl implements NumeroService {	
	@Autowired
	private NumeroRepository numeroRepository;
	@Autowired
	private RifaRepository rifaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private RifaServiceImpl rifaServiceImpl;
	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	
	public ResponseEntity<JSONObject> retornaJsonMensagem(JSONObject msgResposta, boolean erro, HttpStatus httpStatus) {
		msgResposta.put("erro", erro);
		return ResponseEntity.status(httpStatus).body(msgResposta);
	}
	
	public ResponseEntity<?> listAll() {
		List<Numero> numeros = numeroRepository.findAll();
		List<NumeroResponseDto> numeroResponseDtos = new ArrayList<>();
		
		numeros.stream().forEach(numero -> {
			NumeroResponseDto numeroResponseDto = mapEntityParaDto(numero);
			numeroResponseDtos.add(numeroResponseDto);
		});
		
		return ResponseEntity.ok().body(numeroResponseDtos);
	}

	public ResponseEntity<?> create(NumeroRequestDto numeroRequestDto) {
		Long rifaId = numeroRequestDto.getRifa();
		Optional<Rifa> optionalRifaId = rifaRepository.findById(rifaId);
		
		JSONObject msgResposta = new JSONObject();
		
		if(optionalRifaId.isPresent()) {
			Rifa rifa = optionalRifaId.get();
			
			Numero numero = mapDtoParaEntity(numeroRequestDto, new Numero(), rifa);
			
			numero.setStatus(EnumStatus.RESERVADO);
			Numero numeroSalvo = numeroRepository.save(numero);
			UsuarioResponseDto usuarioResponseDto = numeroSalvo.getUsuario() != null? usuarioServiceImpl.mapEntityParaDto(numeroSalvo.getUsuario()) : null;
			NumeroResponseDto numeroResponseDtoSalvo = mapEntityParaDto(numeroSalvo, rifaServiceImpl.mapEntityParaDto(rifa), usuarioResponseDto);
			return ResponseEntity.created(null).body(numeroResponseDtoSalvo);
		}
		
		msgResposta.put("message", "Rifa #"+rifaId+" não existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> read(Long id) {
		Optional<Numero> optional = this.numeroRepository.findById(id);
		
		if(optional.isPresent()) {
			NumeroResponseDto numeroResponseDto = mapEntityParaDto(optional.get());
			return ResponseEntity.ok().body(numeroResponseDto);
		} else {
			JSONObject msgResposta = new JSONObject();
			msgResposta.put("message", "Número #"+id+" não existe no banco de dados!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public ResponseEntity<?> update(Long id, NumeroRequestDto numeroRequestDto) {
		JSONObject msgResposta = new JSONObject();

		Optional<Numero> optionalNumero = numeroRepository.findById(id);
		
		if(optionalNumero.isPresent()) {
			Long rifaId = numeroRequestDto.getRifa();
			Optional<Rifa> optionalRifaId = rifaRepository.findById(rifaId);
			
			if(optionalRifaId.isPresent()) {
				Rifa rifa = optionalRifaId.get();
				
				Numero numero = new Numero();
				
				numero = mapDtoParaEntity(numeroRequestDto, numero, rifa);

				numero.setId(optionalNumero.get().getId());
				numero.setStatus(optionalNumero.get().getStatus());
				
				Numero numeroSalvo = numeroRepository.save(numero);
				UsuarioResponseDto usuarioResponseDto = numeroSalvo.getUsuario() != null? usuarioServiceImpl.mapEntityParaDto(numeroSalvo.getUsuario()) : null;
				NumeroResponseDto numeroResponseDtoSalvo = mapEntityParaDto(numeroSalvo, rifaServiceImpl.mapEntityParaDto(rifa), usuarioResponseDto);
				return ResponseEntity.ok().body(numeroResponseDtoSalvo);
			}
			
			msgResposta.put("message", "Rifa #"+rifaId+" não existe no banco de dados!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
		
		msgResposta.put("message", "Número #"+id+" não existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> delete(Long id) {
		Optional<Numero> optional = this.numeroRepository.findById(id);
		JSONObject msgResposta = new JSONObject();
		
		if(optional.isPresent()) {
			numeroRepository.deleteById(id);
			msgResposta.put("message", "Número #"+id+" excluído com sucesso!");
			return retornaJsonMensagem(msgResposta, false, HttpStatus.OK);
		}
		
		msgResposta.put("message", "Número #"+id+" não existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}
	
	@Transactional
	public NumeroResponseDto mapEntityParaDto(Numero numero) {
		RifaResponseDto rifaResponseDto = rifaServiceImpl.mapEntityParaDto(numero.getRifa());
		UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
		
		usuarioResponseDto = numero.getUsuario() != null? usuarioServiceImpl.mapEntityParaDto(numero.getUsuario()) : null;
		
		return mapEntityParaDto(numero, rifaResponseDto, usuarioResponseDto);
	}
	
	public NumeroResponseDto mapEntityParaDto(Numero numero, RifaResponseDto rifaResponseDto, UsuarioResponseDto usuarioResponseDto) {
		NumeroResponseDto numeroResponseDto = NumeroResponseDto.builder()
		.id(numero.getId())
		.numero(numero.getNumero())
		.status(numero.getStatus())
		.dataCompra(numero.getDataCompra())
		.rifa(rifaResponseDto)
		.usuario(usuarioResponseDto)
		.build();
		
		return numeroResponseDto;
	}
	
	public Numero mapDtoParaEntity(NumeroRequestDto numeroRequestDto, Numero numero, Rifa rifa) {
		numero = Numero.builder()
		.numero(numeroRequestDto.getNumero())
		.rifa(rifa)
		.build();
		
		return numero;
	}
	
	@Transactional
	public ResponseEntity<?> comprarNumero(UsuarioCompraRequestDto usuarioCompraRequestDto) {
		JSONObject msgResposta = new JSONObject();
		
		Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(usuarioCompraRequestDto.getEmail());
		
		if(optionalUsuario.isPresent()) {
			Optional<Numero> optionalNumero = numeroRepository.findByNumero(usuarioCompraRequestDto.getNumero());
			
			if(!optionalNumero.isPresent()) {
				Optional<Rifa> optionalRifa = rifaRepository.findById(usuarioCompraRequestDto.getRifa());
				
				if(optionalRifa.isPresent()) {
					Usuario usuario = optionalUsuario.get();
					Rifa rifa = optionalRifa.get();
					String numeroString = usuarioCompraRequestDto.getNumero();
					
					Numero numero = Numero.builder()
					.numero(numeroString)
					.status(EnumStatus.RESERVADO)
					.dataCompra(LocalDateTime.now())
					.rifa(rifa)
					.usuario(usuario)
					.build();
					
					Numero numeroSalvo = numeroRepository.save(numero);
					NumeroResponseDto numeroResponseDto = mapEntityParaDto(numeroSalvo);
					return ResponseEntity.ok().body(numeroResponseDto);
				}
				
				msgResposta.put("message", "Rifa #"+usuarioCompraRequestDto.getRifa()+" não existe no banco de dados!");
				return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
			}
			
			msgResposta.put("message", "Número #"+usuarioCompraRequestDto.getNumero()+" indisponível!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
		
		msgResposta.put("message", "Usuário inexistente no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}
}