package com.OnlineAdvocateBookingSystem.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;



@Entity
@Table(name = "User") // This is table name
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // isse apneaap id generate hoga
	private int id;
	
	@NotBlank(message ="Name field is required!!")
	private String name;

	@Column(unique = true) // you can not generate same email in two other columns uniquely identified
	private String email;

	private String password;
	private String role;
	private boolean enabled;
	private String imageUrl;

	@Column(length = 500) // max length is 500
	private String about;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user") // user save krega contact apneaap save ho jayega
	private List<Advocate> advocate = new ArrayList<>();
	
	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String eamil) {
		this.email = eamil;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public List<Advocate> getAdvocates() {
		return advocate;
	}

	public void setContacts(List<Advocate> advocates) {
		this.advocate = advocates;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", about=" + about + ", contacts=" + advocate
				+ "]";
	}

}

