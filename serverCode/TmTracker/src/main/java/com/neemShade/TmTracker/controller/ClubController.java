/**
 * @author Balaji
 *
 *         14-Feb-2017 - Balaji creation ClubController.java
 */
package com.neemShade.TmTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.service.ClubService;

/**
 * @author Balaji
 *
 */

@RestController
@RequestMapping(value="/club")
public class ClubController {
	
	 @Autowired
	  private ClubService clubService;

	 
		/**
		 * for the given member, find the associated clubs.  Users of given membership is considered
		 * 
		 * @param clubId
		 * @param membershipValue either Member or Retired or Guest
		 * @return
		 */
		@RequestMapping(value="/fetchClubsOfMember/{userId}/{membershipValue}", method=RequestMethod.GET)
		@ResponseBody
		public List<ClubDto> fetchClubsOfMember(@PathVariable Long userId, @PathVariable  String membershipValue)
		{
			return clubService.fetchClubsOfMember(userId, membershipValue);
		}
}
