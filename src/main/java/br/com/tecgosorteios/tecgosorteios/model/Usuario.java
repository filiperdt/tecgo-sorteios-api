package br.com.tecgosorteios.tecgosorteios.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name = "tblUsuarios",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"email"},
			name="UK_Usuarios_Email"
		)
	}
)
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 50)
	private String nome;
	@Column(nullable = false, length = 50)
	private String sobrenome;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false, length = 500)
	private String senha;
	@Column(nullable = false, length = 18)
	private String telefone;
	
	@JsonBackReference
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private Set<Numero> numeros;
}
