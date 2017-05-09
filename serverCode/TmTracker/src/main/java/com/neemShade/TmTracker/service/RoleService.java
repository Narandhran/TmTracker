/**
 * @author Balaji
 *
 *         21-Feb-2017 - Balaji creation RoleService.java
 */
package com.neemShade.TmTracker.service;

import java.util.List;

import com.neemShade.TmTracker.dto.RoleDto;
import com.neemShade.TmTracker.pojo.Role;
import com.neemShade.TmTracker.pojo.RoleType;


/**
 * @author Balaji
 *
 */
public interface RoleService {
	
	public List<RoleDto> findAll();
	
	public RoleDto findById(Long id);
	
	public Role findRoleById(Long id);

	public RoleDto findByRoleName(String roleName);
	
	public List<RoleDto> findByRoleType(RoleType roleType);
	
	public List<RoleDto> findByRoleTypeRoleName(RoleType roleType, String roleName);
	
	public RoleDto convertToDTO(Role role);
		
	public List<RoleDto> convertToDTOs(List<Role> roles);
	
	public void copy(RoleDto roleDto, Role role);
}
