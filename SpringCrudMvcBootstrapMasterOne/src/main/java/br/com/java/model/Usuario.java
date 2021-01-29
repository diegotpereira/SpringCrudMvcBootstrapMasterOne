package br.com.java.model;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "tab_usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	@NotEmpty
	@Size(max = 50)
	private String username;
	
	@Column
	@NotEmpty
	private String name;
	
	@Column
	@NotEmpty
	private String password;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column
	@NotNull
	private LocalDate dataNascimento;
	
	@Column
	@NotNull
	@Enumerated(EnumType.STRING)
	private Funcao funcao;
	
	public Usuario() {}
	
	public Usuario(String username, String password, Funcao funcao, String name, LocalDate dataNascimento ) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.funcao = funcao;
		this.dataNascimento = dataNascimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	
	public enum Funcao {
		FUNCAO_USUARIO, FUNCAO_ADMIN;

	}

}
