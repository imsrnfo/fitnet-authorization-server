package com.igmasiri.fitnet.authorizationserver.config.database;

import java.util.EnumSet;

import com.igmasiri.fitnet.authorizationserver.models.Permiso;
import com.igmasiri.fitnet.authorizationserver.models.Rol;
import com.igmasiri.fitnet.authorizationserver.models.Usuario;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;


public class GeneradorEsquemaDB {

	public static void main(String[] args) {
		
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
	            .applySetting("hibernate.hbm2ddl.auto", "create")
	            .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect")
	            .build();

		MetadataSources sources = new MetadataSources(standardRegistry);
	    sources.addAnnotatedClass(Usuario.class);
	    sources.addAnnotatedClass(Rol.class);
	    sources.addAnnotatedClass(Permiso.class);

	    MetadataImplementor metadata = (MetadataImplementor) sources
	            .getMetadataBuilder()
	            .build();

	    SchemaExport export = new SchemaExport();
	    export.setDelimiter(";");
	    export.createOnly(EnumSet.of( TargetType.STDOUT ), metadata);

	}

}
