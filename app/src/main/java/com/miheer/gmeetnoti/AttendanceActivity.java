package com.miheer.gmeetnoti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AttendanceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private Button btnDate, btnNextRoll, submitAttend, showAttendance;
    private TextView txtDate, text, attendText;
    private TextSwitcher txtSwitcher;
    private CheckBox cbPresent;
//    Context context=getApplicationContext();
//    private String[] row = context.getResources().getStringArray(R.array.rollNo);
    private int stringIndex = 0;
    private String dateString;
    private String subject;
    private TextView textView;
    SharedPreferences sharedPreferences;



    private Task<Void> reference;
    private DatabaseReference referenc;

    private static final String sharedSubject = "myPref";
    private static final String KEY_SUBJECT = "SUBJECT";
    int present = 0;
    int absent = 0;

    private static final String sharedDate = "myPref";
    private static final String KEY_Date = "DATE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Context context=getApplicationContext();
        String[] row = context.getResources().getStringArray(R.array.rollNo);

        btnDate = findViewById(R.id.btnDate);
        txtDate = findViewById(R.id.txtDate);
        btnNextRoll = findViewById(R.id.btnNextRoll);
        txtSwitcher = findViewById(R.id.txtSwitcher);
        cbPresent = findViewById(R.id.cbPresent);
        text = findViewById(R.id.text);
        submitAttend = findViewById(R.id.submitAttend);
        attendText =findViewById(R.id.attendText);
        showAttendance = findViewById(R.id.showAttendance);

        showAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AttendanceActivity.this,ShowAttendanceActivity.class));
            }
        });



        customProgressDailog dailog = new customProgressDailog(AttendanceActivity.this);

        sharedPreferences = getSharedPreferences(sharedDate,MODE_PRIVATE);
        sharedPreferences = getSharedPreferences(sharedSubject,MODE_PRIVATE);
        String date = sharedPreferences.getString(KEY_Date, null);




        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_Date,dateString);
                editor.apply();


            }
        });




        btnNextRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (cbPresent.isChecked()) {

                    present++;
                    reference = FirebaseDatabase.getInstance().getReference().child("attendance").child("ed").child(dateString).child(row[stringIndex]).setValue("present");
                    reference = FirebaseDatabase.getInstance().getReference().child("attendance").child("ed").child(dateString).child("present").setValue(present);
                    cbPresent.setChecked(false);
                } else {
                    absent++;
                    reference = FirebaseDatabase.getInstance().getReference().child("attendance").child("ed").child(dateString).child(row[stringIndex]).setValue("absent");
                    reference = FirebaseDatabase.getInstance().getReference().child("attendance").child("ed").child(dateString).child("absent").setValue(absent);
                }

                if (stringIndex == row.length - 1) {
                    stringIndex = 0;
                    txtSwitcher.setText(row[stringIndex]);
                } else {
                    txtSwitcher.setText(row[++stringIndex]);
                }


            }
        });

        txtSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textView = new TextView(AttendanceActivity.this);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(60);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                return textView;
            }
        });

        txtSwitcher.setText(row[stringIndex]);

        submitAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendText.setText("");
                dailog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dailog.dismiss();
                    }
                },5000);
                attendText.setText("Attendance for the day:" +
                        "\nPresent: " + present +
                        "\nAbsent: " + absent);
                present = 0;
                absent = 0;


            }
        });


    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        dateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        txtDate.setText("Attendance for the day " + dateString);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        subject = adapterView.getItemAtPosition(i).toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SUBJECT,subject);
        editor.apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}