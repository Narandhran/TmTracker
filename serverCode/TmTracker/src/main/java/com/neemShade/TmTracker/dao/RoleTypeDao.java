/**
 * @author Balaji
 *
 *         13-Feb-2017 - Balaji creation RoleTypeDao.java
 */
package com.neemShade.TmTracker.dao;



import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.neemShade.TmTracker.pojo.RoleType;



/**
 * @author Balaji
 *
 */

@Transactional
public interface RoleTypeDao  extends CrudRepository<RoleType, Long> {

	public RoleType findByTypeName(String typeName);
	
	
}
