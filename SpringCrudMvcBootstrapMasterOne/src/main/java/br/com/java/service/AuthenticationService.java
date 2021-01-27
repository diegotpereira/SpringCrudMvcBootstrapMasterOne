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

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		Usuario user = dao.findBynomeUsuario(username);
//		if (Objects.nonNull(user)) {
//			GrantedAuthority authority = new SimpleGrantedAuthority(user.getFuncao().name());
////			UserDetails userDetails = (UserDetails) new Usuario(user.getNomeUsuario(), 
////					user.getPassword(), Arrays.asList(authority));
////			return userDetails;
////		}
////
////		return null;
//			return new User(username, username, true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_USUARIO"));
//	}
//		return null;
//	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario user = dao.findBynomeUsuario(username);
		if (Objects.nonNull(user)) {
			GrantedAuthority authority = new SimpleGrantedAuthority(user.getFuncao().name());
			UserDetails userDetails = (UserDetails) new User(user.getNomeUsuario(), user.getPassword(),
					Arrays.asList(authority));
			return userDetails;
		}

		return null;
	}
}
