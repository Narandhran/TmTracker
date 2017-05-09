/**
 * @author Balaji
 *
 *         17-Feb-2017 - Balaji creation UserService.java
 */
package com.neemShade.TmTracker.service;

import java.util.List;


import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */
public interface UserService {

	public UserDto create(UserDto userDto);
	
	/**
	 * @param userDtos
	 * @return
	 * @throws Exception 
	 */
	public List<String> createUsers(List<UserDto> userDtos, ClubDto clubDto);
	
	public UserDto delete(Long id);
	
	public List<UserDto> findAll();
	
	public UserDto findById(Long id);
	
	public UserDto findByPhone(String phone);
	
	public Long countByUserId(Long userId);
	
	/**
	 * @param clubId
	 * @return
	 */
	public List<UserDto> fetchClubMembers(Long clubId,  String membershipValue);
	
	public List<UserDto> findUsersWithoutProject();
	
	public UserDto update(UserDto userDto);
	
	public UserDto convertToDTO(User user);
		
	public List<UserDto> convertToDTOs(List<User> users);
	
	public void copy(UserDto userDto, User user);

	/**
	 * 
	 */
	void registerCsvMembers();



	
}
