/**
 * @author Balaji
 *
 *         21-Feb-2017 - Balaji creation ClubServiceImpl.java
 */
package com.neemShade.TmTracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neemShade.TmTracker.dao.ClubDao;
import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.dto.RoleTypeDto;
import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.pojo.Club;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */
@Service
public class ClubServiceImpl implements ClubService {

	
	private ClubDao clubDao;
	
	
	@Autowired
	public ClubServiceImpl(ClubDao clubDao)
	{
		this.clubDao = clubDao;
	}
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ClubService#create(com.neemShade.TmTracker.dto.ClubDto)
	 */
	@Override
	public ClubDto create(ClubDto clubDto) {
		Club persisted = clubDao.save(clubDto.getClub());
		return convertToDTO(persisted);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ClubService#delete(java.lang.Long)
	 */
	@Override
	public ClubDto delete(Long id) {
		Club deleted = findClubById(id);
		clubDao.delete(deleted);
		return convertToDTO(deleted);
		
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ClubService#findAll()
	 */
	@Override
	public List<ClubDto> findAll() {
		List<Club> clubs = (List<Club>) clubDao.findAll();
		return convertToDTOs(clubs);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ClubService#findById(java.lang.Long)
	 */
	@Override
	public ClubDto findById(Long id) {
		Club club = findClubById(id);
		return convertToDTO(club);
	}

	public Club findClubById(Long id)
	{
		return clubDao.findOne(id);
	}
	
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.UserService#fetchClubMembers(java.lang.Long)
	 */
	@Transactional
	@Override
	public List<ClubDto> fetchClubsOfMember(Long userId, String membershipValue) {
		
		List<Club> clubs = clubDao.fetchClubsOfMember(userId, RoleTypeDto.MEMBERSHIP_ROLE, membershipValue);
		
//		System.out.println("Inside fetchClubMembers");
		List<ClubDto> clubDtos = convertToDTOs(clubs);
//		for(Club club : clubs)
//		{
//			UserDto userDto = convertToDTO(user);
//			userDto.setCompletedProjectUserDto(projectUserService.fetchCompletedProject(user.getUserId()));
////			System.out.println("test 23 " + userDto.getUser().getUserName());
//			userDtos.add(userDto );
//			
//		}
		return clubDtos;
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ClubService#update(com.neemShade.TmTracker.dto.ClubDto)
	 */
	@Override
	public ClubDto update(ClubDto clubDto) {
		Club club = findClubById(clubDto.getClub().getClubId());
		copy(clubDto, club);
		club = clubDao.save(club);
		return convertToDTO(club);
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ClubService#convertToDTO(com.neemShade.TmTracker.pojo.Club)
	 */
	@Override
	public ClubDto convertToDTO(Club club) {
		ClubDto clubDto = new ClubDto();
		clubDto.setClub(club);
		return clubDto;
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ClubService#convertToDTOs(java.util.List)
	 */
	@Override
	public List<ClubDto> convertToDTOs(List<Club> clubs) {
//		return clubs.stream().map(this::convertToDTO)
//				.collect(Collectors.toList());
		List<ClubDto> clubDtos = new ArrayList<ClubDto>();
		for(Club club : clubs)
			clubDtos.add(convertToDTO(club));
		return clubDtos;
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.ClubService#copy(com.neemShade.TmTracker.dto.ClubDto, com.neemShade.TmTracker.pojo.Club)
	 */
	@Override
	public void copy(ClubDto clubDto, Club club) {
		if(clubDto == null || clubDto.getClub() == null || club == null)
			return;
		
		Club sourceClub = clubDto.getClub();
		club.setAttendanceSensitivity(sourceClub.getAttendanceSensitivity());
		club.setClubCode(sourceClub.getClubCode());
		club.setClubName(sourceClub.getClubName());
		club.setClubNotes(sourceClub.getClubNotes());
		club.setClubNumber(sourceClub.getClubNumber());
		club.setLocation(sourceClub.getLocation());
		club.setMeetingDay(sourceClub.getMeetingDay());
		club.setMeetingTime(sourceClub.getMeetingTime());
	}

}
