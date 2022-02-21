package br.com.tecgosorteios.tecgosorteios.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
import br.com.tecgosorteios.tecgosorteios.model.Numero;
import br.com.tecgosorteios.tecgosorteios.model.Premio;
import br.com.tecgosorteios.tecgosorteios.model.Rifa;
import br.com.tecgosorteios.tecgosorteios.model.Usuario;
import br.com.tecgosorteios.tecgosorteios.repository.NumeroRepository;
import br.com.tecgosorteios.tecgosorteios.repository.PremioRepository;
import br.com.tecgosorteios.tecgosorteios.repository.RifaRepository;
import br.com.tecgosorteios.tecgosorteios.repository.UsuarioRepository;
import net.minidev.json.JSONObject;

@Service
public class RifaServiceImpl implements RifaService {	
	@Autowired
	private RifaRepository rifaRepository;
	@Autowired
	private PremioRepository premioRepository;
	@Autowired
	private NumeroRepository numeroRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
//	public void iniciarAgendador() {
//		Timer timer = new Timer();
//		Agendador agendador = new Agendador();
//		timer.schedule(agendador, 0, 2000);
//		
//		while(true){
//			System.out.println("Agendador em execução ...");
//			try {
//				Thread.sleep(86400000); // 1 dia em milissegundos
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}		
//	}
	
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
		
		rifa.setDataCriacao(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		
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
			premioRepository.deletarTodosPorRifa(id);
			numeroRepository.deletarTodosPorRifa(id);
			
			rifaRepository.deleteById(id);
			msgResposta.put("message", "Rifa #"+id+" excluída com sucesso!");
			return retornaJsonMensagem(msgResposta, false, HttpStatus.OK);
		}
		
		msgResposta.put("message", "Rifa #"+id+" não existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<?> encontrarTodosPremiosPorRifa(Long id) {
		Optional<List<Premio>> premios = premioRepository.encontrarTodosPremiosPorRifa(id);
		return ResponseEntity.ok().body(premios);
	}

	public ResponseEntity<?> encontrarTodosNumerosPorRifa(Long id) {
		Optional<List<Numero>> numeros = numeroRepository.encontrarTodosNumerosPorRifa(id);
		return ResponseEntity.ok().body(numeros);
	}
	
	public ResponseEntity<?> encontrarTodosNumerosPorRifaEStatus(Long id, String status) {
		Optional<List<Numero>> numeros = numeroRepository.encontrarTodosNumerosPorRifaEStatus(id, status);
		return ResponseEntity.ok().body(numeros);
	}

	public ResponseEntity<?> encontrarQtdeNumerosPorRifaEStatus(Long id, Long idUsuarioLogado) {
		JSONObject qtdeNumeroPorStatus = new JSONObject();
		
		Long qtdeReservado = numeroRepository.encontrarQtdeNumerosPorRifaEStatus(id, "RESERVADO");
		Long qtdePago = numeroRepository.encontrarQtdeNumerosPorRifaEStatus(id, "PAGO");
		Long qtdeMeus = numeroRepository.encontrarQtdeNumerosMeusPorRifa(id, idUsuarioLogado);
		
		qtdeNumeroPorStatus.put("reservado", qtdeReservado);
		qtdeNumeroPorStatus.put("pago", qtdePago);
		qtdeNumeroPorStatus.put("meu", qtdeMeus);
		
		return ResponseEntity.ok().body(qtdeNumeroPorStatus);
	}
	
	public ResponseEntity<?> encontrarTodosNumerosMeusPorRifa(Long id, Long idUsuario) {
		Optional<List<Numero>> numeros = numeroRepository.encontrarTodosNumerosMeusPorRifa(id, idUsuario);
		return ResponseEntity.ok().body(numeros);
	}

	public ResponseEntity<?> encontrarTodosUsuariosPorRifa(JSONObject requestBody) {
		@SuppressWarnings("unchecked")
		List<Integer> usuarioIds = (List<Integer>) requestBody.get("usuarioIds");
		
		Optional<List<Usuario>> usuarios = usuarioRepository.encontrarTodosUsuariosPorRifa(usuarioIds);
		return ResponseEntity.ok().body(usuarios);
	}
	
	public RifaResponseDto mapEntityParaDto(Rifa rifa) {
		RifaResponseDto rifaResponseDto = RifaResponseDto.builder()
		.id(rifa.getId())
		.titulo(rifa.getTitulo())
		.valor(rifa.getValor().toString())
		.dataCriacao(rifa.getDataCriacao())
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