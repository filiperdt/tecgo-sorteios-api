package br.com.tecgosorteios.tecgosorteios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.tecgosorteios.tecgosorteios.dto.request.UsuarioRequestDto;
import br.com.tecgosorteios.tecgosorteios.dto.response.UsuarioResponseDto;
import br.com.tecgosorteios.tecgosorteios.model.Usuario;
import br.com.tecgosorteios.tecgosorteios.repository.UsuarioRepository;
import net.minidev.json.JSONObject;

@Service
public class UsuarioServiceImpl implements UsuarioService {	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public JSONObject verificaExistenciaCreate(String email, JSONObject msgResposta){
		if(usuarioRepository.findByEmail(email).isPresent()) {
			msgResposta.put("email", "Email '" + email + "' já existe no banco!");
		}
		
		return msgResposta;
	}
	
	public JSONObject verificaExistenciaUpdate(String email, JSONObject msgResposta, Usuario usuario){
		if(usuarioRepository.findByEmail(email).isPresent()) {
			msgResposta.put("email", "Email '" + email + "' já existe no banco!");
		}
		
		return msgResposta;
	}
	
	public ResponseEntity<JSONObject> retornaJsonMensagem(JSONObject msgResposta, boolean erro, HttpStatus httpStatus) {
		msgResposta.put("erro", erro);
		return ResponseEntity.status(httpStatus).body(msgResposta);
	}
	
	public ResponseEntity<?> listAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<UsuarioResponseDto> usuarioResponseDtos = new ArrayList<>();
		
		usuarios.stream().forEach(usuario -> {
			UsuarioResponseDto usuarioResponseDto = mapEntityParaDto(usuario);
			usuarioResponseDtos.add(usuarioResponseDto);
		});
		
		return ResponseEntity.ok().body(usuarioResponseDtos);
	}

	@Transactional
	public ResponseEntity<?> create(UsuarioRequestDto usuarioRequestDto) {
		String email = usuarioRequestDto.getEmail();
		
		JSONObject msgResposta = new JSONObject();
		
		msgResposta = verificaExistenciaCreate(email, msgResposta);
		
		if(msgResposta.size() == 0) {
			usuarioRequestDto.setNome(usuarioRequestDto.getNome().trim());
			usuarioRequestDto.setSobrenome(usuarioRequestDto.getSobrenome().trim());
			
			Usuario usuario = new Usuario();
			usuario = mapDtoParaEntity(usuarioRequestDto, usuario);
			
			Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
			UsuarioResponseDto usuarioResponseDto = mapEntityParaDto(usuarioSalvo);
			return ResponseEntity.created(null).body(usuarioResponseDto);
		}else {
			return retornaJsonMensagem(msgResposta, true, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> read(Long id) {
		Optional<Usuario> optional = this.usuarioRepository.findById(id);
		
		if(optional.isPresent()) {
			UsuarioResponseDto usuarioResponseDto = mapEntityParaDto(optional.get());
			return ResponseEntity.ok().body(usuarioResponseDto);
		} else {
			JSONObject msgResposta = new JSONObject();
			msgResposta.put("message", "Usuário #"+id+" não existe no banco de dados!");
			return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
		}
	}

	@Transactional
	public ResponseEntity<?> update(Long id, UsuarioRequestDto usuarioRequestDto) {
		Optional<Usuario> optional = this.usuarioRepository.findById(id);
		JSONObject msgResposta = new JSONObject(); 
		
		if(optional.isPresent()) {
			String email = usuarioRequestDto.getEmail();
			
			msgResposta = verificaExistenciaCreate(email, msgResposta);
			
			if(msgResposta.size() == 0) {
				usuarioRequestDto.setNome(usuarioRequestDto.getNome().trim());
				usuarioRequestDto.setSobrenome(usuarioRequestDto.getSobrenome().trim());
				
				Usuario usuario = new Usuario();
				usuario = mapDtoParaEntity(usuarioRequestDto, usuario); 
				
				usuario.setId(optional.get().getId());
				
				Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
				UsuarioResponseDto usuarioResponseDto = mapEntityParaDto(usuarioSalvo);
				return ResponseEntity.ok().body(usuarioResponseDto);
			}else {
				return retornaJsonMensagem(msgResposta, true, HttpStatus.BAD_REQUEST);
			}
		}
		
		msgResposta.put("message", "Usuário #"+id+" não existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> delete(Long id) {
		Optional<Usuario> optional = this.usuarioRepository.findById(id);
		JSONObject msgResposta = new JSONObject();
		
		if(optional.isPresent()) {
			usuarioRepository.deleteById(id);
			msgResposta.put("message", "Usuário #"+id+" excluído com sucesso!");
			return retornaJsonMensagem(msgResposta, false, HttpStatus.OK);
		}
		
		msgResposta.put("message", "Usuário #"+id+" não existe no banco de dados!");
		return retornaJsonMensagem(msgResposta, true, HttpStatus.NOT_FOUND);
	}
	
	public UsuarioResponseDto mapEntityParaDto(Usuario usuario) {
		UsuarioResponseDto usuarioResponseDto = UsuarioResponseDto.builder()
		.id(usuario.getId())
		.nome(usuario.getNome())
		.sobrenome(usuario.getSobrenome())
		.email(usuario.getEmail())
		.telefone(usuario.getTelefone())
		.build();
		
		return usuarioResponseDto;
	}
	
	public Usuario mapDtoParaEntity(UsuarioRequestDto usuarioRequestDto, Usuario usuario) {
		usuario = Usuario.builder()
		.nome(usuarioRequestDto.getNome())
		.sobrenome(usuarioRequestDto.getSobrenome())
		.email(usuarioRequestDto.getEmail())
		.senha(usuarioRequestDto.getSenha())
		.telefone(usuarioRequestDto.getTelefone())
		.build();
		
		return usuario;
	}
}