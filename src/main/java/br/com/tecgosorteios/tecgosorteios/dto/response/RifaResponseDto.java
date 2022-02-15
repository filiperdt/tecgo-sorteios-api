package br.com.tecgosorteios.tecgosorteios.dto.response;

import java.time.LocalDateTime;

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
public class RifaResponseDto {
	private Long id;
	private String titulo;
	private String valor;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataSorteio;
}
