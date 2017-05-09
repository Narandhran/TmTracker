package com.neemShade.TmTracker.pojo;
// default package
// Generated 20 Feb, 2017 8:03:33 PM by Hibernate Tools 5.2.0.Beta1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "tm_role", catalog = "tm")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "roleId")
public class Role implements java.io.Serializable {

	private Integer roleId;
	private RoleType roleType;
	private String roleName;
	private Integer peckOrder;
	@JsonIgnore
	private Set<RoleUser> roleUsers = new HashSet<RoleUser>(0);

	public Role() {
	}

	public Role(RoleType roleType, String roleName) {
		this.roleType = roleType;
		this.roleName = roleName;
	}

	public Role(RoleType roleType, String roleName, Integer peckOrder,
			Set<RoleUser> roleUsers) {
		this.roleType = roleType;
		this.roleName = roleName;
		this.peckOrder = peckOrder;
		this.roleUsers = roleUsers;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "role_id", unique = true, nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_type_id", nullable = false)
	public RoleType getRoleType() {
		return this.roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	@Column(name = "role_name", nullable = false, length = 100)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "peck_order")
	public Integer getPeckOrder() {
		return this.peckOrder;
	}

	public void setPeckOrder(Integer peckOrder) {
		this.peckOrder = peckOrder;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
//	@JsonBackReference
	public Set<RoleUser> getRoleUsers() {
		return this.roleUsers;
	}

	public void setRoleUsers(Set<RoleUser> roleUsers) {
		this.roleUsers = roleUsers;
	}

}
