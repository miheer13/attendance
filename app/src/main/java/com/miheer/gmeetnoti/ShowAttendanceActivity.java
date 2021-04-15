package com.miheer.gmeetnoti;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ShowAttendanceActivity extends AppCompatActivity {

    private ListView lsAttend;

    private ArrayList<String> attendanceArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance);

        lsAttend =findViewById(R.id.lsAttend);

        ArrayAdapter<String> myArrayAdaptor = new ArrayAdapter<>(ShowAttendanceActivity.this, android.R.layout.simple_list_item_1,attendanceArrayList);
        lsAttend.setAdapter(myArrayAdaptor);

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("attendance").child("ed");
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                attendanceArrayList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    attendanceArrayList.add(snapshot1.getValue().toString());
                }
                myArrayAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}