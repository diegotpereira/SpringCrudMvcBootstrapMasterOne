package br.com.java.service;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.java.dao.UsuarioDao;
import br.com.java.model.Usuario;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private UsuarioDao dao;

	@Override
	public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {

		Usuario usuario = dao.findBynomeUsuario(nomeUsuario);
		if (Objects.nonNull(usuario)) {
			GrantedAuthority authority = new SimpleGrantedAuthority(usuario.getFuncao().name());
			UserDetails userDetails = (UserDetails) new User(usuario.getNomeUsuario(), usuario.getPassword(),
					Arrays.asList(authority));
			return userDetails;
		}

		return null;
	}
}
