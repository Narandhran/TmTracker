/**
 * @author Balaji
 *
 *         21-Feb-2017 - Balaji creation RoleTypeServiceImpl.java
 */
package com.neemShade.TmTracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neemShade.TmTracker.dao.RoleDao;
import com.neemShade.TmTracker.dao.RoleTypeDao;
import com.neemShade.TmTracker.dto.RoleDto;
import com.neemShade.TmTracker.dto.RoleTypeDto;
import com.neemShade.TmTracker.pojo.Role;
import com.neemShade.TmTracker.pojo.RoleType;

/**
 * @author Balaji
 *
 */
@Service
public class RoleTypeServiceImpl implements RoleTypeService {
	

	private RoleTypeDao roleTypeDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	public RoleTypeServiceImpl(RoleTypeDao roleTypeDao)
	{
		this.roleTypeDao = roleTypeDao;
	}



	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleTypeService#fetchRoleTypesWithRoles()
	 */
	@Override
	public List<RoleTypeDto> fetchRoleTypesWithRoles() {
		
		List<RoleTypeDto> roleTypeDtos = findAll();
	    
	    fillRoles(roleTypeDtos);
	    
	    return roleTypeDtos;
	  }

	  public void fillRoles(List<RoleTypeDto> roleTypeDtos)
	  {
		  for (RoleTypeDto roleTypeDto : roleTypeDtos) {
			  
			  RoleType roleType = roleTypeDto.getRoleType();

			  List<Role> roles = roleDao.findByRoleType(roleType);
			  
			  roleType.getRoles().addAll(roles);
			  
		}
	  }

	
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleTypeService#findAll()
	 */
	@Override
	public List<RoleTypeDto> findAll() {
		List<RoleType> roleTypes = (List<RoleType>) roleTypeDao.findAll();
		return convertToDTOs(roleTypes);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleTypeService#findById(java.lang.Long)
	 */
	@Override
	public RoleTypeDto findById(Long id) {
		RoleType roleType = findRoleTypeById(id);
		return convertToDTO(roleType);
	}

	/**
	 * @param id
	 * @return
	 */
	public RoleType findRoleTypeById(Long id) {
		return roleTypeDao.findOne(id);
	}

	public RoleTypeDto findByTypeName(String typeName)
	{
		RoleType roleType = roleTypeDao.findByTypeName(typeName);
		return convertToDTO(roleType);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleTypeService#convertToDTO(com.neemShade.TmTracker.pojo.RoleType)
	 */
	@Override
	public RoleTypeDto convertToDTO(RoleType roleType) {
		RoleTypeDto roleTypeDto = new RoleTypeDto();
		roleTypeDto.setRoleType(roleType);
		return roleTypeDto;
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleTypeService#convertToDTOs(java.util.List)
	 */
	@Override
	public List<RoleTypeDto> convertToDTOs(List<RoleType> roleTypes) {
//		return roleTypes.stream().map(this::convertToDTO)
//				.collect(Collectors.toList());
		
		List<RoleTypeDto> roleTypeDtos = new ArrayList<RoleTypeDto>();
		for(RoleType roleType : roleTypes)
			roleTypeDtos.add(convertToDTO(roleType));
		return roleTypeDtos;
	}
	
	public void copy(RoleTypeDto roleTypeDto, RoleType roleType)
	{
		if(roleTypeDto == null || roleTypeDto.getRoleType() == null || roleType == null)
			return;
		
		RoleType sourceRoleType = roleTypeDto.getRoleType();
		roleType.setTypeName(sourceRoleType.getTypeName());
	}


}
