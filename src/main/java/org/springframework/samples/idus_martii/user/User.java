package org.springframework.samples.idus_martii.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Audited
@Entity
@Table(name = "users")
public class User {
	@Id
	String username;
	 
	String name;
	
	String surname;
	
	String photo;
	
	String email;
	
	String password;
	
	
	boolean enabled;
	
	@NotAudited
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;
}
