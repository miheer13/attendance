package com.miheer.gmeetnoti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {


    private ListView noticeListView;
    private ImageButton ibNewNotice;
    private Button btnAttendance;

    private ArrayList<String> noticeArrayList = new ArrayList<>();

    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        noticeListView = findViewById(R.id.noticeListView);
        ArrayAdapter<String> noticeArrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, noticeArrayList);
        noticeListView.setAdapter(noticeArrayAdapter);
        ibNewNotice = findViewById(R.id.ibNewNotice);
        btnAttendance = findViewById(R.id.btnAttendance);

        reference = FirebaseDatabase.getInstance().getReference().child("notices");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    noticeArrayList.add(snapshot1.getValue().toString());
                }
                noticeArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        ibNewNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherActivity.this, TeacherNewNoticeActivity.class));
                finish();
            }
        });

        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherActivity.this, AttendanceActivity.class));
            }
        });
    }
}