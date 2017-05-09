/**
 * @author Balaji
 *
 *         14-Feb-2017 - Balaji creation ProjectUserDao.java
 */
package com.neemShade.TmTracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.neemShade.TmTracker.pojo.ProjectUser;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */

@Transactional
public interface ProjectUserDao  extends CrudRepository<ProjectUser, Long> {

	public List<ProjectUser> findByUser(User user);

	/**
	 * most recent (highest) project done
	 * @param userId
	 * @return
	 */
//	@Query("select pu from "
//			+ " ProjectUser pu, ProjectType pt "
//			+ " where "
//			+ " pu.projectType.projectTypeId = pt.projectTypeId and "
//			+ " pu.user.userId = :userId "
//			+ " order by pt.peckOrder DESC, pu.projectLevel DESC  "
//			+ "  ")
//	public ProjectUser fetchCompletedProject(@Param("userId") Long userId);
	
	public ProjectUser findFirstByUserUserIdOrderByProjectTypePeckOrderDescProjectLevelDesc(Long userId);
	

}
