package com.neemshade.tmtracker;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neemShade.TmTracker.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tejesvini on 23-02-17.
 */

public class ClubMemberAdapter extends BaseAdapter {
    private Activity activity;
    private static LayoutInflater inflater = null;
    private List<UserDto> memberList;

    public ClubMemberAdapter(Activity activity,List<UserDto> memberList) {
        super();
        this.memberList = memberList;
        this.activity = activity;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Object getItem(int position) {
        return memberList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)  view = inflater.inflate(R.layout.club_member_list_row, null);
        TextView levelTxt,nameTxt;
        levelTxt = (TextView)view.findViewById(R.id.txt_level);
        nameTxt = (TextView)view.findViewById(R.id.txt_member_name);



        UserDto member = memberList.get(i);
        String displayProjectLevel =
                        member == null ||
                        member.getCompletedProjectUserDto() == null ||
                        member.getCompletedProjectUserDto().findProjectLevel() == null
                        ? "" :
                                member.getCompletedProjectUserDto().findProjectLevel();

        String userName =
                member == null ||
                member.getUser() == null ||
                member.getUser().getUserName() == null
                ? "Invalid user" :
                        member.getUser().getUserName();

        levelTxt.setText(displayProjectLevel);
        nameTxt.setText(userName);
        return view;
    }
}
