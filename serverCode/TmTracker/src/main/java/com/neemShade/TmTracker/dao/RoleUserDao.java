/**
 * @author Balaji
 *
 *         14-Feb-2017 - Balaji creation RoleUserDao.java
 */
package com.neemShade.TmTracker.dao;



import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.neemShade.TmTracker.pojo.Club;
import com.neemShade.TmTracker.pojo.Role;
import com.neemShade.TmTracker.pojo.RoleUser;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */

@Transactional
public interface RoleUserDao extends CrudRepository<RoleUser, Long> {

//	public List<RoleUser> findByRoleIdAndUserId(Integer roleId, Integer userId);
	
	public int countByUserUserIdAndClubClubIdAndRoleRoleId(Long userId, Long clubId, Integer roleId);
}
