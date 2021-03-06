package com.igmasiri.fitnet.authorizationserver.repositories;

import com.igmasiri.fitnet.authorizationserver.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByUsername(String username);

	List<Usuario> findByUsernameContainingIgnoreCaseOrderByUsernameAsc(String username);

}


