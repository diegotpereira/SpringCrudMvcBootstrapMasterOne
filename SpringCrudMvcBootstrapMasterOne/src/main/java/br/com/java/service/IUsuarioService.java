package br.com.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.java.model.Usuario;

public interface IUsuarioService {
	
	Usuario salvar(Usuario usuario);
	
	Usuario findBynomeUsuario (String nomeUsuario);
	
	List<Usuario> list();
	
	void remover (long id);
	
	Usuario findById(long id);
	
	Usuario getAuthentication();

}
