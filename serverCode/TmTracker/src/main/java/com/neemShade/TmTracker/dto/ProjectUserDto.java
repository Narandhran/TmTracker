/**
 * @author Balaji
 *
 *         20-Feb-2017 - Balaji creation ProjectUserDto.java
 */
package com.neemShade.TmTracker.dto;

import java.util.Comparator;

import com.neemShade.TmTracker.pojo.ProjectUser;

/**
 * @author Balaji
 *
 */
public class ProjectUserDto {
	
	private ProjectUser projectUser;

	
	/**
	 * form project level
	 * @return
	 */
	public String findProjectLevel()
	{
		if(projectUser == null || projectUser.getProjectType() == null || projectUser.getProjectType().getTypeName() == null)
			return "";
		
		String projectLevel = projectUser.getProjectType().getTypeName() + " " + projectUser.getProjectLevel();
		return projectLevel;
	}
	
	
	public static Comparator<ProjectUserDto> fetchComparator()
	{
		Comparator<ProjectUserDto> projectComparator
                = new Comparator<ProjectUserDto>() {

					@Override
					public int compare(ProjectUserDto p1, ProjectUserDto p2) {
						
						if(p1 == null || p1.getProjectUser() == null)
							return -1;
						if(p2 == null || p1.getProjectUser() == null)
							return 1;
						
						if(p1.getProjectUser().getProjectType() != null && p2.getProjectUser().getProjectType() != null)
						{
							if(p1.getProjectUser().getProjectType().getPeckOrder() != p2.getProjectUser().getProjectType().getPeckOrder())
								return p2.getProjectUser().getProjectType().getPeckOrder().compareTo(p1.getProjectUser().getProjectType().getPeckOrder());
						}
						
						return p2.getProjectUser().getProjectLevel().compareTo(p1.getProjectUser().getProjectLevel());
					}
			
		};
		
		return projectComparator;
	}
	
	public ProjectUser getProjectUser() {
		return projectUser;
	}

	public void setProjectUser(ProjectUser projectUser) {
		this.projectUser = projectUser;
	}

}
