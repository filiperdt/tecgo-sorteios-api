package br.com.tecgosorteios.tecgosorteios.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tecgosorteios.tecgosorteios.dto.request.UsuarioRequestDto;
import br.com.tecgosorteios.tecgosorteios.service.UsuarioService;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	
	public JSONObject analisaBindingResult(BindingResult bindingResult, JSONObject jsonMessages){
		List<FieldError> errors = bindingResult.getFieldErrors();
		errors.stream().forEach(erro -> jsonMessages.put(erro.getField(), erro.getDefaultMessage()));
		
		return jsonMessages;
	}
	
	public ResponseEntity<JSONObject> geraErroDoBindingResult(BindingResult bindingResult) {
		JSONObject jsonMessages = new JSONObject();
		jsonMessages = analisaBindingResult(bindingResult, jsonMessages);
		jsonMessages.put("erro", true);
		
		return ResponseEntity.badRequest().body(jsonMessages);
	}
	
	@GetMapping("")
	@ResponseBody
	public ResponseEntity<?> listAll() {
		ResponseEntity<?> responseEntity = usuarioService.listAll();
		return responseEntity;
	}
	
	@PostMapping("")
	@ResponseBody
	public ResponseEntity<?> create(@Valid @RequestBody UsuarioRequestDto requisicao, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ResponseEntity<JSONObject> responseEntity = geraErroDoBindingResult(bindingResult);
			return responseEntity;
		}else {
			ResponseEntity<?> responseEntity = usuarioService.create(requisicao);
			return responseEntity;
		}
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<?> read(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = usuarioService.read(id);
		return responseEntity;
	}
	
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDto requisicao, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			ResponseEntity<JSONObject> responseEntity = geraErroDoBindingResult(bindingResult);
			return responseEntity;
		}else {
			ResponseEntity<?> responseEntity = usuarioService.update(id, requisicao);
			return responseEntity;
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ResponseEntity<?> responseEntity = usuarioService.delete(id);
		return responseEntity;
	}
}