package br.com.tecgosorteios.tecgosorteios.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class PremioGanhadorRequestDto {
	List<String> listaNumeroRequisicao;
	@NotNull
//	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataSorteioAtual;
	@NotNull
//	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataProximoSorteio;
}
