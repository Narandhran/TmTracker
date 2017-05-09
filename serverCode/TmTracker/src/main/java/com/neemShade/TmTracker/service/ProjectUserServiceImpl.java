/**
 * @author Balaji
 *
 *         20-Feb-2017 - Balaji creation ProjectUserServiceImpl.java
 */
package com.neemShade.TmTracker.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neemShade.TmTracker.dao.ProjectTypeDao;
import com.neemShade.TmTracker.dao.ProjectUserDao;
import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.dto.ProjectUserDto;
import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.pojo.Club;
import com.neemShade.TmTracker.pojo.ProjectType;
import com.neemShade.TmTracker.pojo.ProjectUser;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */
@Service
public class ProjectUserServiceImpl implements ProjectUserService {
	
	@Autowired
	private ProjectUserDao projectUserDao;
	
	
	@Autowired
	private UserService userService;
	 
	@Autowired
	private ProjectTypeDao projectTypeDao;
	
	@Autowired
	public ProjectUserServiceImpl(ProjectUserDao projectUserDao)
	{
		this.projectUserDao = projectUserDao;
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ProjectUserService#create(com.neemShade.TmTracker.dto.ProjectUserDto)
	 */
	@Override
	public ProjectUserDto create(ProjectUserDto projectUserDto) {
		ProjectUser projectUser = projectUserDto.getProjectUser();
//		System.out.println("test 33 " + projectUser.getProjectTheme() + " " + projectUser.getProjectTitle() + " " + projectUser.getProjectLevel() + " " + projectUser.getIsRepeat() + " " + projectUser.getProjectGivenDate() + " " + projectUser.getProjectType() + " " + projectUser.getUser()) ;
		ProjectUser persisted = projectUserDao.save(projectUser);
		return convertToDTO(persisted);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ProjectUserService#delete(java.lang.Long)
	 */
	@Override
	public ProjectUserDto delete(Long id) {
		ProjectUser deleted = findProjectUserById(id);
		projectUserDao.delete(deleted);
		return convertToDTO(deleted);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ProjectUserService#findAll()
	 */
	@Override
	public List<ProjectUserDto> findAll() {
		List<ProjectUser> projectUsers = (List<ProjectUser>) projectUserDao.findAll();
		return convertToDTOs(projectUsers);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ProjectUserService#findById(java.lang.Long)
	 */
	@Override
	public ProjectUserDto findById(Long id) {
		ProjectUser projectUser = findProjectUserById(id);
		return convertToDTO(projectUser);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ProjectUserService#findProjectUserById(java.lang.Long)
	 */
	@Override
	public ProjectUser findProjectUserById(Long id) {
		return projectUserDao.findOne(id);
	}

	public List<ProjectUserDto> findByUserId(Long userId)
	{
		UserDto userDto = userService.findById(userId);
//		System.out.println(userDto.getUser().getUserName());
		List<ProjectUser> projectUsers = (List<ProjectUser>) projectUserDao.findByUser(userDto.getUser());
//		for (ProjectUser projectUser : projectUsers) {
//			System.out.println(projectUser.getProjectTitle() + " " + projectUser.getProjectGivenDate());
//		}
		return convertToDTOs(projectUsers);
	}
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ProjectUserService#fetchCompletedProject(java.lang.Long)
	 */
	@Override
	public ProjectUserDto fetchCompletedProject(Long userId) {
//		ProjectUser projectUser = projectUserDao.fetchCompletedProject(userId);
		ProjectUser projectUser = projectUserDao.findFirstByUserUserIdOrderByProjectTypePeckOrderDescProjectLevelDesc(userId);
		return convertToDTO(projectUser);
	}
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ProjectUserService#update(com.neemShade.TmTracker.dto.ProjectUserDto)
	 */
	@Override
	public ProjectUserDto update(ProjectUserDto projectUserDto) {
		ProjectUser projectUser = findProjectUserById(projectUserDto.getProjectUser().getProjectUserId());
		copy(projectUserDto, projectUser);
		projectUser = projectUserDao.save(projectUser);
		return convertToDTO(projectUser);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ProjectUserService#convertToDTO(com.neemShade.TmTracker.pojo.ProjectUser)
	 */
	@Override
	public ProjectUserDto convertToDTO(ProjectUser projectUser) {
		ProjectUserDto projectUserDto = new ProjectUserDto();
		projectUserDto.setProjectUser(projectUser);
		return projectUserDto;
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ProjectUserService#convertToDTOs(java.util.List)
	 */
	@Override
	public List<ProjectUserDto> convertToDTOs(List<ProjectUser> projectUsers) {
//		return projectUsers.stream().map(this::convertToDTO)
//				.collect(Collectors.toList());
		
		List<ProjectUserDto> projectUserDtos = new ArrayList<ProjectUserDto>();
		for(ProjectUser projectUser : projectUsers)
			projectUserDtos.add(convertToDTO(projectUser));
		return projectUserDtos;
	}


	public void copy(ProjectUserDto projectUserDto, ProjectUser projectUser)
	{
		if(projectUserDto == null || projectUserDto.getProjectUser() == null || projectUser == null)
			return;
		
		ProjectUser sourceProjectUser = projectUserDto.getProjectUser();
		projectUser.setProjectUserId(sourceProjectUser.getProjectUserId());
		projectUser.setProjectType(sourceProjectUser.getProjectType());
		projectUser.setUser(sourceProjectUser.getUser());
		projectUser.setProjectLevel(sourceProjectUser.getProjectLevel());
		projectUser.setProjectGivenDate(sourceProjectUser.getProjectGivenDate());
		projectUser.setIsRepeat(sourceProjectUser.getIsRepeat());
		projectUser.setProjectTheme(sourceProjectUser.getProjectTheme());
		projectUser.setProjectTitle(sourceProjectUser.getProjectTitle());
		projectUser.setComments(sourceProjectUser.getComments());
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ProjectUserService#storeDummyProjectUsers(com.neemShade.TmTracker.dto.UserDto, java.util.List)
	 */
	@Override
	public Integer storeDummyProjectUsers(List<ProjectUser> completedProjectUsers) {
		
		
		
		List<List<ProjectType>> projectTypeLists = formProjectTypeLists();
		
		int countDummyProjects = 0;
		
		for (int i = 0; i < completedProjectUsers.size(); i++) {
			ProjectUser completedProjectUser = completedProjectUsers.get(i);
			User user = completedProjectUser.getUser();
			
			int currentProjectTypeIndex = calcProjectTypeIndex(projectTypeLists.get(i), completedProjectUser.getProjectType());
			if(currentProjectTypeIndex < 0)
				return -1;
			
			int currentProjectLevel = completedProjectUser.getProjectLevel();
			
			Date runningDate = new Date();
			
			do
			{
				runningDate = timeTravel(runningDate);
				
				storeDummyProjectUser(runningDate, currentProjectLevel, projectTypeLists.get(i).get(currentProjectTypeIndex), user);
				countDummyProjects++;
				
				if(currentProjectLevel > 1)
				{
					currentProjectLevel --;
					continue;
				}
				
				if(currentProjectTypeIndex < projectTypeLists.get(i).size() - 1)
				{
					currentProjectTypeIndex++;
					currentProjectLevel = 10;
					continue;
				}
				
				break;
				
			}
			while(true);
		}
		
		return countDummyProjects;
	}

	/**
	 * @param runningDate
	 * @param currentProjectLevel
	 * @param projectType
	 * @param user
	 */
	private void storeDummyProjectUser(Date runningDate, int currentProjectLevel, ProjectType projectType,
			User user) {
		ProjectUser projectUser = new ProjectUser();
		projectUser.setProjectGivenDate(runningDate);
		projectUser.setProjectLevel(currentProjectLevel);
		projectUser.setProjectType(projectType);
		projectUser.setUser(user);
		projectUser.setIsRepeat(false);
		
		projectUserDao.save(projectUser);
	}

	/**
	 * @param runningDate 
	 * @return
	 */
	private Date timeTravel(Date runningDate) {
		int backDays = (int) (Math.random() * 45);
		Calendar cal = Calendar.getInstance();
		cal.setTime(runningDate);
		cal.add(Calendar.DATE, - backDays);
		return cal.getTime();
	}

	/**
	 * find the index of given projectType in the given list
	 * @param projectTypeList
	 * @param projectType
	 * @return
	 */
	private int calcProjectTypeIndex(List<ProjectType> projectTypeList, ProjectType givenProjectType) {
		
		if(givenProjectType == null)
			return -1;
		
		for (int i = 0; i < projectTypeList.size(); i++) {
			if(projectTypeList.get(i).getProjectTypeId().intValue() == givenProjectType.getProjectTypeId().intValue())
				return i;
		}
		
		return -1;
	}

	/**
	 * get all projectType in peckOrder desc
	 * split the list into two lists - communication and leadership
	 * @return
	 */
	private List<List<ProjectType>> formProjectTypeLists() {
		List<ProjectType> projectTypes = projectTypeDao.findAllByOrderByPeckOrderDesc();
		
		List<List<ProjectType>> projectTypeLists = new ArrayList<List<ProjectType>>();
		projectTypeLists.add(new ArrayList<ProjectType>());
		projectTypeLists.add(new ArrayList<ProjectType>());
		
		for (ProjectType projectType : projectTypes) {
//			System.out.println(projectType.getProjectTypeId() + " " + projectType.getTypeName() + " " + projectType.getPeckOrder() + " " + projectType.getIsCommunication());
			projectTypeLists.get(projectType.getIsCommunication() ? 0 : 1).add(projectType);
		}
		
		return projectTypeLists;
	}

	
}



