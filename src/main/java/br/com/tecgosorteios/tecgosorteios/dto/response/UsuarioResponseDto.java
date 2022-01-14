package br.com.tecgosorteios.tecgosorteios.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UsuarioResponseDto {
	private Long id;
	private String nome;
	private String sobrenome;
	private String email;
	private String senha;
	private String telefone;
}
