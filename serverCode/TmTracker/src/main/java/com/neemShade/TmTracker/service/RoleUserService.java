/**
 * @author Balaji
 *
 *         23-Feb-2017 - Balaji creation RoleUserService.java
 */
package com.neemShade.TmTracker.service;

import com.neemShade.TmTracker.pojo.Club;
import com.neemShade.TmTracker.pojo.Role;
import com.neemShade.TmTracker.pojo.RoleUser;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */
public interface RoleUserService {
	
	public int countByUserClubRole(User user, Club club, Role role);

	/**
	 * @param user
	 * @param club
	 * @param role
	 * @return
	 */
	public RoleUser create(User user, Club club, Role role);
	
}
