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
public class PremioResponseDto {
	private Long id;
	private String nome;
	private String descricao;
	private String foto;
	private String video;
	
	private RifaResponseDto rifa;
	
	private NumeroResponseDto numero;
}
