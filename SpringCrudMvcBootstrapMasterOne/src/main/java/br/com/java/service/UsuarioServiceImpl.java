package br.com.java.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.java.dao.UsuarioDao;
import br.com.java.model.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
	
	@Autowired
	private UsuarioDao dao;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public Usuario salvar(Usuario usuario) {
		// TODO Auto-generated method stub
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		dao.save(usuario);
		return usuario;
	}

	@Override
	public Usuario findByUsername(String username) {
		// TODO Auto-generated method stub
		return dao.findByUsername(username);
	}

	@Override
	public List<Usuario> list() {
		// TODO Auto-generated method stub
		Iterable<Usuario> iterable = dao.findAll();
		if (Objects.nonNull(iterable)) {
			return (List<Usuario>) dao.findAll();
		}
		return null;
	}

	@Override
	public void remover(long id) {
		// TODO Auto-generated method stub
		dao.delete(id);
		
	}

	@Override
	public Usuario findById(long id) {
		// TODO Auto-generated method stub
		return dao.findOne(id);
	}

	@Override
	public Usuario getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return findByUsername(authentication.getName());
	}

}
