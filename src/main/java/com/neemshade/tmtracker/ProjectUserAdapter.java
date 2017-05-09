package com.neemshade.tmtracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neemShade.TmTracker.dto.ProjectUserDto;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Tejesvini on 22-02-17.
 */

public class ProjectUserAdapter extends RecyclerView.Adapter<ProjectUserAdapter.MyViewHolder> {

    private List<ProjectUserDto> projectUserDtos;

    public ProjectUserAdapter(List<ProjectUserDto> projectUserDtos) {
        this.projectUserDtos = projectUserDtos;
    }

    @Override
    public ProjectUserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_list_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectUserAdapter.MyViewHolder holder, int position) {
        ProjectUserDto projectUserDto = projectUserDtos.get(position);
        holder.level.setText(projectUserDto.findProjectLevel());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = simpleDateFormat.format(projectUserDto.getProjectUser().getProjectGivenDate());
        holder.year.setText(formatedDate);
        holder.title.setText(projectUserDto.getProjectUser().getProjectTitle());
    }

    @Override
    public int getItemCount() {
        return projectUserDtos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView year, level,title;
        public MyViewHolder(View itemView) {
            super(itemView);
            year = (TextView)itemView.findViewById(R.id.txt_date);
            level = (TextView)itemView.findViewById(R.id.txt_project_level);
            title = (TextView)itemView.findViewById(R.id.txt_title);
        }
    }
}
