package br.com.tecgosorteios.tecgosorteios.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tblSorteios")
public class Sorteio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long concurso;
	@Column(nullable = false)
	private Long proxConcurso;
	@Column(nullable = false)
	private LocalDateTime dataConcurso;
	@Column(nullable = false)
	private LocalDateTime dataProxConcurso;
}
