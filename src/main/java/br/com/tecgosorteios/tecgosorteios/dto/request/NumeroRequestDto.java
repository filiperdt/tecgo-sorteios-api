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
public class NumeroRequestDto {
	@NotNull(message = "{numero.numero.notnull}")
	@NotBlank(message = "{numero.numero.notblank}")
	private String numero;
	
	@PositiveOrZero(message = "{numero.rifa.positiveorzero}")
	private Long rifa;
}
