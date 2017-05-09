/**
 * @author Balaji
 *
 *         21-Feb-2017 - Balaji creation RoleServiceImpl.java
 */
package com.neemShade.TmTracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neemShade.TmTracker.dao.RoleDao;
import com.neemShade.TmTracker.dto.ProjectUserDto;
import com.neemShade.TmTracker.dto.RoleDto;
import com.neemShade.TmTracker.pojo.ProjectUser;
import com.neemShade.TmTracker.pojo.Role;
import com.neemShade.TmTracker.pojo.RoleType;

/**
 * @author Balaji
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

	
	private RoleDao roleDao;
	
	
	@Autowired
	public RoleServiceImpl(RoleDao roleDao)
	{
		this.roleDao = roleDao;
	}
	
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleService#findAll()
	 */
	@Override
	public List<RoleDto> findAll() {
		List<Role> roles = (List<Role>) roleDao.findAll();
		return convertToDTOs(roles);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleService#findById(java.lang.Long)
	 */
	@Override
	public RoleDto findById(Long id) {
		Role role = findRoleById(id);
		return convertToDTO(role);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleService#findRoleById(java.lang.Long)
	 */
	@Override
	public Role findRoleById(Long id) {
		return roleDao.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleService#findByRoleName(java.lang.String)
	 */
	@Override
	public RoleDto findByRoleName(String roleName) {
		Role role = roleDao.findByRoleName(roleName);
		return convertToDTO(role);
	}

	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleService#findByRoleType(com.neemShade.TmTracker.pojo.RoleType)
	 */
	@Override
	public List<RoleDto> findByRoleType(RoleType roleType) {
		List<Role> roles =  roleDao.findByRoleType(roleType);
		return convertToDTOs(roles);
	}
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleService#findByRoleType(com.neemShade.TmTracker.pojo.RoleType)
	 */
	@Override
	public List<RoleDto> findByRoleTypeRoleName(RoleType roleType, String roleName) {
		List<Role> roles =  roleDao.findByRoleTypeRoleTypeIdAndRoleName(roleType.getRoleTypeId(), roleName);
		return convertToDTOs(roles);
	}
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleService#convertToDTO(com.neemShade.TmTracker.pojo.Role)
	 */
	@Override
	public RoleDto convertToDTO(Role role) {
		RoleDto roleDto = new RoleDto();
		roleDto.setRole(role);
		return roleDto;
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleService#convertToDTOs(java.util.List)
	 */
	@Override
	public List<RoleDto> convertToDTOs(List<Role> roles) {
//		return roles.stream().map(this::convertToDTO)
//				.collect(Collectors.toList());
		
		List<RoleDto> roleDtos = new ArrayList<RoleDto>();
		for(Role role : roles)
			roleDtos.add(convertToDTO(role));
		return roleDtos;
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleService#copy(com.neemShade.TmTracker.dto.RoleDto, com.neemShade.TmTracker.pojo.Role)
	 */
	@Override
	public void copy(RoleDto roleDto, Role role) {
		if(roleDto == null || roleDto.getRole() == null || role == null)
			return;
		
		Role sourceRole = roleDto.getRole();
		role.setPeckOrder(sourceRole.getPeckOrder());
		role.setRoleName(sourceRole.getRoleName());
		role.setRoleType(sourceRole.getRoleType());
		role.setRoleUsers(sourceRole.getRoleUsers());
	}


	

}
