package br.com.tecgosorteios.tecgosorteios.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;

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
public class RifaRequestDto {
	@NotNull(message = "{rifa.titulo.notnull}")
	@NotBlank(message = "{rifa.titulo.notblank}")
	private String titulo;
	@NotNull(message = "{rifa.valor.notnull}")
	@PositiveOrZero(message = "{rifa.valor.positiveorzero}")
	private BigDecimal valor;
	@NotNull(message = "{rifa.dataSorteio.notnull}")
	@FutureOrPresent(message = "{rifa.dataSorteio.futureorpresent}")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataSorteio;
}
