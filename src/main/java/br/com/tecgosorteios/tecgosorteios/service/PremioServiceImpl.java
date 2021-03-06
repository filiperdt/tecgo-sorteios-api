package br.com.tecgosorteios.tecgosorteios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.tecgosorteios.tecgosorteios.dto.request.PremioGanhadorRequestDto;
import br.com.tecgosorteios.tecgosorteios.dto.request.PremioRequestDto;
import br.com.tecgosorteios.tecgosorteios.dto.response.NumeroResponseDto;
import br.com.tecgosorteios.tecgosorteios.dto.response.PremioResponseDto;
import br.com.tecgosorteios.tecgosorteios.dto.response.RifaResponseDto;
import br.com.tecgosorteios.tecgosorteios.model.EnumStatus;
import br.com.tecgosorteios.tecgosorteios.model.Numero;
import br.com.tecgosorteios.tecgosorteios.model.Premio;
import br.com.tecgosorteios.tecgosorteios.model.Rifa;
import br.com.tecgosorteios.tecgosorteios.repository.NumeroRepository;
import br.com.tecgosorteios.tecgosorteios.repository.PremioRepository;
import br.com.tecgosorteios.tecgosorteios.repository.RifaRepository;
import net.minidev.json.JSONObject;

@Service
public class PremioServiceImpl implements PremioService {	
	@Autowired
	private PremioRepository premioRepository;
	@Autowired
	private RifaServiceImpl rifaServiceImpl;
	@Autowired
	private NumeroServiceImpl numeroServiceImpl;
	@Autowired
	private RifaRepository rifaRepository;
	@Autowired
	private NumeroRepository numeroRepository;
	
	public ResponseEntity<JSONObject> retornaJsonMensagem(JSONObject msgResposta, boolean erro, HttpStatus httpStatus) {
		msgResposta.put("erro", erro);
		return ResponseEntity.status(httpStatus).body(msgResposta);
	}
	
	public ResponseEntity<?> listAll() {
		List<Premio> premios = premioRepository.findAll();
		List<PremioResponseDto> premioResponseDtos = new ArrayList<>();
		
		premios.stream().forEach(premio -> {
			PremioResponseDto premioResponseDto = mapEntityParaDto(premio);
			premioResponseDtos.add(premioResponseDto);
		});
		
		return ResponseEntity.ok().body(premioResponseDtos);
	}

