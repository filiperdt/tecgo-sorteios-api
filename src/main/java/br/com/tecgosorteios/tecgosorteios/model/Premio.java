package br.com.tecgosorteios.tecgosorteios.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tblPremios")
public class Premio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String descricao;
	@Column(nullable = false)
	private String foto;
	@Column(nullable = false)
	private String video;
	
	@JsonManagedReference // Anotação necessária para não cair em loop infinito
	@ManyToOne
	@JoinColumn(name = "rifa_id", nullable = false, foreignKey = @ForeignKey(name = "FK_Premios_Rifas")) // A anotação @JoinColumn name é o nome que esta coluna terá no BD
	private Rifa rifa;
	
	@OneToOne
	@JoinColumn(name = "numero_id", foreignKey = @ForeignKey(name = "FK_Premios_Numeros"))
	private Numero numero;
}
