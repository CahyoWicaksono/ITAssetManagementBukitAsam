package com.example.itassetmanagementptbukitasam.Admin;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.itassetmanagementptbukitasam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;

public class Schedule extends AppCompatActivity {

    Dialog dialog;
    EditText editText;
    Button delete_subject, add_subject;

    String timetable_subject;
    DatabaseReference database;
    FirebaseUser user;
    ValueEventListener listener;
    TextView Sun1, Sun2, Sun3, Sun4, Sun5, Sun6, Sun7,
            Mon1, Mon2, Mon3, Mon4, Mon5, Mon6, Mon7,
            Tue1, Tue2, Tue3, Tue4, Tue5, Tue6, Tue7,
            Wed1, Wed2, Wed3, Wed4, Wed5, Wed6, Wed7,
            Thu1, Thu2, Thu3, Thu4, Thu5, Thu6, Thu7,
            Fri1, Fri2, Fri3, Fri4, Fri5, Fri6, Fri7,
            Sat1, Sat2, Sat3, Sat4, Sat5, Sat6, Sat7;

    TextView timeslot1, timeslot2, timeslot3, timeslot4,
            timeslot5, timeslot6, timeslot7, timeslot8,
            timeslot9, timeslot10, timeslot11, timeslot12,
            timeslot13, timeslot14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_schedule);

        database = FirebaseDatabase.getInstance().getReference("PlanningMaintenance");
        user = FirebaseAuth.getInstance().getCurrentUser();

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot1").getValue(String.class) != null)
                    timeslot1.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot1").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot2").getValue(String.class) != null)
                    timeslot2.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot2").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot3").getValue(String.class) != null)
                    timeslot3.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot3").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot4").getValue(String.class) != null)
                    timeslot4.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot4").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot5").getValue(String.class) != null)
                    timeslot5.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot5").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot6").getValue(String.class) != null)
                    timeslot6.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot6").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot7").getValue(String.class) != null)
                    timeslot7.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot7").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot8").getValue(String.class) != null)
                    timeslot8.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot8").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot9").getValue(String.class) != null)
                    timeslot9.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot9").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot10").getValue(String.class) != null)
                    timeslot10.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot10").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot11").getValue(String.class) != null)
                    timeslot11.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot11").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot12").getValue(String.class) != null)
                    timeslot12.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot12").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot13").getValue(String.class) != null)
                    timeslot13.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot13").getValue(String.class));

                if (snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot14").getValue(String.class) != null)
                    timeslot14.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("timeslot14").getValue(String.class));

                Sun1.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sunday1").getValue(String.class));
                Sun2.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sunday2").getValue(String.class));
                Sun3.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sunday3").getValue(String.class));
                Sun4.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sunday4").getValue(String.class));
                Sun5.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sunday5").getValue(String.class));
                Sun6.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sunday6").getValue(String.class));
                Sun7.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Sunday7").getValue(String.class));

                Mon1.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Monday1").getValue(String.class));
                Mon2.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Monday2").getValue(String.class));
                Mon3.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Monday3").getValue(String.class));
                Mon4.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Monday4").getValue(String.class));
                Mon5.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Monday5").getValue(String.class));
                Mon6.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Monday6").getValue(String.class));
                Mon7.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Monday7").getValue(String.class));

                Tue1.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Tuesday1").getValue(String.class));
                Tue2.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Tuesday2").getValue(String.class));
                Tue3.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Tuesday3").getValue(String.class));
                Tue4.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Tuesday4").getValue(String.class));
                Tue5.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Tuesday5").getValue(String.class));
                Tue6.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Tuesday6").getValue(String.class));
                Tue7.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Tuesday7").getValue(String.class));


                Wed1.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Wednesday1").getValue(String.class));
                Wed2.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Wednesday2").getValue(String.class));
                Wed3.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Wednesday3").getValue(String.class));
                Wed4.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Wednesday4").getValue(String.class));
                Wed5.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Wednesday5").getValue(String.class));
                Wed6.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Wednesday6").getValue(String.class));
                Wed7.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Wednesday7").getValue(String.class));

                Thu1.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Thursday1").getValue(String.class));
                Thu2.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Thursday2").getValue(String.class));
                Thu3.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Thursday3").getValue(String.class));
                Thu4.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Thursday4").getValue(String.class));
                Thu5.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Thursday5").getValue(String.class));
                Thu6.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Thursday6").getValue(String.class));
                Thu7.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Thursday7").getValue(String.class));

                Fri1.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friday1").getValue(String.class));
                Fri2.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friday2").getValue(String.class));
                Fri3.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friday3").getValue(String.class));
                Fri4.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friday4").getValue(String.class));
                Fri5.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friday5").getValue(String.class));
                Fri6.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friday6").getValue(String.class));
                Fri7.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friday7").getValue(String.class));

                Sat1.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Saturday1").getValue(String.class));
                Sat2.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Saturday2").getValue(String.class));
                Sat3.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Saturday3").getValue(String.class));
                Sat4.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Saturday4").getValue(String.class));
                Sat5.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Saturday5").getValue(String.class));
                Sat6.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Saturday6").getValue(String.class));
                Sat7.setText(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Saturday7").getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };

        //levels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialog=new Dialog(Schedule.this);
        dialog.setContentView(R.layout.custom_pupop_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        editText=dialog.findViewById(R.id.po);
        add_subject=dialog.findViewById(R.id.add_subject);
        delete_subject=dialog.findViewById(R.id.delete_subject);

        //--------timeslots-------------------------------------
        timeslot1=findViewById(R.id.timeslot1);
        timeslot2=findViewById(R.id.timeslot2);
        timeslot3=findViewById(R.id.timeslot3);
        timeslot4=findViewById(R.id.timeslot4);
        timeslot5=findViewById(R.id.timeslot5);
        timeslot6=findViewById(R.id.timeslot6);
        timeslot7=findViewById(R.id.timeslot7);
        timeslot8=findViewById(R.id.timeslot8);
        timeslot9=findViewById(R.id.timeslot9);
        timeslot10=findViewById(R.id.timeslot10);
        timeslot11=findViewById(R.id.timeslot11);
        timeslot12=findViewById(R.id.timeslot12);
        timeslot13=findViewById(R.id.timeslot13);
        timeslot14=findViewById(R.id.timeslot14);

        timeslot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot1,"timeslot1");


            }
        });
        timeslot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot2,"timeslot2");

            }
        });
        timeslot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot3,"timeslot3");

            }
        });
        timeslot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot4,"timeslot4");

            }
        });
        timeslot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot5,"timeslot5");

            }
        });
        timeslot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot6,"timeslot6");

            }
        });
        timeslot7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot7,"timeslot7");

            }
        });
        timeslot8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot8,"timeslot8");

            }
        });
        timeslot9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot9,"timeslot9");

            }
        });
        timeslot10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot10,"timeslot10");

            }
        });
        timeslot11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot11,"timeslot11");

            }
        });
        timeslot12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot12,"timeslot12");

            }
        });
        timeslot13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot13,"timeslot13");

            }
        });
        timeslot14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime(timeslot14,"timeslot14");

            }
        });

    }
    public void show(TextView textView,String dayname){
        timetable_subject=null;

        dialog.show();

        add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timetable_subject=editText.getText().toString();

                if(timetable_subject.isEmpty()){
                    editText.requestFocus();
                    editText.setError("field is empty");

                }else{
                    HashMap<String, Object> values = new HashMap<>();
                    values.put(dayname, timetable_subject);
                    database.updateChildren(values);
                    textView.setText(timetable_subject);
                    editText.setText("");
                    dialog.dismiss();}
            }
        });
        delete_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.removeValue();
                textView.setText("");
                dialog.dismiss();
            }
        });
    }
    public void pickTime(TextView textView,String slotname){


        TimePickerDialog timePickerDialog=new TimePickerDialog(Schedule.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                String time=String.format(Locale.getDefault(),"%02d:%02d",h,m);
                HashMap<String, Object> values = new HashMap<>();
                values.put(slotname, time);
                database.child(user.getUid()).updateChildren(values);
                textView.setText(time);

            }
        },0,0,true);
        timePickerDialog.show();
    }

    @Override
    protected void onPause() {
        database.removeEventListener(listener);
        super.onPause();
    }
}
