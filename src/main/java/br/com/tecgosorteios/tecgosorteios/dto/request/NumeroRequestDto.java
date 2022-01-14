package br.com.tecgosorteios.tecgosorteios.dto.request;

import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.com.tecgosorteios.tecgosorteios.model.EnumStatus;
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
	@NotNull(message = "{numero.status.notnull}")
	@Enumerated(EnumType.STRING)
	private EnumStatus status;
	private LocalDateTime dataCompra;
	
	@PositiveOrZero(message = "{numero.premio.positiveorzero}")
	private PremioRequestDto premio;
	
	@PositiveOrZero(message = "{numero.rifa.positiveorzero}")
	private RifaRequestDto rifa;
	
	@PositiveOrZero(message = "{numero.usuario.positiveorzero}")
	private UsuarioRequestDto usuario;
}
