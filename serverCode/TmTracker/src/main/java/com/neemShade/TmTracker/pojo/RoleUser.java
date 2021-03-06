package com.neemShade.TmTracker.pojo;
// default package
// Generated 20 Feb, 2017 8:03:33 PM by Hibernate Tools 5.2.0.Beta1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * RoleUser generated by hbm2java
 */
@Entity
@Table(name = "tm_role_user", catalog = "tm")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "roleUserId")
public class RoleUser implements java.io.Serializable {

	private Long roleUserId;
	private Club club;
	private Role role;
	private User user;
	private Date joinDate;
	private Boolean isRegular;

	public RoleUser() {
	}

	public RoleUser(Club club, Role role, User user) {
		this.club = club;
		this.role = role;
		this.user = user;
	}

	public RoleUser(Club club, Role role, User user, Date joinDate,
			Boolean isRegular) {
		this.club = club;
		this.role = role;
		this.user = user;
		this.joinDate = joinDate;
		this.isRegular = isRegular;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "role_user_id", unique = true, nullable = false)
	public Long getRoleUserId() {
		return this.roleUserId;
	}

	public void setRoleUserId(Long roleUserId) {
		this.roleUserId = roleUserId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id", nullable = false)
//	@JsonManagedReference
	public Club getClub() {
		return this.club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
//	@JsonManagedReference
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
//	@JsonManagedReference
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "join_date", length = 10)
	public Date getJoinDate() {
		return this.joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	@Column(name = "is_regular")
	public Boolean getIsRegular() {
		return this.isRegular;
	}

	public void setIsRegular(Boolean isRegular) {
		this.isRegular = isRegular;
	}

}
