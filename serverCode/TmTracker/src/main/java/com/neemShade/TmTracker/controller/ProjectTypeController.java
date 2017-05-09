/**
 * @author Balaji
 *
 *         11-Feb-2017 - Balaji creation ProjectTypeController.java
 */
package com.neemShade.TmTracker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.neemShade.TmTracker.dao.ProjectTypeDao;
import com.neemShade.TmTracker.dto.ProjectUserDto;
import com.neemShade.TmTracker.pojo.ProjectType;



/**
 * @author Balaji
 *
 */

@RestController
@RequestMapping(value="/projectType")
public class ProjectTypeController {

	  @Autowired
	  private ProjectTypeDao projectTypeDao;
	  
	  /**
	   * GET /list  --> list of projectType in descending order
	   */
	  @RequestMapping("/list")
	  @ResponseBody
	  public List<ProjectType> list() {
	    List<ProjectType> projectTypes = new ArrayList<ProjectType>();
	    try {
	      projectTypes = projectTypeDao.findAllByOrderByPeckOrderDesc();
	    }
	    catch (Exception ex) {
	    	return projectTypes;
	    }
	    return projectTypes;
	  }
	  
	  
}
