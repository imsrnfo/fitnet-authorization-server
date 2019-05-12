package com.igmasiri.fitnet.authorizationserver.service;

import com.igmasiri.fitnet.authorizationserver.entity.Usuario;
import com.igmasiri.fitnet.authorizationserver.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String input) {
		Usuario usuario = usuarioRepository.findByUsername(input);
		if (usuario == null) throw new BadCredentialsException("Bad credentials");
		new AccountStatusUserDetailsChecker().check(usuario);
		return usuario;
	}
}
