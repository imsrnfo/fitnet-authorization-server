package com.igmasiri.fitnet.authorizationserver.repositories;

import com.igmasiri.fitnet.authorizationserver.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface RolRepository  extends JpaRepository<Rol, Long> {

    Rol findByName(String name);

    List<Rol> findByNameContainingIgnoreCaseOrderByNameAsc(String name);

    List<Rol> findByNameIn(Collection<String> names);

}