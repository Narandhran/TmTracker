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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * RoleType generated by hbm2java
 */
@Entity
@Table(name = "tm_role_type", catalog = "tm", uniqueConstraints = @UniqueConstraint(columnNames = "type_name"))
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roleTypeId")
public class RoleType implements java.io.Serializable {

	private Integer roleTypeId;
	private String typeName;
	@JsonIgnore
	private Set<Role> roles = new HashSet<Role>(0);

	public RoleType() {
	}

	public RoleType(String typeName, Set<Role> roles) {
		this.typeName = typeName;
		this.roles = roles;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "role_type_id", unique = true, nullable = false)
	public Integer getRoleTypeId() {
		return this.roleTypeId;
	}

	public void setRoleTypeId(Integer roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	@Column(name = "type_name", unique = true, length = 50)
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "roleType")
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
