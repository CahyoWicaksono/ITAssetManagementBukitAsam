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

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    int lastpos = -1;
    private ArrayList<ScheduleModel> scheduleModelArrayList;
    private Context context;
    private ScheduleAdapter.ViewScheduleClickInterface viewScheduleClickInterface;


    public ScheduleAdapter(ArrayList<ScheduleModel> scheduleModelArrayList, Context context, ViewScheduleClickInterface viewScheduleClickInterface) {
        this.scheduleModelArrayList = scheduleModelArrayList;
        this.context = context;
        this.viewScheduleClickInterface = viewScheduleClickInterface;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_schedule,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ViewHolder Scheduleholder, int position) {
        ScheduleModel scheduleModel = scheduleModelArrayList.get(position);
        Scheduleholder.task.setText(scheduleModel.getTaskSchedule());
        Scheduleholder.date.setText(scheduleModel.getDateSchedule());
        Scheduleholder.time.setText(scheduleModel.getTimeSchedule());


        setAnimation(Scheduleholder.itemView, position);
        Scheduleholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewScheduleClickInterface.onScheduleClick(position);
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

    public interface ViewScheduleClickInterface {
        void onScheduleClick(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView task,date, time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.tv_task);
            date = itemView.findViewById(R.id.tv_date_schedule);
            time = itemView.findViewById(R.id.tv_time_schedule);
        }
    }
}
