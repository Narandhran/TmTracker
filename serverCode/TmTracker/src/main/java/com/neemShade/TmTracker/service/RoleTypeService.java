/**
 * @author Balaji
 *
 *         21-Feb-2017 - Balaji creation RoleTypeService.java
 */
package com.neemShade.TmTracker.service;

import java.util.List;

import com.neemShade.TmTracker.dto.RoleTypeDto;
import com.neemShade.TmTracker.pojo.RoleType;


/**
 * @author Balaji
 *
 */
public interface RoleTypeService {
	
	
	
	public List<RoleTypeDto> findAll();
	
	public RoleTypeDto findById(Long id);
	
	public RoleType findRoleTypeById(Long id);

	public RoleTypeDto findByTypeName(String typeName);
	
	/**
	 * @return
	 */
	public List<RoleTypeDto> fetchRoleTypesWithRoles();
	
	public RoleTypeDto convertToDTO(RoleType roleType);
		
	public List<RoleTypeDto> convertToDTOs(List<RoleType> roleTypes);
	
	public void copy(RoleTypeDto roleTypeDto, RoleType roleType);


}
