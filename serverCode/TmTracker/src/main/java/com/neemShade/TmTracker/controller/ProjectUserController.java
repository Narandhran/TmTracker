/**
 * @author Balaji
 *
 *         14-Feb-2017 - Balaji creation ProjectUserController.java
 */
package com.neemShade.TmTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.neemShade.TmTracker.dto.ProjectUserDto;
import com.neemShade.TmTracker.pojo.ProjectUser;
import com.neemShade.TmTracker.service.ProjectUserService;

/**
 * @author Balaji
 *
 */

@RestController
@RequestMapping(value="/projectUser")
public class ProjectUserController {
	
	  @Autowired
	  private ProjectUserService projectUserService;
	
	  /**
	   * GET /findByUserId  --> Return the list of projectUsers 
	   * 
	   */
	  @RequestMapping(value="/findByUserId/{userId}", method=RequestMethod.GET)
	  @ResponseBody
	  public List<ProjectUserDto> findByUserId(@PathVariable Long userId) {
	    
	    try {
//	    	System.out.println("controller " + userId);
//	      return projectUserDao.findByUserId(userId);
	    	List<ProjectUserDto> projectUserDtos = projectUserService.findByUserId(userId);
	    	
//	    	for (ProjectUserDto projectUserDto : projectUserDtos) {
//				System.out.println("test 55 " + projectUserDto.getProjectUser().getUser());
//			}
	    	
	    	return projectUserDtos;
	      
	    }
	    catch (Exception ex) {
	      
	    }
	    return null;
	  }
	  
	  

	  @RequestMapping(value="/create", method=RequestMethod.POST)
	  @ResponseBody
	  public ProjectUserDto create(@RequestBody ProjectUserDto projectUserDto)
	  {
//		  System.out.println("in the create " + projectUserDto);
		  return projectUserService.create(projectUserDto);
	  }
	  
	  @RequestMapping("/delete/{projectUserId}")
	  @ResponseBody
	  public ProjectUserDto delete(@PathVariable Long projectUserId)
	  {
		  return projectUserService.delete(projectUserId);
	  }
	  
	  
	  @RequestMapping(value="/update", method=RequestMethod.PUT)
	  @ResponseBody
	  public ProjectUserDto update(@RequestBody ProjectUserDto projectUserDto)
	  {
		  return projectUserService.update(projectUserDto);
	  }
	  
	  @RequestMapping(value="/storeDummyProjectUsers", method=RequestMethod.POST)
	  @ResponseBody
	  public Integer storeDummyProjectUsers(@RequestBody List<ProjectUser> completedProjectUsers)
	  {
		  return projectUserService.storeDummyProjectUsers(completedProjectUsers);
	  }
}
