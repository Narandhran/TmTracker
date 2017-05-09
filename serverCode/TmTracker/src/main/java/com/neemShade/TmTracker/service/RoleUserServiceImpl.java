/**
 * @author Balaji
 *
 *         23-Feb-2017 - Balaji creation RoleUserServiceImpl.java
 */
package com.neemShade.TmTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neemShade.TmTracker.dao.RoleUserDao;
import com.neemShade.TmTracker.pojo.Club;
import com.neemShade.TmTracker.pojo.Role;
import com.neemShade.TmTracker.pojo.RoleUser;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {

	@Autowired
	private RoleUserDao roleUserDao;
	
	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleUserService#countByUserClubRole(com.neemShade.TmTracker.pojo.User, com.neemShade.TmTracker.pojo.Club, com.neemShade.TmTracker.pojo.Role)
	 */
	@Override
	public int countByUserClubRole(User user, Club club, Role role) {
		return roleUserDao.countByUserUserIdAndClubClubIdAndRoleRoleId(user.getUserId(), club.getClubId(), role.getRoleId());
	}

	/* (non-Javadoc)
	 * @see com.neemShade.TmTracker.service.RoleUserService#create(com.neemShade.TmTracker.pojo.User, com.neemShade.TmTracker.pojo.Club, com.neemShade.TmTracker.pojo.Role)
	 */
	@Override
	public RoleUser create(User user, Club club, Role role) {
		RoleUser roleUser = new RoleUser();
		roleUser.setUser(user);
		roleUser.setClub(club);
		roleUser.setRole(role);
		roleUser.setIsRegular(true);
		//TODO set join date
		
		return roleUserDao.save(roleUser);
	}

}
