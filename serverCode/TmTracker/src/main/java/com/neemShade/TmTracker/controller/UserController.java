/**
 * @author Balaji
 *
 *         07-Feb-2017 - Balaji creation UserController.java
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

import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.service.UserService;

/**
 * @author Balaji
 *
 */

@RestController
@RequestMapping(value="/user")
public class UserController {

	  @Autowired
	  private UserService service;
	 
	  
	  
	  
	  @RequestMapping(value="/create", method = RequestMethod.POST)
	  @ResponseBody
	  public UserDto create(@RequestBody UserDto userDto) {
	    
	      return service.create(userDto);
	      
	  }
	  
	  /**
	   * GET /create  --> Create a new user and save it in the database.
	   */
	  @RequestMapping(value="/createUsers", method = RequestMethod.POST)
	  @ResponseBody
	  public List<String> createUsers(@RequestBody List<UserDto> userDtos, @RequestBody ClubDto clubDto) {
		  
	      return service.createUsers(userDtos, clubDto);
	      
	  }
	  
	  /**
	   * GET /delete  --> Delete the user having the passed id.
	   */
	  @RequestMapping("/delete")
	  @ResponseBody
	  public UserDto delete(Long id) {
	    
	      return service.delete(id);
	   
	  }
	  
	  /**
	   * GET /getByPhone  --> Return the id for the user having the passed
	   * phone.
	   */
	  @RequestMapping(value="/getByPhone/{phone}", method=RequestMethod.GET)
	  @ResponseBody
	  public UserDto getByPhone(@PathVariable String phone) {
	   
	    
	      return service.findByPhone(phone);

	  }
	  

	
	/**
	 * for the given club, find the roleUser and corresponding users.  Users of given membership is considered
	 * 
	 * @param clubId
	 * @param membershipValue either Member or Retired or Guest
	 * @return
	 */
	@RequestMapping(value="/fetchClubMembers/{clubId}/{membershipValue}", method=RequestMethod.GET)
	@ResponseBody
	public List<UserDto> fetchClubMembers(@PathVariable Long clubId, @PathVariable  String membershipValue)
	{
		return service.fetchClubMembers(clubId, membershipValue);
	}
	
	
	/**
	 * list of users without projects
	 * 
	 * @param clubId
	 * @param membershipValue either Member or Retired or Guest
	 * @return
	 */
	@RequestMapping(value="/findUsersWithoutProject", method=RequestMethod.GET)
	@ResponseBody
	public List<UserDto> findUsersWithoutProject()
	{
		return service.findUsersWithoutProject();
	}
	
	

	/**
	   * GET /update  --> Update the email and the name for the user in the 
	   * database having the passed id.
	   */
	  @RequestMapping(value="/update", method = RequestMethod.PUT)
	  @ResponseBody
//	  public String updateUser(Long id, String userName, String phone, String email, String password, Date dateOfBirth, String userNotes) {
	  public UserDto update(UserDto userDto)  
	  {
		  return service.update(userDto);
	  }

	  
	  
	  @RequestMapping(value="/registerCsvMembers", method=RequestMethod.GET)
	  @ResponseBody
	  public void registerCsvMembers()
	  {
			service.registerCsvMembers();
	  }
	 
}
