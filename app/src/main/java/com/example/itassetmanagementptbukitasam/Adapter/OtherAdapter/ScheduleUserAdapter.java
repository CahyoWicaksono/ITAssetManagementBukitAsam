package com.example.itassetmanagementptbukitasam.Adapter.OtherAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itassetmanagementptbukitasam.R;
import com.example.itassetmanagementptbukitasam.model.ScheduleModel;

import java.util.ArrayList;

public class ScheduleUserAdapter extends RecyclerView.Adapter<ScheduleUserAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<ScheduleModel> scheduleModelArrayList;
    private Context context;
    private ScheduleUserAdapter.ViewScheduleUserClickInterface viewScheduleUserClickInterface;

    public ScheduleUserAdapter(ArrayList<ScheduleModel> scheduleModelArrayList, Context context, ViewScheduleUserClickInterface viewScheduleUserClickInterface) {
        this.scheduleModelArrayList = scheduleModelArrayList;
        this.context = context;
        this.viewScheduleUserClickInterface = viewScheduleUserClickInterface;
    }



    @NonNull
    @Override
    public ScheduleUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_schedule_user,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleUserAdapter.ViewHolder Scheduleholder, int position) {
        ScheduleModel scheduleModel = scheduleModelArrayList.get(position);
        Scheduleholder.task.setText(scheduleModel.getTaskSchedule());
        Scheduleholder.date.setText(scheduleModel.getDateSchedule());
        Scheduleholder.time.setText(scheduleModel.getTimeSchedule());


        setAnimation(Scheduleholder.itemView, position);
        Scheduleholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewScheduleUserClickInterface.onScheduleUserClick(position);
            }
        });

    }
    private void setAnimation (View itemView,int position){
        if (position > lastpos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastpos = position;
        }


    }

    @Override
    public int getItemCount() {
        return scheduleModelArrayList.size();
    }
    public interface ViewScheduleUserClickInterface {
        void onScheduleUserClick(int position);

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView task,date, time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.tv_task_user);
            date = itemView.findViewById(R.id.tv_date_schedule_user);
            time = itemView.findViewById(R.id.tv_time_schedule_user);
        }
    }
}
