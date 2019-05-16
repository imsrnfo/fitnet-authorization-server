package com.igmasiri.fitnet.authorizationserver.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario extends GenericEntity implements UserDetails {

	private static final long serialVersionUID = 1L;
	@NotBlank
	@Pattern(regexp="\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")
	private String email;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@JsonIgnore
	private boolean enabled;
	@JsonIgnore
	@Column(name = "account_locked")
	private boolean accountNonLocked;
	@JsonIgnore
	@Column(name = "account_expired")
	private boolean accountNonExpired;
	@JsonIgnore
	@Column(name = "credentials_expired")
	private boolean credentialsNonExpired;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rol_usuario", joinColumns = {
			@JoinColumn(name = "usuario_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "rol_id", referencedColumnName = "id") })
	private List<Rol> roles;

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountNonExpired;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountNonLocked;
	}

	@JsonIgnore
	/* Get roles and permissions and add them as a Set of GrantedAuthority */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		roles.forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getName()));
			r.getPermisos().forEach(p -> {
				authorities.add(new SimpleGrantedAuthority(p.getName()));
			});
		});
		return authorities;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

}
