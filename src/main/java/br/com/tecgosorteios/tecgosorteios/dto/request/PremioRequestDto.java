package br.com.tecgosorteios.tecgosorteios.dto.request;

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
public class PremioRequestDto {
	@NotNull(message = "{premio.nome.notnull}")
	@NotBlank(message = "{premio.nome.notblank}")
	private String nome;
	@NotNull(message = "{premio.descricao.notnull}")
	@NotBlank(message = "{premio.descricao.notblank}")
	private String descricao;
	@NotNull(message = "{premio.foto.notnull}")
	@NotBlank(message = "{premio.foto.notblank}")
	private String foto;
	@NotNull(message = "{premio.video.notnull}")
	@NotBlank(message = "{premio.video.notblank}")
	private String video;
	
	@PositiveOrZero(message = "{premio.rifa.positiveorzero}")
	private RifaRequestDto rifa;
	
	@PositiveOrZero(message = "{premio.numero.positiveorzero}")
	private NumeroRequestDto numero;
}
