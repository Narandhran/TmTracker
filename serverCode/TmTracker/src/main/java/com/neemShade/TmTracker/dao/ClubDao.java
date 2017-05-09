/**
 * @author Balaji
 *
 *         14-Feb-2017 - Balaji creation ClubDao.java
 */
package com.neemShade.TmTracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.neemShade.TmTracker.pojo.Club;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */

@Transactional
public interface ClubDao  extends CrudRepository<Club, Long> {

	
	/**
	 * list of clubs that the given user is associated
	 * @param clubId
	 * @param membershipType
	 * @param membershipValue
	 * @return
	 */
	 @Query ("select distinct c "
		  		+ " from User u, RoleUser ru, Role r, RoleType rt, Club c "
				+ " where u.userId = ru.user.userId and "
		  		+ "       ru.role.roleId = r.roleId  and "
				+ "       r.roleType.roleTypeId = rt.roleTypeId  and "
		  		+ "       ru.club.clubId = c.clubId  and "
				+ "       u.userId = :userId  and "
		  		+ "       r.roleName = :membershipValue  and "
				+ "       rt.typeName = :membershipType "
				  )
		  public List<Club> fetchClubsOfMember(@Param("userId") Long userId, @Param("membershipType") String membershipType, @Param("membershipValue") String membershipValue);

}
