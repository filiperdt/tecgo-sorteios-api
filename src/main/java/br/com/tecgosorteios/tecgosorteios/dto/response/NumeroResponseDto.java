package br.com.tecgosorteios.tecgosorteios.dto.response;

import java.time.LocalDateTime;

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
public class NumeroResponseDto {
	private Long id;
	private String numero;
	private EnumStatus status;
	private LocalDateTime dataCompra;
	
	private RifaResponseDto rifa;
	
	private UsuarioResponseDto usuario;
}
