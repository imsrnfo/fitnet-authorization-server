package com.igmasiri.fitnet.authorizationserver.services;

import com.igmasiri.fitnet.authorizationserver.models.Rol;
import com.igmasiri.fitnet.authorizationserver.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;


    public Rol findByName(String username){
        return rolRepository.findByName(username);
    }

    public Rol save(Rol rol){
        return rolRepository.save(rol);
    }

    public List<Rol> findAll(){
        return rolRepository.findAll();
    }

    public void delete(Rol rol){
        rolRepository.delete(rol);
    }

    public List<Rol> findByNameContainingIgnoreCase(String username){
        return rolRepository.findByNameContainingIgnoreCaseOrderByNameAsc(username);
    }

}
