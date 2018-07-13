package rs.gecko.assessment.domain.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import rs.gecko.assessment.domain.DomainObject;

@Entity
public class User implements DomainObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String username;
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	// ~ defaults to @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name =
	// "user_id"),
	// inverseJoinColumns = @joinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		if (!this.roles.contains(role)) {
			this.roles.add(role);
		}

		if (!role.getUsers().contains(this)) {
			role.getUsers().add(this);
		}
	}

	public void removeRole(Role role) {
		this.roles.remove(role);
		role.getUsers().remove(this);
	}



}
