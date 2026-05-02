package com.cruxtrack.backend.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// CRUXTRACK: JPA ENTITY = ONE TABLE ROW REPRESENTS ONE LOGIN ACCOUNT
// CRUXTRACK: CLASS NAME IS "APPUSER" TO AVOID CLASHING WITH SPRING'S OWN "USER" TYPES
@Entity
@Table(name = "users")
public class AppUser {

	// CRUXTRACK: AUTO-GENERATED PRIMARY KEY (DATABASE ROW ID)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// CRUXTRACK: LOGIN NAME (UNIQUE SO TWO PEOPLE CANNOT SHARE THE SAME USERNAME)
	@Column(nullable = false, unique = true)
	private String username;

	// CRUXTRACK: NEVER STORE PLAIN TEXT — THIS HOLDS THE BCRYPT HASH FROM SPRING SECURITY
	@Column(nullable = false)
	private String password;

	// CRUXTRACK: STORED AS TEXT IN SQLITE ("USER" OR "ADMIN"), NOT AS A NUMBER
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	// CRUXTRACK: ADDED FOR USER SETTINGS PROFILE UPDATES
	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String preferredName;

	@Column(nullable = false)
	private boolean active = true;

	// CRUXTRACK: JPA REQUIRES A NO-ARG CONSTRUCTOR (IT CREATES OBJECTS FOR YOU)
	protected AppUser() {
	}

	// CRUXTRACK: USED WHEN CREATING NEW USERS IN CODE (E.G. SEED DATA)
	public AppUser(String username, String password, Role role, String firstName, String lastName) {
		this.username = username;
		this.password = password;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public Role getRole() { return role; }
	public void setRole(Role role) { this.role = role; }

	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getPreferredName() { return preferredName; }
	public void setPreferredName(String preferredName) { this.preferredName = preferredName; }

	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
}
