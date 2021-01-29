package br.com.java.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.java.model.Usuario;

@Repository
public interface UsuarioDao extends CrudRepository<Usuario, Long> {
	public Usuario findByUsername(String username);

}
