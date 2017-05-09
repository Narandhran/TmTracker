/**
 * @author Balaji
 *
 *         17-Feb-2017 - Balaji creation ClubDto.java
 */
package com.neemShade.TmTracker.dto;

import com.neemShade.TmTracker.pojo.Club;

/**
 * @author Balaji
 *
 */
public class ClubDto {

	private Club club;
	
	public String toString()
	{
		return club == null ? "" : club.toString();
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}
	
}
