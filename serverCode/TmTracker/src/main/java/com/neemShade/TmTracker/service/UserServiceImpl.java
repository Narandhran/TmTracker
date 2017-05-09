/**
 * @author Balaji
 *
 *         17-Feb-2017 - Balaji creation UserServiceImpl.java
 */
package com.neemShade.TmTracker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neemShade.TmTracker.common.ParseCSVFileExample;
import com.neemShade.TmTracker.dao.UserDao;
import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.dto.RoleDto;
import com.neemShade.TmTracker.dto.RoleTypeDto;
import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.pojo.Club;
import com.neemShade.TmTracker.pojo.RoleUser;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	
	private UserDao userDao;
	
	@Autowired
	private RoleTypeService roleTypeService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleUserService roleUserService;
	
	@Autowired
	private ProjectUserService projectUserService;
	
	@Autowired
	private ClubService clubService;
	
	
	@Autowired
	public UserServiceImpl(UserDao userDao)
	{
		this.userDao = userDao;
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.UserService#create(com.neemShade.TmTracker.dto.UserDto)
	 */
	@Override
	public UserDto create(UserDto userDto) {
		User persisted = userDao.save(userDto.getUser());
		return convertToDTO(persisted);
	}
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.UserService#createUsers(java.util.List)
	 */
	@Override
	public List<String> createUsers(List<UserDto> userDtos, ClubDto clubDto) {
		List<String> messages = new ArrayList<String>();
		
		
		RoleTypeDto roleTypeDto = roleTypeService.findByTypeName(RoleTypeDto.MEMBERSHIP_ROLE);
		List<RoleDto> roleDtos = roleService.findByRoleTypeRoleName(roleTypeDto.getRoleType(), RoleDto.CLUB_MEMBER);
		if(roleDtos == null || roleDtos.size() < 0)
			return null;
		
		RoleDto roleDto = roleDtos.get(0);
		
		
		for(UserDto userDto : userDtos)
		{
			UserDto newUserDto = null;
			
			if(userDto == null || userDto.getUser() == null)
				continue;
			
			if(userDto.getUser().getPhone() == null || "".equals(userDto.getUser().getPhone()))
			{
				messages.add("WARN! " + userDto.getUser().getUserName() + " has invalid phone number [" +  userDto.getUser().getPhone() + "]");
				continue;
			}
				
			if(isAlreadyUser(userDto))
			{
				messages.add("WARN! " + userDto.getUser().getUserName() + " may be a dual member");
				newUserDto = findById(userDto.getUser().getUserId());
			}
			else
			{
				newUserDto = create(userDto);
			}
			
			if(isMemberOfClub(newUserDto, clubDto, roleDto))
			{
				messages.add("Error! " + userDto.getUser().getUserName() + " already registered with the club " + clubDto.getClub().getClubCode());
				continue;
			}
			else
			{
				try {
					addClubMembership(newUserDto, clubDto, roleDto);
					messages.add("Success! added " + userDto.getUser().getUserName());
				} catch (Exception e) {
					messages.add("Error while registering " + e.getMessage());
				}
			}
		}
		
		return messages;
	}


	/**
	 * @param userDto
	 * @param clubDto
	 * @param roleDto 
	 * @throws Exception 
	 */
	private RoleUser addClubMembership(UserDto userDto, ClubDto clubDto, RoleDto roleDto) throws Exception {
		
		
		if(roleDto == null)
			throw new Exception("Unable to get role");
		
		
		RoleUser roleUser = roleUserService.create(userDto.getUser(), clubDto.getClub(), roleDto.getRole());
		
		return roleUser;
	}

	/**
	 * check if the user is already part of the system
	 * user table's phone field is compared to conclude
	 * @param userDto
	 * @return
	 */
	public boolean isAlreadyUser(UserDto userDto)
	{
		Long count = 0l;
		
		if(userDto == null || userDto.getUser() == null || 
				userDto.getUser().getUserId() == null ||userDto.getUser().getUserId() == 0)
			return false;
		else
			count = countByUserId(userDto.getUser().getUserId());
		return count != null && count != 0;
	}
//	public boolean isAlreadyUser(UserDto userDto)
//	{
//		UserDto newUserDto = null;
//		
//		if(userDto == null || userDto.getUser() == null || 
//				userDto.getUser().getUserId() == null ||userDto.getUser().getUserId() == 0)
//			return false;
//		else
//			newUserDto = findByPhone(userDto.getUser().getPhone());
//		return newUserDto != null && newUserDto.getUser() != null;
//	}
	
	public boolean isMemberOfClub(UserDto userDto, ClubDto clubDto, RoleDto roleDto)
	{
		if(roleDto == null) return false;
		
		int count = roleUserService.countByUserClubRole(userDto.getUser(), clubDto.getClub(), roleDto.getRole());
		
		return count > 0;
	}
	

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.UserService#delete(java.lang.Integer)
	 */
	@Override
	public UserDto delete(Long id) {
		User deleted = findUserById(id);
		userDao.delete(deleted);
		return convertToDTO(deleted);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.UserService#findAll()
	 */
	@Override
	public List<UserDto> findAll() {
		List<User> users = (List<User>) userDao.findAll();
		return convertToDTOs(users);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.UserService#findById(java.lang.Long)
	 */
	@Override
	public UserDto findById(Long id) {
		User user = findUserById(id);
		return convertToDTO(user);
	}
	
	
	public User findUserById(Long id)
	{
		return userDao.findOne(id);
		
	}
	
	public UserDto findByPhone(String phone)
	{
//		System.out.println("trying to get user by phone");
		User user = userDao.findByPhone(phone);
//		System.out.println("test 23 " + user.getUserName());
		return convertToDTO(user);
	}
	
	@Override
	public Long countByUserId(Long userId)
	{
		return userDao.countByUserId(userId);
	}
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.UserService#fetchClubMembers(java.lang.Long)
	 */
	@Transactional
	@Override
	public List<UserDto> fetchClubMembers(Long clubId, String membershipValue) {
		
		List<User> users = userDao.fetchClubMembers(clubId, RoleTypeDto.MEMBERSHIP_ROLE, membershipValue);
		
//		System.out.println("Inside fetchClubMembers");
		List<UserDto> userDtos = new ArrayList<UserDto>();
		for(User user : users)
		{
			UserDto userDto = convertToDTO(user);
			userDto.setCompletedProjectUserDto(projectUserService.fetchCompletedProject(user.getUserId()));
//			System.out.println("test 23 " + userDto.getUser().getUserName());
			userDtos.add(userDto );
			
		}
		return userDtos;
	}
	
	public List<UserDto> findUsersWithoutProject()
	{
		return convertToDTOs(userDao.findUsersWithoutProject());
	}
	

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.UserService#update(com.neemShade.TmTracker.dto.UserDto)
	 */
	@Override
	public UserDto update(UserDto userDto) {
		User user = findUserById(userDto.getUser().getUserId());
		copy(userDto, user);
		user = userDao.save(user);
		return convertToDTO(user);
	}
	
	public UserDto convertToDTO(User user)
	{
		UserDto userDto = new UserDto();
		userDto.setUser(user);
		return userDto;
	}
	
	public List<UserDto> convertToDTOs(List<User> users)
	{
//		return users.stream().map(this::convertToDTO)
//				.collect(Collectors.toList());
		
		List<UserDto> userDtos = new ArrayList<UserDto>();
		for(User user : users)
			userDtos.add(convertToDTO(user));
		return userDtos;
	}

	public void copy(UserDto userDto, User user)
	{
		if(userDto == null || userDto.getUser() == null || user == null)
			return;
		
		User sourceUser = userDto.getUser();
		user.setUserId(sourceUser.getUserId());
		user.setUserName(sourceUser.getUserName());
		user.setPhone(sourceUser.getPhone());
		user.setEmail(sourceUser.getEmail());
		user.setPassword(sourceUser.getPassword());
		user.setDateOfBirth(sourceUser.getDateOfBirth());
		user.setUserNotes(sourceUser.getUserNotes());
		
	}

	
	@Override
	public void registerCsvMembers()
	{
		Club club = clubService.findClubById(2l);
		
		ParseCSVFileExample parseCSVFileExample = new ParseCSVFileExample();
        parseCSVFileExample.setFilename("D:\\jbalaji\\personal\\neemShade\\software\\tmTracker\\MembershipRoster.csv");
//        parseCSVFileExample.setClubId(2l);
        parseCSVFileExample.setClub(club);
        parseCSVFileExample.setClubService(clubService);
        parseCSVFileExample.setUserService(this);
        parseCSVFileExample.process();
	}
	
}

