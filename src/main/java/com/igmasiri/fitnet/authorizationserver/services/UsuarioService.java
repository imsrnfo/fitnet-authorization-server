package com.igmasiri.fitnet.authorizationserver.services;

import com.igmasiri.fitnet.authorizationserver.models.Usuario;
import com.igmasiri.fitnet.authorizationserver.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userDetailsService")
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String input) {
		Usuario usuario = usuarioRepository.findByUsername(input);
		if (usuario == null) throw new BadCredentialsException("Bad credentials");
		new AccountStatusUserDetailsChecker().check(usuario);
		return usuario;
	}

	public Usuario findByUsername(String username){
		return usuarioRepository.findByUsername(username);
	}

	public Usuario save(Usuario usuario){
		return usuarioRepository.save(usuario);
	}

	public List<Usuario> findAll(){
		return usuarioRepository.findAll();
	}

	public void delete(Usuario usuario){
		usuarioRepository.delete(usuario);
	}

	public List<Usuario> findByUsernameContainingIgnoreCase(String username){
		return usuarioRepository.findByUsernameContainingIgnoreCaseOrderByUsernameAsc(username);
	}
}
