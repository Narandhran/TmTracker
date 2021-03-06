package com.neemShade.TmTracker.pojo;
// default package
// Generated 20 Feb, 2017 8:03:33 PM by Hibernate Tools 5.2.0.Beta1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "tm_user", catalog = "tm", uniqueConstraints = {
		@UniqueConstraint(columnNames = "phone"),
		@UniqueConstraint(columnNames = "email")})
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "userId")
public class User implements java.io.Serializable {

	private Long userId;
	private String userName;
	private String phone;
	private String email;
	private String password;
	private Date dateOfBirth;
	private String userNotes;
	@JsonIgnore
	private Set<AttendanceUser> attendanceUsers = new HashSet<AttendanceUser>(0);
	@JsonIgnore
	private Set<ProjectUser> projectUsers = new HashSet<ProjectUser>(0);
	@JsonIgnore
	private Set<RoleUser> roleUsers = new HashSet<RoleUser>(0);

	public User() {
	}

	public User(String userName) {
		this.userName = userName;
	}

	public User(String userName, String phone, String email, String password,
			Date dateOfBirth, String userNotes,
			Set<AttendanceUser> attendanceUsers, Set<ProjectUser> projectUsers,
			Set<RoleUser> roleUsers) {
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.userNotes = userNotes;
		this.attendanceUsers = attendanceUsers;
		this.projectUsers = projectUsers;
		this.roleUsers = roleUsers;
	}
	

	@Override
	public String toString() {
		
		return userName;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "user_name", nullable = false, length = 200)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "phone", unique = true, length = 30)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", unique = true, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth", length = 10)
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "user_notes", length = 4000)
	public String getUserNotes() {
		return this.userNotes;
	}

	public void setUserNotes(String userNotes) {
		this.userNotes = userNotes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//	@JsonBackReference
	public Set<AttendanceUser> getAttendanceUsers() {
		return this.attendanceUsers;
	}

	public void setAttendanceUsers(Set<AttendanceUser> attendanceUsers) {
		this.attendanceUsers = attendanceUsers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//	@JsonBackReference
	public Set<ProjectUser> getProjectUsers() {
		return this.projectUsers;
	}

	public void setProjectUsers(Set<ProjectUser> projectUsers) {
		this.projectUsers = projectUsers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
//	@JsonBackReference
	public Set<RoleUser> getRoleUsers() {
		return this.roleUsers;
	}

	public void setRoleUsers(Set<RoleUser> roleUsers) {
		this.roleUsers = roleUsers;
	}


}
