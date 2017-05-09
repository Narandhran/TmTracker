/**
 * @author Balaji
 *
 *         17-Feb-2017 - Balaji creation UserDto.java
 */
package com.neemShade.TmTracker.dto;

import java.util.List;

import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */
public class UserDto {
	private User user;
	
	private ProjectUserDto completedProjectUserDto;
	
	private List<ClubDto> clubs;

	/**
	 * remove non-digits in the given str
	 * @param givenStr
	 * @return
	 */
	public static String purifier(String givenStr)
	{
		if(givenStr == null) return null;
		
		String result = "";
		
		for (int i = 0; i < givenStr.length(); i++) {
			char ch = givenStr.charAt(i);
			if(Character.isDigit(ch))
				result += ch;
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		return getUser().getUserName();
	}
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public List<ClubDto> getClubs() {
		return clubs;
	}

	public void setClubs(List<ClubDto> clubs) {
		this.clubs = clubs;
	}

	public ProjectUserDto getCompletedProjectUserDto() {
		return completedProjectUserDto;
	}

	public void setCompletedProjectUserDto(ProjectUserDto completedProjectUserDto) {
		this.completedProjectUserDto = completedProjectUserDto;
	}


}
