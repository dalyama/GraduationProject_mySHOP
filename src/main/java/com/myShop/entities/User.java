package com.myShop.entities;


import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Table(name = "users")
/*
 * @FieldMatch's function has error that we solved it 
 *by compare confirm password with password manual in user controller class -> register function 
 *@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
 */
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	@NotEmpty(message = "Name Connot be Empty.")
	private String name;

	@Column(name = "email",unique = true)
	@NotEmpty(message = "Email Connot be Empty.")
	@Email(message = "Email should be valid.")
	private String email;

	//@ValidPassword
	@Column(name = "password")
	@NotEmpty(message = "Password should be at least 8 characters.")
	@Length(min = 8 , message = "Password should be at least 8 characters.")
	private String password;
	
	//@NotEmpty(message = "Confirm Password should be Same Password")
	@Transient
	private String confirmPassword;

	private String birthdate;

	private String gendor;
	
	@Column(name = "phone")
	private String phone;
	
	private Date registerationDate = new Date();

	//@JsonIgnore
	@OneToOne(mappedBy = "user")
	private address address;
	
	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade  = CascadeType.ALL)
	private userImage userImage;
    
	//this field has confirm account code
	@Column(name = "status")
	private String status = getRandomString();
	/*
	@Column(name = "role")
	private String role = "USER";
	*/
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<order> order;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
    private Set<cart> cart;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
    		name = "user_roles",
    		joinColumns = {@JoinColumn (name = "user_id")},
    		inverseJoinColumns = {@JoinColumn (name = "roles_id")}
    )
    private Set<Roles> roles;
		
	public String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String Key = salt.toString();
        return Key;

    }

	public User(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles();
		this.status = user.getStatus();
		this.birthdate = user.getBirthdate();
		this.gendor = user.getGendor();
		this.phone = user.getPhone();
		this.userImage = user.getUserImage();
		this.name = user.getName();
	}

}