/**
 * @author Balaji
 *
 *         11-Feb-2017 - Balaji creation UserDao.java
 */
package com.neemShade.TmTracker.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.pojo.RoleUser;
import com.neemShade.TmTracker.pojo.User;

/**
 * @author Balaji
 *
 */
@Transactional
@Repository
public interface UserDao extends PagingAndSortingRepository<User, Long> {

  /**
   * This method will find an User instance in the database by its phone.
   * Note that this method is not implemented and its working code will be
   * automagically generated from its signature by Spring Data JPA.
   */
  public User findByPhone(String phone);
  
  public Long countByUserId(Long userId);

//  public List<UserDto> fetchClubMembers(Long clubId, Pageable pageable);
  
  @Query ("select distinct u "
  		+ " from User u, RoleUser ru, Role r, RoleType rt, Club c "
		+ " where u.userId = ru.user.userId and "
  		+ "       ru.role.roleId = r.roleId  and "
		+ "       r.roleType.roleTypeId = rt.roleTypeId  and "
  		+ "       ru.club.clubId = c.clubId  and "
		+ "       c.clubId = :clubId  and "
  		+ "       r.roleName = :membershipValue  and "
		+ "       rt.typeName = :membershipType "
		  )
  public List<User> fetchClubMembers(@Param("clubId") Long clubId, @Param("membershipType") String membershipType, @Param("membershipValue") String membershipValue);

  
  @Query ("Select distinct u "
		+ " from User u "
		+ " where  "
		+ "       u.userId not in (select pu.user.userId from ProjectUser pu)"
		  )
  public List<User> findUsersWithoutProject();
  
}