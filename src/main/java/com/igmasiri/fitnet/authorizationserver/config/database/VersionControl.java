package com.igmasiri.fitnet.authorizationserver.config.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
@Transactional
public class VersionControl {

    @Value("${fitnet.version}")
    private String appVersion;

    @Autowired
    private EntityManager em;

    @PostConstruct
    public void init() {
        String version = (String) em.createNativeQuery("select p.valor from PARAMETRO p where p.nombre = 'VERSION'").getSingleResult();

        if (!version.equals(appVersion)) {
            throw new RuntimeException(String.format("Versión en la base de datos %s no coincide con la versión de la aplicación %s", version, appVersion));
        }
    }

}
