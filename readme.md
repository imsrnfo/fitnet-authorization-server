#Fitnet Authorization Server

##Wildfly

###Standalone.xml

####Datasource
<datasource jta="true" jndi-name="java:/fitnetAuthorizationServerDS" pool-name="fitnetAuthorizationServerDS" enabled="true" use-ccm="false">
    <connection-url>jdbc:mysql://127.0.0.1/tmp</connection-url>
    <driver-class>com.mysql.jdbc.Driver</driver-class>
    <connection-property name="rewriteBatchedStatements">
        true
    </connection-property>
    <driver>mysql-connector-java-5.1.46.jar_com.mysql.jdbc.Driver_5_1</driver>
    <security>
        <user-name>root</user-name>
        <password>puntos</password>
    </security>
    <validation>
        <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker"/>
        <background-validation>true</background-validation>
        <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter"/>
    </validation>
</datasource>

####Virtual Host
<host name="fitnet-authorization-host" alias="auth.fitnet.com.ui,authorization.fitnet.com.ui" default-web-module="authorization-server-1.0.0.war"/>

####Acceso desde afuera
<inet-address value="${jboss.bind.address:0.0.0.0}"/>

####Puerto
<socket-binding name="http" port="${jboss.http.port:80}"/>

####SSL
<socket-binding name="https" port="${jboss.https.port:443}"/>
<keystore path="nacho.keystore" relative-to="jboss.server.config.dir" keystore-password="1234" alias="as" key-password="1234"/>
#####Certificado
...\wildfly-17.0.0.Final\standalone\configuration\nacho.keystore

##Windows

###Simular DNS
C:\Windows\System32\drivers\etc\hosts
127.0.0.1 auth.fitnet.com.ui
127.0.0.1 res.fitnet.com.ui
127.0.0.1:3000 fitnet.com.ui

###Generar Certificado
####Usar kse (KeyStoreExplorer)
.Create new KeyStore -> JKS -> OK
.(click-der) -> Generate Key Pair -> RSA (2048) -> OK
.Edit Name -> (llenar campos) -> OK -> OK -> Elegir Alias -> OK -> Password para el alias
.Save (con extension .keystore) -> Password para el keystore