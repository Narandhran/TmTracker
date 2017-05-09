package com.neemShade.TmTracker.pojo;
// default package
// Generated 20 Feb, 2017 8:03:33 PM by Hibernate Tools 5.2.0.Beta1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Club generated by hbm2java
 */
@Entity
@Table(name = "tm_club", catalog = "tm")
//@JsonIdentityInfo(
//		  generator = ObjectIdGenerators.PropertyGenerator.class, 
//		  property = "clubId")
public class Club implements java.io.Serializable {

	private Long clubId;
	private String clubName;
	private String clubCode;
	private String clubNumber;
	private String location;
	private Integer meetingDay;
	private Integer attendanceSensitivity;
	private String meetingTime;
	private String clubNotes;
	@JsonIgnore
	private Set<Meeting> meetings = new HashSet<Meeting>(0);
	@JsonIgnore
	private Set<RoleUser> roleUsers = new HashSet<RoleUser>(0);

	public Club() {
	}

	public Club(String clubName, String clubCode, String clubNumber,
			String location, Integer meetingDay, Integer attendanceSensitivity,
			String meetingTime, String clubNotes, Set<Meeting> meetings,
			Set<RoleUser> roleUsers) {
		this.clubName = clubName;
		this.clubCode = clubCode;
		this.clubNumber = clubNumber;
		this.location = location;
		this.meetingDay = meetingDay;
		this.attendanceSensitivity = attendanceSensitivity;
		this.meetingTime = meetingTime;
		this.clubNotes = clubNotes;
		this.meetings = meetings;
		this.roleUsers = roleUsers;
	}
	
	@Override
	public String toString()
	{
		return clubCode;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "club_id", unique = true, nullable = false)
	public Long getClubId() {
		return this.clubId;
	}

	public void setClubId(Long clubId) {
		this.clubId = clubId;
	}

	@Column(name = "club_name", length = 500)
	public String getClubName() {
		return this.clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	@Column(name = "club_code", length = 50)
	public String getClubCode() {
		return this.clubCode;
	}

	public void setClubCode(String clubCode) {
		this.clubCode = clubCode;
	}

	@Column(name = "club_number", length = 100)
	public String getClubNumber() {
		return this.clubNumber;
	}

	public void setClubNumber(String clubNumber) {
		this.clubNumber = clubNumber;
	}

	@Column(name = "location", length = 2000)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "meeting_day")
	public Integer getMeetingDay() {
		return this.meetingDay;
	}

	public void setMeetingDay(Integer meetingDay) {
		this.meetingDay = meetingDay;
	}

	@Column(name = "attendance_sensitivity")
	public Integer getAttendanceSensitivity() {
		return this.attendanceSensitivity;
	}

	public void setAttendanceSensitivity(Integer attendanceSensitivity) {
		this.attendanceSensitivity = attendanceSensitivity;
	}

	@Column(name = "meeting_time", length = 100)
	public String getMeetingTime() {
		return this.meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	@Column(name = "club_notes", length = 2000)
	public String getClubNotes() {
		return this.clubNotes;
	}

	public void setClubNotes(String clubNotes) {
		this.clubNotes = clubNotes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
//	@JsonBackReference
	public Set<Meeting> getMeetings() {
		return this.meetings;
	}

	public void setMeetings(Set<Meeting> meetings) {
		this.meetings = meetings;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
//	@JsonBackReference
	public Set<RoleUser> getRoleUsers() {
		return this.roleUsers;
	}

	public void setRoleUsers(Set<RoleUser> roleUsers) {
		this.roleUsers = roleUsers;
	}

}