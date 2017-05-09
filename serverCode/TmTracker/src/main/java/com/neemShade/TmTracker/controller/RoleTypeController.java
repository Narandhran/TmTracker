/**
 * @author Balaji
 *
 *         13-Feb-2017 - Balaji creation RoleTypeController.java
 */
package com.neemShade.TmTracker.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.neemShade.TmTracker.dao.RoleDao;
import com.neemShade.TmTracker.dao.RoleTypeDao;
import com.neemShade.TmTracker.dto.RoleTypeDto;
import com.neemShade.TmTracker.pojo.Role;
import com.neemShade.TmTracker.pojo.RoleType;
import com.neemShade.TmTracker.service.RoleTypeService;



/**
 * @author Balaji
 *
 */


@RestController
@RequestMapping(value="/roleType")
public class RoleTypeController {
	
	@Autowired
	  private RoleTypeService roleTypeService;
	  
	
	  /**
	   * GET /list  --> list of roleType 
	   */
	  @RequestMapping("/list")
	  @ResponseBody
	  public List<RoleTypeDto> fetchRoleTypesWithRoles() {
		  return roleTypeService.fetchRoleTypesWithRoles();
	  }
	    
	  
	  public static <E> List<E> toList(Iterable<E> iterable) {
		    if(iterable instanceof List) {
		      return (List<E>) iterable;
		    }
		    ArrayList<E> list = new ArrayList<E>();
		    if(iterable != null) {
		      for(E e: iterable) {
		        list.add(e);
		      }
		    }
		    return list;
		  }
		  
}
