/**
 * @author Balaji
 *
 *         14-Feb-2017 - Balaji creation RoleDao.java
 */
package com.neemShade.TmTracker.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.neemShade.TmTracker.pojo.Role;
import com.neemShade.TmTracker.pojo.RoleType;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */

@Transactional
public interface RoleDao   extends CrudRepository<Role, Long>{

//	public Role findByRoleTypeIdAndRoleName(Integer roleTypeId, String roleName);
	
	public List<Role> findByRoleType(RoleType roleType);
	
	public List<Role> findByRoleTypeRoleTypeIdAndRoleName(Integer roleTypeId, String roleName);
	
	public Role findByRoleName(String roleName);
}
