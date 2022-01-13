package br.com.tecgosorteios.tecgosorteios.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tblNumeros")
public class Numero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Long numero;
	@Column(nullable = false, length = 15)
	@Enumerated(EnumType.STRING)
	private EnumStatus status;
	private LocalDateTime data_compra;
	
	@OneToOne(mappedBy = "numero")
	private Premio premio;
	
	@JsonManagedReference // Anotação necessária para não cair em loop infinito
	@ManyToOne
	@JoinColumn(name = "rifa_id", nullable = false, foreignKey = @ForeignKey(name = "FK_Numeros_Rifas")) // A anotação @JoinColumn name é o nome que esta coluna terá no BD
	private Rifa rifa;
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "FK_Numeros_Usuarios"))
	private Usuario usuario;
}
