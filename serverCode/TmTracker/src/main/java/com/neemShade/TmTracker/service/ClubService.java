/**
 * @author Balaji
 *
 *         21-Feb-2017 - Balaji creation ClubService.java
 */
package com.neemShade.TmTracker.service;

import java.util.List;

import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.pojo.Club;


/**
 * @author Balaji
 *
 */
public interface ClubService {
	public ClubDto create(ClubDto clubDto);
	
	public ClubDto delete(Long id);
	
	public List<ClubDto> findAll();
	
	public ClubDto findById(Long id);
	
	public Club findClubById(Long id);
	
	/**
	 * @param userId
	 * @param membershipValue
	 * @return
	 */
	public List<ClubDto> fetchClubsOfMember(Long userId, String membershipValue);
	
	public ClubDto update(ClubDto clubDto);
	
	public ClubDto convertToDTO(Club club);
		
	public List<ClubDto> convertToDTOs(List<Club> clubs);
	
	public void copy(ClubDto clubDto, Club club);

	
}
