
CREATE TABLE PERMISO (id integer not null auto_increment, name varchar(255), primary key (id)) ;
CREATE TABLE PERMISO_ROL (rol_id integer not null, permiso_id integer not null) ;
CREATE TABLE ROL (id integer not null auto_increment, name varchar(255), primary key (id)) ;
CREATE TABLE ROL_USUARIO (usuario_id integer not null, rol_id integer not null) ;
CREATE TABLE USUARIO (id integer not null auto_increment, account_expired bit, account_locked bit, credentials_expired bit, email varchar(255), enabled bit not null, imagen varchar(255), password varchar(255), username varchar(255), primary key (id)) ;

CREATE TABLE PARAMETRO (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255) NOT NULL,
  valor VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX nombre_UNIQUE (nombre ASC));

CREATE TABLE OAUTH_CLIENT_DETAILS (
    CLIENT_ID VARCHAR(255) NOT NULL PRIMARY KEY,
    CLIENT_SECRET VARCHAR(255) NOT NULL,
    RESOURCE_IDS VARCHAR(255) DEFAULT NULL,
    SCOPE VARCHAR(255) DEFAULT NULL,
    AUTHORIZED_GRANT_TYPES VARCHAR(255) DEFAULT NULL,
    WEB_SERVER_REDIRECT_URI VARCHAR(255) DEFAULT NULL,
    AUTHORITIES VARCHAR(255) DEFAULT NULL,
    ACCESS_TOKEN_VALIDITY INT(11) DEFAULT NULL,
    REFRESH_TOKEN_VALIDITY INT(11) DEFAULT NULL,
    ADDITIONAL_INFORMATION VARCHAR(4096) DEFAULT NULL,
    AUTOAPPROVE VARCHAR(255) DEFAULT NULL
);

ALTER TABLE USUARIO ADD CONSTRAINT UK_4tdehxj7dh8ghfc68kbwbsbll UNIQUE (email);
ALTER TABLE USUARIO ADD CONSTRAINT UK_471i15k6vbj1lfsfb19getcdi UNIQUE (username);
ALTER TABLE PERMISO_ROL ADD CONSTRAINT FKinl6wsw56hee7cu2wbgggi2n3 FOREIGN KEY (permiso_id) REFERENCES PERMISO (id);
ALTER TABLE PERMISO_ROL ADD CONSTRAINT FK4pgjyrhmnbho1f0t450e5shps FOREIGN KEY (rol_id) REFERENCES ROL (id);
ALTER TABLE ROL_USUARIO ADD CONSTRAINT FKeeqjpnqatgvjicfhvmd3uvpad FOREIGN KEY (rol_id) REFERENCES ROL (id);
ALTER TABLE ROL_USUARIO ADD CONSTRAINT FKflapgi3ekh596socl96tu5h9k FOREIGN KEY (usuario_id) REFERENCES USUARIO (id);

#Configuracion servidor de recursos
INSERT INTO OAUTH_CLIENT_DETAILS (
  CLIENT_ID,CLIENT_SECRET,
  RESOURCE_IDS,
  SCOPE,
  AUTHORIZED_GRANT_TYPES,
  WEB_SERVER_REDIRECT_URI,AUTHORITIES,
  ACCESS_TOKEN_VALIDITY,REFRESH_TOKEN_VALIDITY,
  ADDITIONAL_INFORMATION,AUTOAPPROVE)
VALUES(
  'USER_CLIENT_APP','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi',
                    'USER_CLIENT_RESOURCE,USER_ADMIN_RESOURCE',
                    'role_admin,role_user',
                    'authorization_code,password,refresh_token,implicit',
                    NULL,NULL,
                    900,3600,
                    '{}',NULL);

INSERT INTO PERMISO (NAME) VALUES
  ('can_create_user'),
  ('can_update_user'),
  ('can_read_user'),
  ('can_delete_user');

INSERT INTO ROL (NAME) VALUES
  ('role_admin'),('role_user');

INSERT INTO PERMISO_ROL (PERMISO_ID, ROL_ID) VALUES
  (1,1),
  (2,1),
  (3,1),
  (4,1),
  (3,2);

INSERT INTO USUARIO (
  USERNAME,PASSWORD,
  EMAIL,ENABLED,ACCOUNT_EXPIRED,CREDENTIALS_EXPIRED,ACCOUNT_LOCKED) VALUES (
  'admin','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi',
  'william@gmail.com',1,0,0,0),
  ('user','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi',
   'john@gmail.com',1,0,0,0);

INSERT INTO ROL_USUARIO (ROL_ID, USUARIO_ID) VALUES (1, 1), (2, 2) ;

INSERT INTO PARAMETRO (nombre, valor) VALUES ('VERSION', '1.0.0');