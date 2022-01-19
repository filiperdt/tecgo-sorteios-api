package br.com.tecgosorteios.tecgosorteios.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

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
public class UsuarioCompraRequestDto {
	@NotNull(message = "{usuario.nome.notnull}")
	@NotBlank(message = "{usuario.nome.notblank}")
	private String nome;
	@NotNull(message = "{usuario.sobrenome.notnull}")
	@NotBlank(message = "{usuario.sobrenome.notblank}")
	private String sobrenome;
	@NotNull(message = "{usuario.email.notnull}")
	@NotBlank(message = "{usuario.email.notblank}")
	@Email(message = "{usuario.email.email}")
	private String email;
	@NotNull(message = "{usuario.telefone.notnull}")
	@NotBlank(message = "{usuario.telefone.notblank}")
	private String telefone;
	
	@NotNull(message = "{numero.numero.notnull}")
	@NotBlank(message = "{numero.numero.notblank}")
	private String numero;
	
	@PositiveOrZero(message = "{numero.rifa.positiveorzero}")
	private Long rifa;
}
