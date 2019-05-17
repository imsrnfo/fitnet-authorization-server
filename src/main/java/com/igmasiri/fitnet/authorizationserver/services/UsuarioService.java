package com.igmasiri.fitnet.authorizationserver.services;

import com.igmasiri.fitnet.authorizationserver.models.Rol;
import com.igmasiri.fitnet.authorizationserver.models.Usuario;
import com.igmasiri.fitnet.authorizationserver.repositories.RolRepository;
import com.igmasiri.fitnet.authorizationserver.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "userDetailsService")
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolRepository rolRepository;

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
		Usuario usuarioAttach = usuarioRepository.findByUsername(usuario.getUsername());
		if (usuarioAttach!=null){
			usuarioAttach.setUsername(usuario.getUsername());
			usuarioAttach.setEmail(usuario.getEmail());
			usuarioAttach.setPassword(usuario.getPassword());
			usuario = usuarioAttach;
		}
		usuario.setRoles((usuario.getRoles()!=null) ? rolRepository.findByNameIn(usuario.getRoles().stream().map(Rol::getName).collect(Collectors.toList())) : new ArrayList<>());
		return usuarioRepository.save(usuario);
	}

	public List<Usuario> findAll(){
		return usuarioRepository.findAll();
	}

	public void delete(Usuario usuario){
		usuarioRepository.delete(usuario);
	}

	public List<Usuario> findByUsernameContainingIgnoreCaseOrderByUsernameAsc(String username){
		return usuarioRepository.findByUsernameContainingIgnoreCaseOrderByUsernameAsc(username);
	}
}