	@Transactional
	public ResponseEntity<?> create(PremioRequestDto premioRequestDto) {
		Long rifaId = premioRequestDto.getRifa();
		Optional<Rifa> optionalRifaId = rifaRepository.findById(rifaId);
		
		JSONObject msgResposta = new JSONObject();
		
		if(optionalRifaId.isPresent()) {
			Rifa rifa = optionalRifaId.get();
			
			Premio premio = mapDtoParaEntity(premioRequestDto, new Premio(), rifa);
			
			Premio premioSalvo = premioRepository.save(premio);
			NumeroResponseDto numeroResponseDto = premioSalvo.getNumero() != null? numeroServiceImpl.mapEntityParaDto(premioSalvo.getNumero()) : null;
			PremioResponseDto premioResponseDtoSalvo = mapEntityParaDto(premioSalvo, rifaServiceImpl.mapEntityParaDto(rifa), numeroResponseDto);
			return ResponseEntity.created(null).body(premioResponseDtoSalvo);
		}
		
		msgResposta.put("message", "Rifa #"+rifaId+" n??o existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> read(Long id) {
		Optional<Premio> optional = this.premioRepository.findById(id);
		
		if(optional.isPresent()) {
			PremioResponseDto premioResponseDto = mapEntityParaDto(optional.get());
			return ResponseEntity.ok().body(premioResponseDto);
		} else {
			JSONObject msgResposta = new JSONObject();
			msgResposta.put("message", "Pr??mio #"+id+" n??o existe no banco de dados!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public ResponseEntity<?> update(Long id, PremioRequestDto premioRequestDto) {
		JSONObject msgResposta = new JSONObject();

		Optional<Premio> optionalPremio = premioRepository.findById(id);
		
		if(optionalPremio.isPresent()) {
			Long rifaId = premioRequestDto.getRifa();
			Optional<Rifa> optionalRifaId = rifaRepository.findById(rifaId);
			
			if(optionalRifaId.isPresent()) {
				Rifa rifa = optionalRifaId.get();

				Premio premio = new Premio();
				
				premio = mapDtoParaEntity(premioRequestDto, premio, rifa);
				
				premio.setId(optionalPremio.get().getId());
				
				Premio premioSalvo = premioRepository.save(premio);
				NumeroResponseDto numeroResponseDto = premioSalvo.getNumero() != null? numeroServiceImpl.mapEntityParaDto(premioSalvo.getNumero()) : null;
				PremioResponseDto premioResponseDtoSalvo = mapEntityParaDto(premioSalvo, rifaServiceImpl.mapEntityParaDto(rifa), numeroResponseDto);
				return ResponseEntity.ok().body(premioResponseDtoSalvo);
			}
			
			msgResposta.put("message", "Rifa #"+rifaId+" n??o existe no banco de dados!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
		
		msgResposta.put("message", "Pr??mio #"+id+" n??o existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> delete(Long id) {
		Optional<Premio> optional = this.premioRepository.findById(id);
		JSONObject msgResposta = new JSONObject();
		
		if(optional.isPresent()) {
			premioRepository.deleteById(id);
			msgResposta.put("message", "Pr??mio #"+id+" exclu??do com sucesso!");
			return retornaJsonMensagem(msgResposta, false, HttpStatus.OK);
		}
		
		msgResposta.put("message", "Pr??mio #"+id+" n??o existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}
	
	@Transactional
	public PremioResponseDto mapEntityParaDto(Premio premio) {
		RifaResponseDto rifaResponseDto = rifaServiceImpl.mapEntityParaDto(premio.getRifa());
		NumeroResponseDto numeroResponseDto = new NumeroResponseDto();
		
		numeroResponseDto = premio.getNumero() != null? numeroServiceImpl.mapEntityParaDto(premio.getNumero()) : null;
		
		return mapEntityParaDto(premio, rifaResponseDto, numeroResponseDto);
	}
	
	public PremioResponseDto mapEntityParaDto(Premio premio, RifaResponseDto rifaResponseDto, NumeroResponseDto numeroResponseDto) {
		PremioResponseDto premioResponseDto = PremioResponseDto.builder()
		.id(premio.getId())
		.nome(premio.getNome())
		.descricao(premio.getDescricao())
		.foto(premio.getFoto())
		.video(premio.getVideo())
		.rifa(rifaResponseDto)
		.numero(numeroResponseDto)
		.build();
		
		return premioResponseDto;
	}
	
	public Premio mapDtoParaEntity(PremioRequestDto premioRequestDto, Premio premio, Rifa rifa) {
		premio = Premio.builder()
		.nome(premioRequestDto.getNome())
		.descricao(premioRequestDto.getDescricao())
		.foto(premioRequestDto.getFoto())
		.video(premioRequestDto.getVideo())
		.rifa(rifa)
		.build();
		
		return premio;
	}
	
	@Transactional
	public void atribuirGanhador(PremioGanhadorRequestDto premioGanhadorRequestDto) {
		Optional<List<Rifa>> optionalListaRifa = rifaRepository.encontrarTodasRifasPorDataSorteio(premioGanhadorRequestDto.getDataSorteioAtual());
		
		if(optionalListaRifa.isPresent()) {
			List<Rifa> listaRifa = optionalListaRifa.get();
			
			listaRifa.stream().forEach(rifa -> {
				premioGanhadorRequestDto.getListaNumeroRequisicao().stream().forEachOrdered(numeroRequisicao -> {
					Optional<Numero> optionalNumero = numeroRepository.encontrarPorNumeroERifaEStatus(numeroRequisicao, Long.valueOf(rifa.getId()), "PAGO");

					if(optionalNumero.isPresent()) {
						Numero numero = optionalNumero.get();
						
						Optional<Premio> optionalPremioDaRifaNaoSorteado = premioRepository.encontrarTodosPremiosPorRifaNaoSorteados(rifa.getId());
						
						if(optionalPremioDaRifaNaoSorteado.isPresent()) {
							Premio premioDaRifaNaoSorteado = optionalPremioDaRifaNaoSorteado.get();
							
							numero.setStatus(EnumStatus.SORTEADO);
							numeroRepository.save(numero);
							
							premioDaRifaNaoSorteado.setNumero(numero);
							premioRepository.save(premioDaRifaNaoSorteado);
						}
					}else {
						sortear(rifa.getId());
					}
				});
				Optional<Premio> optionalPremioDaRifaAindaNaoSorteado = premioRepository.encontrarTodosPremiosPorRifaNaoSorteados(rifa.getId());
				if(optionalPremioDaRifaAindaNaoSorteado.isPresent()) {
					rifa.setDataSorteio(premioGanhadorRequestDto.getDataProximoSorteio());
					rifaRepository.save(rifa);
				}
			});
		}
	}
	
	@Transactional
	public void sortear(Long rifaId) {
		Optional<List<Numero>> optionalTodosNumerosPagos = numeroRepository.encontrarTodosNumerosPorRifaEStatus(rifaId, "PAGO");
		if(optionalTodosNumerosPagos.isPresent()) {
			List<Numero> listaTodosNumerosPagos = optionalTodosNumerosPagos.get();

			if(listaTodosNumerosPagos.size() > 0) {
				Random random = new Random();
				int numeroAleat??rio = random.nextInt(listaTodosNumerosPagos.size());
				Numero numeroSorteado = listaTodosNumerosPagos.get(numeroAleat??rio);
				Optional<Premio> optionalPremioDaRifaNaoSorteado = premioRepository.encontrarTodosPremiosPorRifaNaoSorteados(numeroSorteado.getRifa().getId());
				
				if(optionalPremioDaRifaNaoSorteado.isPresent()) {
					Premio premioDaRifaNaoSorteado = optionalPremioDaRifaNaoSorteado.get();
					
					premioDaRifaNaoSorteado.setNumero(numeroSorteado);
					premioRepository.save(premioDaRifaNaoSorteado);
					
					numeroSorteado.setStatus(EnumStatus.SORTEADO);
					numeroRepository.save(numeroSorteado);
				}
			}
		}
	}
}