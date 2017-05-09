/**
 * @author Balaji
 *
 *         20-Feb-2017 - Balaji creation ProjectUserService.java
 */
package com.neemShade.TmTracker.service;

import java.util.List;

import com.neemShade.TmTracker.dto.ProjectUserDto;
import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.pojo.ProjectType;
import com.neemShade.TmTracker.pojo.ProjectUser;
import com.neemShade.TmTracker.pojo.User;


/**
 * @author Balaji
 *
 */
public interface ProjectUserService {
	
	public ProjectUserDto create(ProjectUserDto projectUserDto);
	
	public ProjectUserDto delete(Long id);
	
	public List<ProjectUserDto> findAll();
	
	public ProjectUserDto findById(Long id);
	
	public ProjectUser findProjectUserById(Long id);
	
	public List<ProjectUserDto> findByUserId(Long userId);
	
	
	/**
	 * @param userId
	 * @return
	 */
	public ProjectUserDto fetchCompletedProject(Long userId);
	
	public ProjectUserDto update(ProjectUserDto projectUserDto);
	
	public ProjectUserDto convertToDTO(ProjectUser projectUser);
		
	public List<ProjectUserDto> convertToDTOs(List<ProjectUser> projectUsers);
	
	
	public void copy(ProjectUserDto projectUserDto, ProjectUser projectUser);

	
	/**
	 * store dummy projects for the given user
	 * @param userDto
	 * @param completedProjectUsers - first is communication project and second is leadership
	 * @return
	 */
	public Integer storeDummyProjectUsers(List<ProjectUser> completedProjectUsers);
}
