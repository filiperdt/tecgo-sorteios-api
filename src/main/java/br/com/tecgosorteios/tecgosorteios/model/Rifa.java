package br.com.tecgosorteios.tecgosorteios.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tblRifas")
public class Rifa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String titulo;
	@Column(nullable = false)
	private BigDecimal valor;
	@Column(nullable = false)
	private LocalDateTime dataCriacao;
	private LocalDateTime dataSorteio;
	
	@JsonBackReference // Anotação necessária para não cair em loop infinito
	@OneToMany(mappedBy = "rifa", cascade = CascadeType.ALL)
	private Set<Premio> premios;
	
	@JsonBackReference
	@OneToMany(mappedBy = "rifa", cascade = CascadeType.ALL)
	private Set<Numero> numeros;
}
