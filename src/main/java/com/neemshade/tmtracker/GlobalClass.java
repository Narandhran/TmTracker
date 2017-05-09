package com.neemshade.tmtracker;

import android.app.Application;


import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.pojo.ProjectType;
import com.neemShade.TmTracker.pojo.User;

import java.util.List;

/**
 * Created by Tejesvini on 15-02-17.
 */

public class GlobalClass extends Application {

    private UserDto loginUserDto;

    private ClubDto selectedClubDto;
    private List<ProjectType> projectTypes;

    public UserDto getLoginUserDto() {
        return loginUserDto;
    }

    public void setLoginUserDto(UserDto loginUserDto) {
        this.loginUserDto = loginUserDto;
    }

    public ClubDto getSelectedClubDto() {
        return selectedClubDto;
    }

    public void setSelectedClubDto(ClubDto selectedClubDto) {
        this.selectedClubDto = selectedClubDto;
    }

    public void setProjectTypes(List<ProjectType> projectTypes) {
        this.projectTypes = projectTypes;
    }
}
