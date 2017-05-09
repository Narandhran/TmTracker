/**
 * @author Balaji
 *
 *         11-Feb-2017 - Balaji creation ProjectTypeDao.java
 */
package com.neemShade.TmTracker.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.neemShade.TmTracker.pojo.ProjectType;

/**
 * @author Balaji
 *
 */

@Transactional
public interface ProjectTypeDao extends CrudRepository<ProjectType, Long> {

	public List<ProjectType> findAllByOrderByPeckOrderDesc();
}
