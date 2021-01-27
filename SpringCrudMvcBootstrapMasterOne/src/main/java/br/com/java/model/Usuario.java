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
	private String nomeUsuario;
	
	@Column
	@NotEmpty
	private String nome;
	
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
	
	public Usuario(String nomeUsuario, String password, Funcao funcao, String nome, LocalDate dataNascimento ) {
		super();
		this.nomeUsuario = nomeUsuario;
		this.password = password;
		this.nome = nome;
		this.funcao = funcao;
		this.dataNascimento = dataNascimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
